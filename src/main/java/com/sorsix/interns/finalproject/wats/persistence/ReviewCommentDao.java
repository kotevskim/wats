package com.sorsix.interns.finalproject.wats.persistence;

import com.sorsix.interns.finalproject.wats.domain.Review.ReviewComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewCommentDao extends JpaRepository<ReviewComment, Long> {

    Optional<ReviewComment> findByReviewId(Long id);
}
