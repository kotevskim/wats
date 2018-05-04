package com.sorsix.interns.finalproject.wats.service.impl;

import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.persistence.UserDao;
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

    private final UserDao userDao;

    @Autowired
    public AuthenticationServiceImpl(UserDao userDao) {
        this.userDao = userDao;
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
            return Optional.of((Long) SecurityContextHolder.getContext().getAuthentication().getDetails());
        } catch (ClassCastException e) {
            return Optional.empty();
        }
    }

    @Override
    public User getUser() {
            return getUserId()
                    .map(id -> userDao.findById(id).get())
                    .orElseGet(() -> userDao.findByUsername(getPrinciple().get().getName()).get());
    }

    @Override
    public boolean isUserAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }
}
