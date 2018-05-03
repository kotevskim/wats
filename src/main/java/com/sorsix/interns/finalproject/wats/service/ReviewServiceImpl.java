package com.sorsix.interns.finalproject.wats.service;

import com.sorsix.interns.finalproject.wats.domain.Review.Review;
import com.sorsix.interns.finalproject.wats.persistence.ReviewDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao;

    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Override
    public Collection<Review> getReviewsByLocation(Long locationId) {
        return null;
    }
}
