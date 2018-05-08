package com.sorsix.interns.finalproject.wats.service.impl;

import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.persistence.UserDAO;
import com.sorsix.interns.finalproject.wats.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDAO userDAO;

    @Autowired
    public AuthenticationServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Optional<Principal> getPrinciple() {
        return Optional.of((Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

    @Override
    public Optional<Long> getUserId() {

        try {
            return Optional.of(Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getDetails().toString()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public User getUser() {
            return getUserId()
                    .map(id -> userDAO.findById(id).get())
                    .orElseGet(() -> userDAO.findByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()).get());
    }

    @Override
    public boolean isUserAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }
}
