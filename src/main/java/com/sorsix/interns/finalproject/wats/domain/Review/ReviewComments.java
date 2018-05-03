package com.sorsix.interns.finalproject.wats.domain.Review;

import com.sorsix.interns.finalproject.wats.domain.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "review_comments")
public class ReviewComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewCommentId;
    private String description;
    private Timestamp datePublished;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    public ReviewComments() {}

    public Long getReviewCommentId() {
        return reviewCommentId;
    }

    public void setReviewCommentId(Long reviewCommentId) {
        this.reviewCommentId = reviewCommentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Timestamp datePublished) {
        this.datePublished = datePublished;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
