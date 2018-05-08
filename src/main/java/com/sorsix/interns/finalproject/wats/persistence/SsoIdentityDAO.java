package com.sorsix.interns.finalproject.wats.persistence;

import com.sorsix.interns.finalproject.wats.domain.SsoIdentity;
import com.sorsix.interns.finalproject.wats.domain.SsoIdentityPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SsoIdentityDAO extends JpaRepository<SsoIdentity, SsoIdentityPrimaryKey> {
}
