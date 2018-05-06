package com.sorsix.interns.finalproject.wats.api;

import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.forum.ForumAnswer;
import com.sorsix.interns.finalproject.wats.domain.review.Review;
import com.sorsix.interns.finalproject.wats.domain.review.ReviewComment;
import com.sorsix.interns.finalproject.wats.domain.review.ReviewRequest;
import com.sorsix.interns.finalproject.wats.service.AuthenticationService;
import com.sorsix.interns.finalproject.wats.service.ReviewService;
import com.sorsix.interns.finalproject.wats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/locations")
public class ReviewController {

    private final ReviewService reviewService;
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @Autowired
    public ReviewController(ReviewService reviewService,
                            AuthenticationService authenticationService,
                            UserService userService) {
        this.reviewService = reviewService;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @GetMapping("{locationId}/reviews")
    public Page<Review> getReviewsByLocation(@PathVariable Long locationId,
                                             Pageable pageable,
                                             Sort sort) {
        return reviewService.getReviewsForLocation(locationId, pageable);
    }

    @GetMapping("{locationId}/reviews/{reviewId}/comments")
    public Collection<ReviewComment> getReviewComments(@PathVariable Long locationId,
                                                       @PathVariable Long reviewId) {
        return reviewService.getReviewComments(reviewId);
    }

    @PostMapping("{locationId}/reviews")
    public Review postReview(@RequestBody ReviewRequest reviewRequest) {
        User user = authenticationService.getUser();
        return reviewService.createReview(reviewRequest, user)
                .orElseThrow(() -> new RuntimeException("cannot create review"));
    }

    @GetMapping(value = "{locationId}/reviews/{reviewId}/comments/top")
    Collection<ReviewComment> getTopCommentsForReview(@PathVariable long reviewId,
                                                     @RequestParam int quantity) {
        Collection<ReviewComment> result = reviewService.getTopCommentsForReview(reviewId, quantity);
        return result;
    }
}
