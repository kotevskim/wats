package com.sorsix.interns.finalproject.wats.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2SuccessHandler.class);

    private final JwtUtil jwtUtil;

    public OAuth2SuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        LOGGER.info("Authentication success for user {}", authentication.getName());
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub",  authentication.getName());
        claims.put("userId", authentication.getName());
        // TODO add authorities to claims based on some logic...
        String jwtToken = jwtUtil.generateToken(claims);
        response.addHeader(jwtUtil.getHeaderString(), jwtUtil.getTokenPrefix() + jwtToken);
        LOGGER.info("JWT token subject {} created and added to the Authorisation header", authentication.getName());
        response.sendRedirect("http://localhost:4200/jwt?jwt=" + jwtToken);
    }
}