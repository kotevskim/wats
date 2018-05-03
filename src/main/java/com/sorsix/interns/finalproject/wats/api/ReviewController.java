package com.sorsix.interns.finalproject.wats.api;

import com.sorsix.interns.finalproject.wats.domain.Review.Review;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/locations")
public class ReviewController {

    @GetMapping("{locationId}/reviews")
    public Collection<Review> getReviews(@PathVariable Long locationId) {
        return null;
    }

}
