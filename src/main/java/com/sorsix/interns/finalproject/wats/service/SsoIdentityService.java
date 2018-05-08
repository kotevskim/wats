package com.sorsix.interns.finalproject.wats.service;

import com.sorsix.interns.finalproject.wats.domain.SsoIdentity;
import com.sorsix.interns.finalproject.wats.domain.User;

import java.util.Optional;

public interface SsoIdentityService {
    Optional<User> findUser(String ssoId, String provider);
    SsoIdentity createSsoIdentity(String ssoId, String provider, User user);
}
