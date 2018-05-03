package com.sorsix.interns.finalproject.wats.persistence;

import com.sorsix.interns.finalproject.wats.domain.forum.ForumAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ForumAnswerDAO extends JpaRepository<ForumAnswer, Long> {
    Collection<ForumAnswer> findAllByForumQuestionId(long questionId);

}
