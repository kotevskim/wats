package com.sorsix.interns.finalproject.wats.security;

import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.service.SsoIdentityService;
import com.sorsix.interns.finalproject.wats.service.UserService;
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
import java.util.HashMap;
import java.util.Map;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2SuccessHandler.class);

    private final JwtUtil jwtUtil;
    private final SsoIdentityService ssoIdentityService;
    private final UserService userService;

    public OAuth2SuccessHandler(JwtUtil jwtUtil,
                                SsoIdentityService ssoIdentityService,
                                UserService userService) {
        this.jwtUtil = jwtUtil;
        this.ssoIdentityService = ssoIdentityService;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        Map<String, String> details = (Map<String, String>) oAuth2Authentication.getUserAuthentication().getDetails();
        String provider = "";
        String ssoId = "";
        String username = authentication.getName();
        String email = "";
        String name = "";
        String pictureUrl = "";
        if (request.getRequestURI().contains("github")) {
            provider = "github";
            ssoId = String.valueOf(details.getOrDefault("id", ""));
            username = details.getOrDefault("login", authentication.getName());
            email = details.getOrDefault("email", "");
            name = details.getOrDefault("name", "");
            pictureUrl = details.getOrDefault("avatar_url", "");
        }

        User user = null;
        if (!ssoId.isEmpty()) {
            String finalUsername = username;
            String finalName = name;
            String finalEmail = email;
            String finalPictureUrl = pictureUrl;
            String finalSsoId = ssoId;
            String finalProvider = provider;
            user = this.ssoIdentityService.findUser(ssoId, provider)
                    .orElseGet(() -> {
                        User u = this.userService.createUser(finalName, finalUsername, finalEmail, null, finalPictureUrl);
                        this.ssoIdentityService.createSsoIdentity(finalSsoId, finalProvider, u);
                        return u;
                    });
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", username);
        claims.put("userId", user.getId());
        claims.put("userEmail", email);
        // TODO add authorities to claims based on some logic...
        String jwtToken = jwtUtil.generateToken(claims);
        response.addHeader(jwtUtil.getHeaderString(), jwtUtil.getTokenPrefix() + jwtToken);
        response.sendRedirect("http://localhost:4200/token?jwt=" + jwtToken);

        LOGGER.info("Authentication successful for user [id: {}, username: {}, email: {}] ; provider: {}",
                user.getId(), username, email, provider);
        LOGGER.info("JWT token with subject = {} and claims=[userId: {}, userEmail: {}] created and added to the Authorisation header",
                username, user.getId(), email);
    }
}