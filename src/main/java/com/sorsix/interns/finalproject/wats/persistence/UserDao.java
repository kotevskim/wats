package com.sorsix.interns.finalproject.wats.persistence;

import com.sorsix.interns.finalproject.wats.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
