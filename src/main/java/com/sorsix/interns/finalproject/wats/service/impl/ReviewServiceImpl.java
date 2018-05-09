package com.sorsix.interns.finalproject.wats.service.impl;

import com.sorsix.interns.finalproject.wats.domain.Location;
import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.UserDTO;
import com.sorsix.interns.finalproject.wats.domain.review.Review;
import com.sorsix.interns.finalproject.wats.domain.review.ReviewComment;
import com.sorsix.interns.finalproject.wats.persistence.LocationDAO;
import com.sorsix.interns.finalproject.wats.persistence.ReviewCommentDAO;
import com.sorsix.interns.finalproject.wats.persistence.ReviewDAO;
import com.sorsix.interns.finalproject.wats.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDAO reviewDAO;
    private final ReviewCommentDAO reviewCommentDAO;
    private final LocationDAO locationDAO;

    @Autowired
    public ReviewServiceImpl(ReviewDAO reviewDAO,
                             ReviewCommentDAO reviewCommentDAO,
                             LocationDAO locationDAO) {
        this.reviewDAO = reviewDAO;
        this.reviewCommentDAO = reviewCommentDAO;
        this.locationDAO = locationDAO;
    }

    @Override
    public Optional<Review> findReview(Long id) {
        return reviewDAO.findById(id);
    }

    @Override
    public Optional<ReviewComment> findReviewComment(Long commentId) {
        return this.reviewCommentDAO.findById(commentId);
    }

    @Override
    public Page<Review> getReviewsForLocation(Long locationId, Pageable pageable) {
        return reviewDAO.findByLocationId(locationId, pageable);
    }

    @Override
    public Page<ReviewComment> getReviewComments(Long reviewId, Pageable pageable) {
        return reviewCommentDAO.findByReviewId(reviewId, pageable);
    }

    @Override
    public Review createReview(User user, Location location, String description) {
        LocalDateTime datePublished = LocalDateTime.now();
        Review review = new Review(description, datePublished, user, location);
        return reviewDAO.save(review);
    }

    @Override
    public Collection<ReviewComment> getTopCommentsForReview(long reviewId, int limit) {
        return reviewCommentDAO.findTopByQuestionId(reviewId, limit);
    }

    @Override
    public Long countReviewLikes(Long reviewId) {
        return this.reviewDAO.countReviewLikes(reviewId);
    }

    @Override
    public ReviewComment createReviewComment(User user, Review review, String description) {
        LocalDateTime datePublished = LocalDateTime.now();
        return this.reviewCommentDAO.save(new ReviewComment(user, review, description, datePublished));
    }

    @Override
    public Collection<UserDTO> getUsersForLikesOnReview(Long reviewId) {
        return this.reviewDAO.getUsersForLikesOnReview(reviewId)
                .stream()
                .map(it -> new UserDTO(Long.parseLong(
                        it.get("id").toString()),
                        it.get("name").toString(),
                        it.get("username").toString()))
                .collect(Collectors.toList());
    }

    @Override
    public Long countReviewCommentLikes(Long commentId) {
        return this.reviewCommentDAO.countReviewCommentLikes(commentId);
    }

    @Override
    public Collection<UserDTO> getUsersForLikesOnReviewComment(Long commentId) {
        return this.reviewCommentDAO.getUsersForLikesOnReviewComment(commentId)
                .stream()
                .map(it -> new UserDTO(Long.parseLong(
                        it.get("id").toString()),
                        it.get("name").toString(),
                        it.get("username").toString()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void likeReview(Review review, User user) {
        review.getLikes().add(user);
    }

    @Override
    @Transactional
    public void likeReviewComment(ReviewComment comment, User user) {
        comment.getLikes().add(user);
    }

}
