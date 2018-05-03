package com.sorsix.interns.finalproject.wats.service;

import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.forum.ForumAnswer;
import com.sorsix.interns.finalproject.wats.domain.forum.ForumQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Optional;

public interface ForumService {

    Page<ForumQuestion> getQuestionsForLocation(long locationId, Pageable pageable);

    Collection<ForumAnswer> getAnswersForQuestion(long questionId);

    ForumAnswer createAnswerForQuestion(User user, ForumQuestion forumQuestion, String answer);

    Optional<ForumQuestion> findQuestionById(long questionId);
}
