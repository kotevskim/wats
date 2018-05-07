package com.sorsix.interns.finalproject.wats.persistence;

import com.sorsix.interns.finalproject.wats.domain.review.ReviewComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ReviewCommentDao extends JpaRepository<ReviewComment, Long> {

    Collection<ReviewComment> findByReviewId(Long id);

    @Query(value =
            "SELECT temp_result.id, temp_result.description, temp_result.date_published, \n" +
                    " \ttemp_result.user_id, temp_result.forum_question_id \n" +
                    "FROM \n" +
                    "(\n" +
                    "SELECT fa.*, tmp_count.* from forum_answers fa\n" +
                    "JOIN\n" +
                    "(\n" +
                    "    SELECT foo.id as answer_id, COUNT(*) as num_likes\n" +
                    "    FROM (\n" +
                    "        SELECT fa.* from forum_answers fa \n" +
                    "        JOIN forum_answer_likes fal \n" +
                    "        ON fa.id=fal.forum_answer_id\n" +
                    "    ) foo \n" +
                    "    WHERE foo.forum_question_id=?1\n" +
                    "    GROUP BY foo.id\n" +
                    ") tmp_count\n" +
                    "ON fa.id=tmp_count.answer_id\n" +
                    ") temp_result\n" +
                    "ORDER BY temp_result.num_likes DESC\n" +
                    "LIMIT ?2",
            nativeQuery = true)
    Collection<ReviewComment> findTopByQuestionId(long questionId, int quantity);
}
