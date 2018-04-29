package com.sorsix.interns.finalproject.wats.config;

import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.util.Collections;

//@EnableOAuth2Client
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDao userDao;
//    private final OAuth2ClientContext oauth2ClientContext;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final LogoutSuccessHandler logoutSuccessHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public SecurityConfig(UserDao userDao,
//                          @Qualifier("oauth2ClientContext") OAuth2ClientContext oauth2ClientContext,
                          AuthenticationSuccessHandler authenticationSuccessHandler,
                          AuthenticationFailureHandler authenticationFailureHandler,
                          LogoutSuccessHandler logoutSuccessHandler,
                          AuthenticationEntryPoint authenticationEntryPoint) {
        this.userDao = userDao;
//        this.oauth2ClientContext = oauth2ClientContext;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint).and()
                .formLogin()
                    .loginProcessingUrl("/api/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(authenticationSuccessHandler)
                    .failureHandler(authenticationFailureHandler).and()
                .logout()
                    .logoutUrl("/api/logout")
                    .logoutSuccessHandler(logoutSuccessHandler)
                    .deleteCookies("JSESSIONID").and()
                .authorizeRequests()
                    .antMatchers("/", "/login**", "/test").permitAll()
                    .anyRequest().authenticated();
//                .logout().logoutSuccessUrl("/me").and()
//                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userDao.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " does not exist"));

                return new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.emptyList()
                );
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
//        FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<>();
//        registration.setFilter(filter);
//        registration.setOrder(-100);
//        return registration;
//    }

//    @Bean
//    @ConfigurationProperties("github")
//    public ClientResources github() {
//        return new ClientResources();
//    }

//    @Bean
//    @ConfigurationProperties("facebook")
//    public ClientResources facebook() {
//        return new ClientResources();
//    }

//    private Filter ssoFilter() {
//        CompositeFilter compositeFilter = new CompositeFilter();
//        List<Filter> filters = new ArrayList<>();
//        filters.add(ssoFilter(facebook(), "/login/facebook"));
//        filters.add(ssoFilter(github(), "/login/github"));
//        compositeFilter.setFilters(filters);
//        return compositeFilter;
//    }

//    private Filter ssoFilter(ClientResources client, String path) {
//        OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationFilter
//                = new OAuth2ClientAuthenticationProcessingFilter(path);
//        OAuth2RestTemplate oAuth2RestTemplate
//                = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
//        oAuth2ClientAuthenticationFilter.setRestTemplate(oAuth2RestTemplate);
//        UserInfoTokenServices tokenServices
//                = new UserInfoTokenServices(client.getResource().getUserInfoUri(), client.getResource().getClientId());
//        tokenServices.setRestTemplate(oAuth2RestTemplate);
//        oAuth2ClientAuthenticationFilter.setTokenServices(tokenServices);
//        return oAuth2ClientAuthenticationFilter;
//    }
}

//class ClientResources {
//
//    @NestedConfigurationProperty
//    private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();
//
//    @NestedConfigurationProperty
//    private ResourceServerProperties resource = new ResourceServerProperties();
//
//    public AuthorizationCodeResourceDetails getClient() {
//        return client;
//    }
//
//    public ResourceServerProperties getResource() {
//        return resource;
//    }
//}
