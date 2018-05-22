package com.sorsix.interns.finalproject.wats.persistence;

import com.sorsix.interns.finalproject.wats.domain.review.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ReviewDAO extends JpaRepository<Review, Long> {

    Page<Review> findByLocationId(Long id, Pageable pageable);

    Collection<Review> findByUserId(Long id);

    @Deprecated
    @Query(value = "SELECT u.id, u.name, u.username FROM users u JOIN review_likes rl ON u.id = rl.user_id WHERE rl.review_id = ?1",
            nativeQuery = true)
    Collection<Map<String, Object>> mapReviewLikesToUsers(Long reviewId);

    /**
     * @param userId
     * @param reviewId
     * @return true if the user has liked the comment, false otherwise.
     */
    @Query(value = "SELECT EXISTS (SELECT FROM review_likes rl WHERE rl.user_id = ?1 AND rl.review_id = ?2)",
            nativeQuery = true)
    Boolean existsUserLikeForReview(Long userId, Long reviewId);

    @Modifying
    @Query(value = "INSERT INTO review_likes VALUES (?1, ?2)", nativeQuery = true)
    void postLikeForReview(Long userId, Long reviewId);

    @Modifying
    @Query(value = "DELETE FROM review_likes WHERE user_id = ?1 AND review_id = ?2", nativeQuery = true)
    void removeLikeForReview(Long userId, Long reviewId);
}
