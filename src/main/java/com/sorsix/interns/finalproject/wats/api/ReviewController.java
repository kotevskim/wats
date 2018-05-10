package com.sorsix.interns.finalproject.wats.api;

import com.sorsix.interns.finalproject.wats.api.exception.LocationNotFoundException;
import com.sorsix.interns.finalproject.wats.api.exception.ReviewCommentNotFoundException;
import com.sorsix.interns.finalproject.wats.api.exception.ReviewNotFoundException;
import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.Location;
import com.sorsix.interns.finalproject.wats.domain.review.Review;
import com.sorsix.interns.finalproject.wats.domain.review.ReviewComment;
import com.sorsix.interns.finalproject.wats.service.AuthenticationService;
import com.sorsix.interns.finalproject.wats.service.LocationService;
import com.sorsix.interns.finalproject.wats.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;
    private final AuthenticationService authenticationService;
    private final LocationService locationService;

    @Autowired
    public ReviewController(ReviewService reviewService,
                            AuthenticationService authenticationService,
                            LocationService locationService) {
        this.reviewService = reviewService;
        this.authenticationService = authenticationService;
        this.locationService = locationService;
    }

    @GetMapping("public/locations/{locationId}/reviews/{reviewId}")
    public Review getReview(@PathVariable Long reviewId) {
        return reviewService.findReview(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
    }

    @GetMapping("public/locations/{locationId}/reviews")
    public Page<Review> getReviewsForLocation(@PathVariable Long locationId,
                                              Pageable pageable,
                                              Sort sort) {
        return this.locationService.findLocation(locationId)
                .map(it -> reviewService.getReviewsForLocation(locationId, pageable))
                .orElseThrow(() -> new LocationNotFoundException(locationId));
    }

    @GetMapping("public/locations/{locationId}/reviews/{reviewId}/comments")
    public Page<ReviewComment> getReviewComments(@PathVariable Long reviewId,
                                                 Pageable pageable,
                                                 Sort sort) {
        return this.reviewService.findReview(reviewId)
                .map(it -> reviewService.getReviewComments(reviewId, pageable))
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
    }

    @GetMapping("public/locations/{locationId}/reviews/{reviewId}/top-comments")
    Collection<ReviewComment> getTopReviewComments(@PathVariable long reviewId,
                                                   @RequestParam int limit) {
        return this.reviewService.findReview(reviewId)
                .map(it -> reviewService.getTopCommentsForReview(reviewId, limit))
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
    }

    @GetMapping("public/locations/{locationId}/reviews/{reviewId}/likes")
    Collection<User> getUsersForLikesOnReview(@PathVariable Long reviewId) {
        return this.reviewService.findReview(reviewId)
                .map(this.reviewService::mapReviewLikesToUsers)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
    }

    @GetMapping("public/locations/{locationId}/reviews/{reviewId}/comments/{commentId}/likes")
    Collection<User> getUsersForLikesOnReviewComment(@PathVariable Long commentId) {
        return this.reviewService.findReviewComment(commentId)
                .map(this.reviewService::mapReviewCommentLikesToUsers)
                .orElseThrow(() -> new ReviewCommentNotFoundException(commentId));
    }

    @PostMapping("locations/{locationId}/reviews")
    public Review postReview(@PathVariable Long locationId,
                             @RequestBody String description) {
        return this.locationService.findLocation(locationId)
                .map(location -> {
                    User user = authenticationService.getUser();
                    return reviewService.createReview(user, location, description);
                })
                .orElseThrow(() -> new LocationNotFoundException(locationId));
    }

    @PostMapping("locations/{locationId}/reviews/{reviewId}/comments")
    ReviewComment postReviewComment(@PathVariable Long reviewId,
                                    @RequestBody String description) {
        return this.reviewService.findReview(reviewId)
                .map(review -> {
                    User user = authenticationService.getUser();
                    return this.reviewService.createReviewComment(user, review, description);
                })
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
    }

    @PostMapping("locations/{locationId}/reviews/{reviewId}/likes")
    boolean likeReview(@PathVariable Long reviewId) {
        return this.reviewService.findReview(reviewId)
                .map(review -> {
                    User user = authenticationService.getUser();
                    return this.reviewService.postLikeForReview(review, user);
                })
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
    }

    @PostMapping("locations/{locationId}/reviews/{reviewId}/comments/{commentId}/likes")
    boolean likeReviewComment(@PathVariable Long commentId) {
        return this.reviewService.findReviewComment(commentId)
                .map(comment -> {
                    User user = authenticationService.getUser();
                    return this.reviewService.postLikeForReviewComment(comment, user);
                })
                .orElseThrow(() -> new ReviewCommentNotFoundException(commentId));
    }
}
