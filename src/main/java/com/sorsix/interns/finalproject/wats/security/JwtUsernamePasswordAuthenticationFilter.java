package com.sorsix.interns.finalproject.wats.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.UserCredentials;
import com.sorsix.interns.finalproject.wats.persistence.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    static final Logger LOGGER = LoggerFactory.getLogger(JwtUsernamePasswordAuthenticationFilter.class);

    private final JwtUtil jwtUtil;
    private final UserDao userDao;
    private final AuthenticationManager authenticationManager;

    public JwtUsernamePasswordAuthenticationFilter(JwtUtil jwtUtil,
                                                   UserDao userDao,
                                                   AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.userDao = userDao;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            UserCredentials credentials = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    credentials.username,
                    credentials.password,
                    new ArrayList<>());
            return authenticationManager.authenticate(authRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User coreUser
                = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        User user = userDao.findByUsername(coreUser.getUsername()).get();
        Map<String, Object> tokenClaims = new HashMap<>();
        tokenClaims.put("sub", user.getUsername()); // sets token's subject
        tokenClaims.put("userId", user.getId());
        tokenClaims.put("userAuthorities", new ArrayList<GrantedAuthority>());
        String token = jwtUtil.generateToken(tokenClaims);
        res.addHeader(jwtUtil.getHeaderString(), jwtUtil.getTokenPrefix() + token);
        ObjectMapper mapper = new ObjectMapper();
        res.getWriter().write(mapper.writeValueAsString(user));
        LOGGER.info("Authentication successful for user {}", user.getUsername());
    }

    @Override
    public void setFilterProcessesUrl(String filterProcessesUrl) {
        super.setFilterProcessesUrl(filterProcessesUrl);
    }
}
