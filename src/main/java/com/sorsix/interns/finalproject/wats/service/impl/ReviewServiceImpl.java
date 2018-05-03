package com.sorsix.interns.finalproject.wats.service.impl;

import com.sorsix.interns.finalproject.wats.domain.Location;
import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.review.Review;
import com.sorsix.interns.finalproject.wats.domain.review.ReviewComment;
import com.sorsix.interns.finalproject.wats.domain.review.ReviewDto;
import com.sorsix.interns.finalproject.wats.persistence.LocationDao;
import com.sorsix.interns.finalproject.wats.persistence.ReviewCommentDao;
import com.sorsix.interns.finalproject.wats.persistence.ReviewDao;
import com.sorsix.interns.finalproject.wats.persistence.UserDao;
import com.sorsix.interns.finalproject.wats.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao;
    private final ReviewCommentDao reviewCommentDao;
    private final UserDao userDao;
    private final LocationDao locationDao;

    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao,
                             ReviewCommentDao reviewCommentDao,
                             UserDao userDao,
                             LocationDao locationDao) {
        this.reviewDao = reviewDao;
        this.reviewCommentDao = reviewCommentDao;
        this.userDao = userDao;
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
    public Review createReview(Review review) {
        return reviewDao.save(review);
    }

    public Optional<Review> convertToReview(ReviewDto reviewDto) {
        try {
            Location location = locationDao.findById(reviewDto.getLocationId()).orElseThrow(Exception::new);
            Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();
            User user = userDao.findById(userId).get();
            return Optional.of(new Review(reviewDto.getDescription(), reviewDto.getDatePublished(), user, location));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
