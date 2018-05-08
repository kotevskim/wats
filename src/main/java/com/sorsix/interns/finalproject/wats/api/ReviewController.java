package com.sorsix.interns.finalproject.wats.api;

import com.sorsix.interns.finalproject.wats.domain.Location;
import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.UserDTO;
import com.sorsix.interns.finalproject.wats.domain.review.Review;
import com.sorsix.interns.finalproject.wats.domain.review.ReviewComment;
import com.sorsix.interns.finalproject.wats.persistence.ReviewDAO;
import com.sorsix.interns.finalproject.wats.persistence.UserDAO;
import com.sorsix.interns.finalproject.wats.service.AuthenticationService;
import com.sorsix.interns.finalproject.wats.service.LocationService;
import com.sorsix.interns.finalproject.wats.service.ReviewService;
import com.sorsix.interns.finalproject.wats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final LocationService locationService;

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ReviewDAO reviewDAO;

    @Autowired
    public ReviewController(ReviewService reviewService,
                            AuthenticationService authenticationService,
                            UserService userService,
                            LocationService locationService) {
        this.reviewService = reviewService;
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.locationService = locationService;
    }

    @GetMapping("public/locations/{locationId}/reviews/{reviewId}")
    public Review getReviewsByLocation(@PathVariable Long locationId,
                                       @PathVariable Long reviewId) {
        return reviewService.findReview(reviewId)
                .orElseThrow(() -> new RuntimeException("No such review"));
    }

    @GetMapping("public/locations/{locationId}/reviews")
    public Page<Review> getReviewsByLocation(@PathVariable Long locationId,
                                             Pageable pageable,
                                             Sort sort) {
        return reviewService.getReviewsForLocation(locationId, pageable);
    }

    @GetMapping("public/locations/{locationId}/reviews/{reviewId}/comments")
    public Collection<ReviewComment> getReviewComments(@PathVariable Long locationId,
                                                       @PathVariable Long reviewId) {
        return reviewService.getReviewComments(reviewId);
    }

    @PostMapping("locations/{locationId}/reviews")
    public Review postReview(@PathVariable Long locationId, @RequestParam String description) {
        Location location = this.locationService.findLocation(locationId)
                .orElseThrow(() -> new RuntimeException("invalid location id"));
        User user = authenticationService.getUser();
        return reviewService.createReview(user, location, description);
    }

    @GetMapping(value = "public/locations/{locationId}/reviews/{reviewId}/top-comments")
    Collection<ReviewComment> getTopCommentsForReview(@PathVariable long reviewId,
                                                      @RequestParam int limit) {
        return this.reviewService.findReview(reviewId)
                .map(review -> reviewService.getTopCommentsForReview(reviewId, limit))
                .orElseThrow(() -> new RuntimeException("review not found"));
    }

    @PostMapping("locations/{locationId}/reviews/{reviewId}/comments")
    ReviewComment postCommentForReview(@PathVariable Long reviewId, @RequestParam String description) {
        Review review = this.reviewService.findReview(reviewId)
                .orElseThrow(() -> new RuntimeException("invalid review id"));
        User user = authenticationService.getUser();
        return this.reviewService.createReviewComment(user, review, description);
    }

    @GetMapping("public/locations/{locationId}/reviews/{reviewId}/likes/count")
    Long countReviewLikes(@PathVariable Long reviewId) {
       return this.reviewService.countReviewLikes(reviewId);
    }

    @GetMapping("public/locations/{locationId}/reviews/{reviewId}/likes")
    Collection<UserDTO> getUsersForLikesOnReview(@PathVariable Long reviewId) {
        return this.reviewService.getUsersForLikesOnReview(reviewId);
    }

    @GetMapping("public/locations/{locationId}/reviews/{reviewId}/comments/{commendId}/likes/count")
    Long countReviewCommentLikes(@PathVariable Long commendId) {
        return this.reviewService.countReviewCommentLikes(commendId);
    }

    @GetMapping("public/locations/{locationId}/reviews/{reviewId}/comments/{commendId}/likes")
    Collection<UserDTO> getUsersForLikesOnReviewComment(@PathVariable Long commendId) {
        return this.reviewService.getUsersForLikesOnReviewComment(commendId);
    }

    @PostMapping("locations/{locationId}/reviews/{reviewId}/likes")
    void likeReview(@PathVariable Long reviewId) {
        Review review = this.reviewService.findReview(reviewId)
                .orElseThrow(() -> new RuntimeException("no cuh review"));
        User user = authenticationService.getUser();
        this.reviewService.likeReview(review, user);
    }

    @PostMapping("locations/{locationId}/reviews/{reviewId}/comments/{commentId}/likes")
    void likeReviewComment(@PathVariable Long commentId) {
        ReviewComment comment = this.reviewService.findReviewComment(commentId)
                .orElseThrow(() -> new RuntimeException("no cuh review comment"));
        User user = authenticationService.getUser();
        this.reviewService.likeReviewComment(comment, user);
    }
}
