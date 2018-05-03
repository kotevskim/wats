package com.sorsix.interns.finalproject.wats.service;

import com.sorsix.interns.finalproject.wats.domain.Forum.ForumAnswer;
import com.sorsix.interns.finalproject.wats.domain.Forum.ForumQuestion;

import java.security.Principal;
import java.util.Collection;

public interface ForumService {

    Collection<ForumQuestion> getQuestionsForLocation(long locationId);

    Collection<ForumAnswer> getAnswersForQuestion(long questionId);

//    ForumAnswer createAnswerForQuestion(long userId, long questionId, String answer);
}
