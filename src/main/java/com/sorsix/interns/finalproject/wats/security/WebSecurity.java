package com.sorsix.interns.finalproject.wats.security;

import com.sorsix.interns.finalproject.wats.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;

import java.util.ArrayList;
import java.util.List;

@EnableOAuth2Client
@EnableWebSecurity
@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserDao userDao;
    private final OAuth2ClientContext oauth2ClientContext;
//    private final AuthenticationSuccessHandler authenticationSuccessHandler;
//    private final AuthenticationFailureHandler authenticationFailureHandler;
//    private final LogoutSuccessHandler logoutSuccessHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    public WebSecurity(UserDao userDao,
                       @Qualifier("oauth2ClientContext") OAuth2ClientContext oauth2ClientContext,
                       AuthenticationEntryPoint authenticationEntryPoint,
                       PasswordEncoder passwordEncoder,
                       UserDetailsService userDetailsService, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.oauth2ClientContext = oauth2ClientContext;
        this.userDao = userDao;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint).and()
//                .formLogin().loginProcessingUrl("/login").and()
//                .logout()
//                    .logoutUrl("/api/logout")
//                    .logoutSuccessHandler(logoutSuccessHandler)
//                    .deleteCookies("JSESSIONID").and()
                .authorizeRequests()
                    .antMatchers( "/", "/login**", "/login/facebook").permitAll()
//                    .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
//                    .antMatchers(HttpMethod.POST, "/login").permitAll()
                    .anyRequest().authenticated().and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                .logout().logoutSuccessUrl("/me").and()
//                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    @Bean
    @ConfigurationProperties("github")
    public ClientResources github() {
        return new ClientResources();
    }

    @Bean
    @ConfigurationProperties("facebook")
    public ClientResources facebook() {
        return new ClientResources();
    }

    private Filter ssoFilter() {
        CompositeFilter compositeFilter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        filters.add(ssoFilter(facebook(), "/login/facebook"));
        filters.add(ssoFilter(github(), "/login/github"));
        compositeFilter.setFilters(filters);
        return compositeFilter;
    }

    private Filter ssoFilter(ClientResources client, String path) {
        OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationFilter
                = new OAuth2ClientAuthenticationProcessingFilter(path);
        OAuth2RestTemplate oAuth2RestTemplate
                = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
        oAuth2ClientAuthenticationFilter.setRestTemplate(oAuth2RestTemplate);
        UserInfoTokenServices tokenServices
                = new UserInfoTokenServices(client.getResource().getUserInfoUri(), client.getResource().getClientId());
        tokenServices.setRestTemplate(oAuth2RestTemplate);
        oAuth2ClientAuthenticationFilter.setTokenServices(tokenServices);
        oAuth2ClientAuthenticationFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        return oAuth2ClientAuthenticationFilter;
    }
}

class ClientResources {

    @NestedConfigurationProperty
    private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

    @NestedConfigurationProperty
    private ResourceServerProperties resource = new ResourceServerProperties();

    public AuthorizationCodeResourceDetails getClient() {
        return client;
    }

    public ResourceServerProperties getResource() {
        return resource;
    }
}
