package com.sorsix.interns.finalproject.wats.domain;

import javax.persistence.*;

@Entity
@Table(name = "identities")
public class Identity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ssoId;
    private String provider;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Identity() {}

    public Long getSsoId() {
        return ssoId;
    }

    public void setSsoId(Long ssoId) {
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
