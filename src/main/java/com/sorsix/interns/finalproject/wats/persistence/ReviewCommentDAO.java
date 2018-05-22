package com.sorsix.interns.finalproject.wats.persistence;

import com.sorsix.interns.finalproject.wats.domain.review.ReviewComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Map;

@Repository
public interface ReviewCommentDAO extends JpaRepository<ReviewComment, Long> {

    Page<ReviewComment> findByReviewId(Long id, Pageable pageable);

    @Query(value =
            "SELECT temp_result.id, temp_result.description, temp_result.date_published, \n" +
                    " \ttemp_result.user_id, temp_result.review_id \n" +
                    "FROM \n" +
                    "(\n" +
                    "SELECT fa.*, tmp_count.* from review_comments fa\n" +
                    "JOIN\n" +
                    "(\n" +
                    "    SELECT foo.id as comment_id, COUNT(*) as num_likes\n" +
                    "    FROM (\n" +
                    "        SELECT fa.* from review_comments fa \n" +
                    "        LEFT OUTER JOIN review_comment_likes fal \n" +
                    "        ON fa.id=fal.comment_id\n" +
                    "    ) foo \n" +
                    "    WHERE foo.review_id=?1\n" +
                    "    GROUP BY foo.id\n" +
                    ") tmp_count\n" +
                    "ON fa.id=tmp_count.comment_id\n" +
                    ") temp_result\n" +
                    "ORDER BY temp_result.num_likes DESC\n" +
                    "LIMIT ?2",
            nativeQuery = true)
    Collection<ReviewComment> findTopByQuestionId(long commentId, int size);

    @Deprecated
    @Query(value = "SELECT u.id, u.name, u.username FROM users u JOIN review_comment_likes rcl ON u.id = rcl.user_id WHERE rcl.comment_id = ?1",
            nativeQuery = true)
    Collection<Map<String, Object>> mapReviewCommentLikesToUsers(Long commentId);

    /**
     * @param userId
     * @param commentId
     * @return true if the user has liked the comment, false otherwise.
     */
    @Query(value = "SELECT EXISTS (SELECT FROM review_comment_likes rcl WHERE rcl.user_id = ?1 AND rcl.comment_id = ?2)",
            nativeQuery = true)
    Boolean existsUserLikeForReviewComment(Long userId, Long commentId);

    @Modifying
    @Query(value = "INSERT INTO review_comment_likes VALUES (?1, ?2)", nativeQuery = true)
    void postLikeForReviewComment(Long userId, Long commentId);

    @Modifying
    @Query(value = "DELETE FROM review_comment_likes WHERE user_id = ?1 AND comment_id = ?2", nativeQuery = true)
    void removeLikeForReviewComment(Long userId, Long commentId);
}
