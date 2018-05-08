package com.sorsix.interns.finalproject.wats.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sso_identities")
@IdClass(SsoIdentityPrimaryKey.class)
public class SsoIdentity {

    @Id
    private String ssoId;
    @Id
    private String provider;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public SsoIdentity() {}

    public SsoIdentity(String ssoId, String provider, User user) {
        this.ssoId = ssoId;
        this.provider = provider;
        this.user = user;
    }

    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}