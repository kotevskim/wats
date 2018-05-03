package com.sorsix.interns.finalproject.wats.persistence;

import com.sorsix.interns.finalproject.wats.domain.review.ReviewComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ReviewCommentDao extends JpaRepository<ReviewComment, Long> {

    Collection<ReviewComment> findByReviewId(Long id);
}
