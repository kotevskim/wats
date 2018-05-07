package com.sorsix.interns.finalproject.wats.service;

import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.review.Review;
import com.sorsix.interns.finalproject.wats.domain.review.ReviewComment;
import com.sorsix.interns.finalproject.wats.domain.review.ReviewRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Optional;

public interface ReviewService {

    Page<Review> getReviewsForLocation(Long loactionId, Pageable pageable);

    Collection<ReviewComment> getReviewComments(Long reviewId);

    Optional<Review> findReviewById(Long id);

    Optional<Review> createReview(ReviewRequest reviewRequest, User user);
}
