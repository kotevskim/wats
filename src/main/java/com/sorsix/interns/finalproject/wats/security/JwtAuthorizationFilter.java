package com.sorsix.interns.finalproject.wats.security;

import com.sorsix.interns.finalproject.wats.persistence.UserDao;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final UserDao userDao;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDao userDao) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDao = userDao;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(jwtUtil.getHeaderString());
        if (header == null || !header.startsWith(jwtUtil.getTokenPrefix())) {
            chain.doFilter(req, res);
            return;
        }
        Authentication authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(jwtUtil.getHeaderString());
        if (token != null) {
            token = jwtUtil.clearPrefix(token);
            Map<String, java.lang.Object> tokenClaims = jwtUtil.parseToken(token);
            if (tokenClaims != null) {
                String username = (String) tokenClaims.get("sub"); // get token subject (username)
                if (username != null) {
                    Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) tokenClaims.get("userAuthorities");
                    AbstractAuthenticationToken authentication
                            = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    String userId = tokenClaims.get("userId").toString();
                    authentication.setDetails(userId);
                    return authentication;
                }
            }
            return null;
        }
        return null;
    }


}
