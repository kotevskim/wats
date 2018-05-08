package com.sorsix.interns.finalproject.wats.service.impl;

import com.sorsix.interns.finalproject.wats.domain.SsoIdentity;
import com.sorsix.interns.finalproject.wats.domain.SsoIdentityPrimaryKey;
import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.persistence.SsoIdentityDAO;
import com.sorsix.interns.finalproject.wats.service.SsoIdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SsoIdentityServiceImpl implements SsoIdentityService {

    private final SsoIdentityDAO ssoIdentityDAO;

    @Autowired
    public SsoIdentityServiceImpl(SsoIdentityDAO ssoIdentityDAO) {
        this.ssoIdentityDAO = ssoIdentityDAO;
    }

    @Override
    public Optional<User> findUser(String ssoId, String provider) {
        return this.ssoIdentityDAO.findById(new SsoIdentityPrimaryKey(ssoId, provider))
                .map(it -> it.getUser());
    }

    @Override
    public SsoIdentity createSsoIdentity(String ssoId, String provider, User user) {
        return this.ssoIdentityDAO.save(new SsoIdentity(ssoId, provider, user));
    }
}
