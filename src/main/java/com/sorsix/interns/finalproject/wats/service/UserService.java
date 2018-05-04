package com.sorsix.interns.finalproject.wats.service;

import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.review.Review;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public interface UserService {

    Collection<Review> getUserReviews(Long userId);
    Optional<User> findUserById(long userId);
    Optional<User> findUserByUsername(String username);
}
