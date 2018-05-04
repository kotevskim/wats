package com.sorsix.interns.finalproject.wats.service;

import com.sorsix.interns.finalproject.wats.domain.User;
import org.springframework.security.core.GrantedAuthority;

import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

public interface AuthenticationService {

    Optional<Principal> getPrinciple();

    Collection<? extends GrantedAuthority> getAuthorities();

    Optional<Long> getUserId();

    User getUser();

    boolean isUserAuthenticated();
}
