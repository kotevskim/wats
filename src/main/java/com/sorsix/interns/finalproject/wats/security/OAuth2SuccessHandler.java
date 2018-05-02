package com.sorsix.interns.finalproject.wats.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.sorsix.interns.finalproject.wats.security.SecurityConstants.*;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    static final Logger LOGGER = LoggerFactory.getLogger(OAuth2SuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        LOGGER.info("Authentication success for user {}", authentication.getName());
        String jwtToken = generateJwtToken(authentication.getName());
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwtToken);
        response.sendRedirect("http://localhost:4200/jwt?jwt=" + jwtToken);
    }

    private String generateJwtToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();
    }
}