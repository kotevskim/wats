package com.sorsix.interns.finalproject.wats.service;

import com.sorsix.interns.finalproject.wats.domain.Location;
import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.forum.ForumAnswer;
import com.sorsix.interns.finalproject.wats.domain.forum.ForumQuestion;
import com.sorsix.interns.finalproject.wats.domain.review.Review;
import com.sorsix.interns.finalproject.wats.domain.review.ReviewComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Optional;

public interface ForumService {

    Page<ForumQuestion> getQuestionsForLocation(Long locationId, Pageable pageable);

    Page<ForumAnswer> getQuestionAnswers(Long questionId, Pageable pageable);

    ForumQuestion createQuestion(User user, Location location, String question);

    ForumAnswer createAnswerForQuestion(User user, ForumQuestion forumQuestion, String answer);

    Optional<ForumQuestion> findQuestion(Long questionId);

    Optional<ForumAnswer> findAnswer(Long answerId);

    Collection<ForumAnswer> getTopAnswersForQuestion(Long questionId, int quantity);

    Collection<User> mapAnswerLikesToUsers(ForumAnswer anser);

    boolean hasUserLikedForumAnswer(Long userId, Long answerId);

    void likeForumAnswer(Long userId, Long answerId);

    void dislikeForumAnswer(Long userId, Long answerId);


}
