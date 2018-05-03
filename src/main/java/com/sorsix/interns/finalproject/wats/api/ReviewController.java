package com.sorsix.interns.finalproject.wats.api;

import com.sorsix.interns.finalproject.wats.domain.review.Review;
import com.sorsix.interns.finalproject.wats.domain.review.ReviewComment;
import com.sorsix.interns.finalproject.wats.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/locations")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("{locationId}/reviews")
    public Page<Review> getReviewsByLocation(@PathVariable Long locationId, Pageable pageable) {
        return reviewService.getReviewsForLocation(locationId, pageable);
    }

    @GetMapping("{locationId}/reviews/{reviewId}/comments")
    public Collection<ReviewComment> getReviewComments(@PathVariable Long locationId,
                                                       @PathVariable Long reviewId) {
        return reviewService.getReviewComments(reviewId);
    }

}
