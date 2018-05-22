package com.sorsix.interns.finalproject.wats.service;

import com.sorsix.interns.finalproject.wats.domain.Location;
import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.review.Review;
import com.sorsix.interns.finalproject.wats.domain.review.ReviewComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Optional;

public interface ReviewService {

    Optional<Review> findReview(Long id);

    Optional<ReviewComment> findReviewComment(Long commentId);

    Page<Review> getReviewsForLocation(Long locationId, Pageable pageable);

    Page<ReviewComment> getReviewComments(Long reviewId, Pageable pageable);

    Collection<ReviewComment> getTopCommentsForReview(Long reviewId, int limit);

    Collection<User> mapReviewLikesToUsers(Review review);

    Collection<User> mapReviewCommentLikesToUsers(ReviewComment comment);

    Review createReview(User user, Location location, String description);

    ReviewComment createReviewComment(User user, Review review, String description);

    boolean hasUserLikedReview(Long userId, Long reviewId);

    boolean hasUserLikedReviewComment(Long userId, Long commentId);

    void likeReview(Long userId, Long reviewId);

    void dislikeReview(Long userId, Long reviewId);

    void likeReviewComment(Long userId, Long commentId);

    void dislikeReviewComment(Long userId, Long commentId);
}
