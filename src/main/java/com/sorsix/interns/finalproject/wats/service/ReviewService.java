package com.sorsix.interns.finalproject.wats.service;

import com.sorsix.interns.finalproject.wats.domain.Review.Review;
import com.sorsix.interns.finalproject.wats.persistence.ReviewDao;

import java.util.Collection;

public interface ReviewService {

    Collection<Review> getReviewsByLocation(Long locationId);
}
