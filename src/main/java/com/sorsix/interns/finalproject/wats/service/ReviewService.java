package com.sorsix.interns.finalproject.wats.service;

import com.sorsix.interns.finalproject.wats.domain.review.Review;
import com.sorsix.interns.finalproject.wats.domain.review.ReviewComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface ReviewService {

    Page<Review> getReviewsForLocation(Long loactionId, Pageable pageable);

    Collection<ReviewComment> getReviewComments(Long reviewId);
}
