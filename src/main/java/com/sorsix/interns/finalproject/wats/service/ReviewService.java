package com.sorsix.interns.finalproject.wats.service;

import com.sorsix.interns.finalproject.wats.domain.Location;
import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.UserDTO;
import com.sorsix.interns.finalproject.wats.domain.review.Review;
import com.sorsix.interns.finalproject.wats.domain.review.ReviewComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Optional;

public interface ReviewService {

    Optional<Review> findReview(Long id);

    Optional<ReviewComment> findReviewComment(Long commentId);

    Page<Review> getReviewsForLocation(Long loactionId, Pageable pageable);

    Page<ReviewComment> getReviewComments(Long reviewId, Pageable pageable);

    Collection<ReviewComment> getTopCommentsForReview(long reviewId, int limit);

    Collection<UserDTO> getUsersForLikesOnReview(Long reviewId);

    Collection<UserDTO> getUsersForLikesOnReviewComment(Long commentId);

    Long countReviewLikes(Long reviewId);

    Long countReviewCommentLikes(Long commentId);

    Review createReview(User user, Location location, String description);

    ReviewComment createReviewComment(User user, Review review, String description);

    void likeReview(Review review, User user);

    void likeReviewComment(ReviewComment comment, User user);
}
