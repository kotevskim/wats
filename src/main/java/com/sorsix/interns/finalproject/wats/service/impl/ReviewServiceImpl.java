package com.sorsix.interns.finalproject.wats.service.impl;

import com.sorsix.interns.finalproject.wats.domain.Location;
import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.review.Review;
import com.sorsix.interns.finalproject.wats.domain.review.ReviewComment;
import com.sorsix.interns.finalproject.wats.domain.review.ReviewRequest;
import com.sorsix.interns.finalproject.wats.persistence.LocationDao;
import com.sorsix.interns.finalproject.wats.persistence.ReviewCommentDao;
import com.sorsix.interns.finalproject.wats.persistence.ReviewDao;
import com.sorsix.interns.finalproject.wats.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao;
    private final ReviewCommentDao reviewCommentDao;
    private final LocationDao locationDao;

    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao,
                             ReviewCommentDao reviewCommentDao,
                             LocationDao locationDao) {
        this.reviewDao = reviewDao;
        this.reviewCommentDao = reviewCommentDao;
        this.locationDao = locationDao;
    }

    @Override
    public Page<Review> getReviewsForLocation(Long locationId, Pageable pageable) {
        return reviewDao.findByLocationId(locationId, pageable);
    }

    @Override
    public Collection<ReviewComment> getReviewComments(Long reviewId) {
        return reviewCommentDao.findByReviewId(reviewId);
    }

    @Override
    public Optional<Review> createReview(ReviewRequest reviewRequest, User user) {
        try {
            Location location = locationDao.findById(reviewRequest.getLocationId()).orElseThrow(Exception::new);
            Review review = new Review(reviewRequest.getDescription(), reviewRequest.getDatePublished(), user, location);
            return Optional.of(reviewDao.save(review));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
