package com.sorsix.interns.finalproject.wats.api;

import com.sorsix.interns.finalproject.wats.api.exception.EntityNotFoundException;
import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.review.Review;
import com.sorsix.interns.finalproject.wats.domain.review.ReviewComment;
import com.sorsix.interns.finalproject.wats.persistence.ReviewDAO;
import com.sorsix.interns.finalproject.wats.service.AuthenticationService;
import com.sorsix.interns.finalproject.wats.service.LocationService;
import com.sorsix.interns.finalproject.wats.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SecuredReviewController {

    private final ReviewService reviewService;
    private final AuthenticationService authenticationService;
    private final LocationService locationService;

    @Autowired
    public SecuredReviewController(ReviewService reviewService,
                                   AuthenticationService authenticationService,
                                   LocationService locationService) {
        this.reviewService = reviewService;
        this.authenticationService = authenticationService;
        this.locationService = locationService;
    }

    @PostMapping("locations/{locationId}/reviews")
    public Review postReview(@PathVariable Long locationId,
                             @RequestBody String description) {
        return this.locationService.findLocation(locationId)
                .map(location -> {
                    User user = authenticationService.getUser();
                    return reviewService.createReview(user, location, description);
                })
                .orElseThrow(() -> new EntityNotFoundException("Location", locationId));
    }

    @PostMapping("locations/{locationId}/reviews/{reviewId}/comments")
    ReviewComment postReviewComment(@PathVariable Long reviewId,
                                    @RequestBody String description) {
        return this.reviewService.findReview(reviewId)
                .map(review -> {
                    User user = authenticationService.getUser();
                    return this.reviewService.createReviewComment(user, review, description);
                })
                .orElseThrow(() -> new EntityNotFoundException("Review", reviewId));
    }

    /**
     * @param reviewId
     * @return true for like (user hasn't liked before), false for dislike (user has liked before, so like is removed)
     */
    @PostMapping("locations/{locationId}/reviews/{reviewId}/likes")
    boolean likeOrDislikeReview(@PathVariable Long reviewId) {
        return this.reviewService.findReview(reviewId)
                .map(review -> {
                    User user = authenticationService.getUser();
                    boolean hasLiked = reviewService.hasUserLikedReview(user.getId(), reviewId);
                    if (hasLiked) reviewService.dislikeReview(user.getId(), reviewId);
                    else reviewService.likeReview(user.getId(), reviewId);
                    return !hasLiked;
                })
                .orElseThrow(() -> new EntityNotFoundException("Review", reviewId));
    }

    /**
     * @param commentId
     * @return true for like (user hasn't liked before), false for dislike (user has liked before, so like is removed)
     */
    @PostMapping("locations/{locationId}/reviews/{reviewId}/comments/{commentId}/likes")
    boolean likeOrDislikeReviewComment(@PathVariable Long commentId) {
        return this.reviewService.findReviewComment(commentId)
                .map(comment -> {
                    User user = authenticationService.getUser();
                    boolean hasLiked = reviewService.hasUserLikedReviewComment(user.getId(), commentId);
                    if (hasLiked) reviewService.dislikeReviewComment(user.getId(), commentId);
                    else reviewService.likeReviewComment(user.getId(), commentId);
                    return !hasLiked;
                })
                .orElseThrow(() -> new EntityNotFoundException("Comment", commentId));
    }
}
