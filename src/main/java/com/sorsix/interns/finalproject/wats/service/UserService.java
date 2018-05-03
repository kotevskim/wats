package com.sorsix.interns.finalproject.wats.service;

import com.sorsix.interns.finalproject.wats.domain.review.Review;

import java.util.Collection;

public interface UserService {

    Collection<Review> getUserReviews(Long userId);
}
