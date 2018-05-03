package com.sorsix.interns.finalproject.wats.service.impl;

import com.sorsix.interns.finalproject.wats.domain.review.Review;
import com.sorsix.interns.finalproject.wats.domain.review.ReviewComment;
import com.sorsix.interns.finalproject.wats.persistence.ReviewCommentDao;
import com.sorsix.interns.finalproject.wats.persistence.ReviewDao;
import com.sorsix.interns.finalproject.wats.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao;
    private final ReviewCommentDao reviewCommentDao;

    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao, ReviewCommentDao reviewCommentDao) {
        this.reviewDao = reviewDao;
        this.reviewCommentDao = reviewCommentDao;
    }

    @Override
    public Page<Review> getReviewsForLocation(Long locationId, Pageable pageable) {
        return reviewDao.findByLocationId(locationId, pageable);
    }

    @Override
    public Collection<ReviewComment> getReviewComments(Long reviewId) {
        return reviewCommentDao.findByReviewId(reviewId);
    }
}
