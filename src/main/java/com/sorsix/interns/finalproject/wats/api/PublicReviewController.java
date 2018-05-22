package com.sorsix.interns.finalproject.wats.api;

import com.sorsix.interns.finalproject.wats.api.exception.EntityNotFoundException;
import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.review.Review;
import com.sorsix.interns.finalproject.wats.domain.review.ReviewComment;
import com.sorsix.interns.finalproject.wats.service.LocationService;
import com.sorsix.interns.finalproject.wats.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/public")
public class PublicReviewController {

    private final ReviewService reviewService;
    private final LocationService locationService;

    @Autowired
    public PublicReviewController(ReviewService reviewService,
                                  LocationService locationService) {
        this.reviewService = reviewService;
        this.locationService = locationService;
    }

    @GetMapping("locations/{locationId}/reviews/{reviewId}")
    public Review getReview(@PathVariable Long reviewId) {
        return reviewService.findReview(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review", reviewId));
    }

    @GetMapping("locations/{locationId}/reviews")
    public Page<Review> getReviewsForLocation(@PathVariable Long locationId,
                                              Pageable pageable,
                                              Sort sort) {
        return this.locationService.findLocation(locationId)
                .map(it -> reviewService.getReviewsForLocation(locationId, pageable))
                .orElseThrow(() -> new EntityNotFoundException("Location", locationId));
    }

    @GetMapping("locations/{locationId}/reviews/{reviewId}/comments")
    public Page<ReviewComment> getReviewComments(@PathVariable Long reviewId,
                                                 Pageable pageable,
                                                 Sort sort) {
        return this.reviewService.findReview(reviewId)
                .map(it -> reviewService.getReviewComments(reviewId, pageable))
                .orElseThrow(() -> new EntityNotFoundException("Review", reviewId));
    }

    @GetMapping("locations/{locationId}/reviews/{reviewId}/top-comments")
    Collection<ReviewComment> getTopReviewComments(@PathVariable long reviewId,
                                                   @RequestParam int limit) {
        return this.reviewService.findReview(reviewId)
                .map(it -> reviewService.getTopCommentsForReview(reviewId, limit))
                .orElseThrow(() -> new EntityNotFoundException("Review", reviewId));
    }

    @GetMapping("locations/{locationId}/reviews/{reviewId}/likes")
    Collection<User> getUsersForLikesOnReview(@PathVariable Long reviewId) {
        return this.reviewService.findReview(reviewId)
                .map(this.reviewService::mapReviewLikesToUsers)
                .orElseThrow(() -> new EntityNotFoundException("Review", reviewId));
    }

    @GetMapping("locations/{locationId}/reviews/{reviewId}/comments/{commentId}/likes")
    Collection<User> getUsersForLikesOnReviewComment(@PathVariable Long commentId) {
        return this.reviewService.findReviewComment(commentId)
                .map(this.reviewService::mapReviewCommentLikesToUsers)
                .orElseThrow(() -> new EntityNotFoundException("Comment", commentId));
    }
}
