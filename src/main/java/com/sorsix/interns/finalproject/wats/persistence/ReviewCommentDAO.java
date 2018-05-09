package com.sorsix.interns.finalproject.wats.persistence;

import com.sorsix.interns.finalproject.wats.domain.review.ReviewComment;
import org.springframework.data.jpa.repository.JpaRepository;
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
    Collection<ReviewComment> findTopByQuestionId(long reviewId, int size);

    @Query(value = "SELECT COUNT(*) FROM review_comment_likes WHERE comment_id = ?1",
            nativeQuery = true)
    Long countReviewCommentLikes(Long comment_id);

    @Query(value = "SELECT u.id, u.name, u.username FROM users u JOIN review_comment_likes rcl ON u.id = rcl.user_id WHERE rcl.comment_id = ?1",
            nativeQuery = true)
    Collection<Map<String, Object>> getUsersForLikesOnReviewComment(Long commentId);
}
