package com.sorsix.interns.finalproject.wats.domain;

import java.io.Serializable;

public class SsoIdentityPrimaryKey implements Serializable {
    public String ssoId;
    public String provider;

    public SsoIdentityPrimaryKey() {
    }

    public SsoIdentityPrimaryKey(String ssoId, String provider) {
        this.ssoId = ssoId;
        this.provider = provider;
    }
}

