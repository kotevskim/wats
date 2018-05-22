package com.sorsix.interns.finalproject.wats.persistence;

import com.sorsix.interns.finalproject.wats.domain.forum.ForumAnswer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ForumAnswerDAO extends JpaRepository<ForumAnswer, Long> {

    Page<ForumAnswer> findAllByForumQuestionId(long questionId, Pageable pageable);

//    @Query(value = "SELECT COUNT(*) FROM (SELECT a.* from forum_answers a " +
//            "JOIN forum_answer_likes fal ON a.id=fal.forum_answer_id) foo GROUP BY foo.forum_question_id",

//    @Query(value = "SELECT fa.* FROM forum_answers fa",
//            nativeQuery = true)

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
        Collection<ForumAnswer> findTopByQuestionId(@Param("questionId") long questionId, @Param("numItems") int numItems);

    /**
     * @param userId
     * @param answerId
     * @return true if the user has liked the answer, false otherwise.
     */
    @Query(value = "SELECT EXISTS (SELECT FROM forum_answer_likes fal WHERE fal.user_id = ?1 AND fal.forum_answer_id = ?2)",
            nativeQuery = true)
    Boolean existsUserLikeForForumAnswer(Long userId, Long answerId);

    @Modifying
    @Query(value = "INSERT INTO forum_answer_likes VALUES (?1, ?2)", nativeQuery = true)
    void postLikeForForumAnswer(Long userId, Long answerId);

    @Modifying
    @Query(value = "DELETE FROM forum_answer_likes WHERE user_id = ?1 AND forum_answer_id = ?2", nativeQuery = true)
    void removeLikeForForumAnswer(Long userId, Long answerId);
}
