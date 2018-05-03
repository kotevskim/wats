package com.sorsix.interns.finalproject.wats.service;

import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.forum.ForumAnswer;
import com.sorsix.interns.finalproject.wats.domain.forum.ForumQuestion;

import java.util.Collection;
import java.util.Optional;

public interface ForumService {

    Collection<ForumQuestion> getQuestionsForLocation(long locationId);

    Collection<ForumAnswer> getAnswersForQuestion(long questionId);

    ForumAnswer createAnswerForQuestion(User user, ForumQuestion forumQuestion, String answer);

    Optional<ForumQuestion> findQuestionById(long questionId);
}
