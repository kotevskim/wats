package com.sorsix.interns.finalproject.wats.security;

import com.sorsix.interns.finalproject.wats.config.AuthHandlers;
import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.persistence.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Configuration
public class SecurityBeans {

    static final Logger logger = LoggerFactory.getLogger(SecurityBeans.class);

    private final UserDao userDao;

    public SecurityBeans(UserDao userDao) {
        this.userDao = userDao;
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

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            /**
             * Always returns a 401 error code to the client.
             */
            @Override
            public void commence(HttpServletRequest request,
                                 HttpServletResponse response,
                                 AuthenticationException authenticationException) throws IOException {
                logger.info("Pre-authenticated entry point called. Rejecting access");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
            }
        };
    }
}
