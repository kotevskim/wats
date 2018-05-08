package com.sorsix.interns.finalproject.wats.service.impl;

import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.forum.ForumAnswer;
import com.sorsix.interns.finalproject.wats.domain.forum.ForumQuestion;
import com.sorsix.interns.finalproject.wats.persistence.ForumAnswerDAO;
import com.sorsix.interns.finalproject.wats.persistence.ForumQuestionsDAO;
import com.sorsix.interns.finalproject.wats.persistence.UserDAO;
import com.sorsix.interns.finalproject.wats.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ForumServiceImpl implements ForumService {

    private ForumQuestionsDAO forumQuestionsDAO;
    private ForumAnswerDAO forumAnswerDAO;
    private UserDAO userDAO;
    @Autowired
    public ForumServiceImpl(ForumQuestionsDAO forumQuestionsDAO,
                            ForumAnswerDAO forumAnswerDAO,
                            UserDAO userDAO) {
        this.forumQuestionsDAO = forumQuestionsDAO;
        this.forumAnswerDAO = forumAnswerDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Page<ForumQuestion> getQuestionsForLocation(long locationId, Pageable pageable) {
        Page<ForumQuestion> result = forumQuestionsDAO.findByLocationId(locationId, pageable);
        return result;
    }

    @Override
    public Page<ForumAnswer> getAnswersForQuestion(long questionId, Pageable pageable) {
        Page<ForumAnswer> result = forumAnswerDAO.findAllByForumQuestionId(questionId, pageable);
        return result;
    }

    @Override
    public ForumAnswer createAnswerForQuestion(User user, ForumQuestion forumQuestion, String answer) {
        ForumAnswer forumAnswer = new ForumAnswer(user, forumQuestion, answer);
        return forumAnswerDAO.save(forumAnswer);
    }

    @Override
    public Optional<ForumQuestion> findQuestionById(long questionId) {
        return forumQuestionsDAO.findById(questionId);
    }

    @Override
    public Collection<ForumAnswer> getTopAnswersForQuestion(int questionId, int quantity) {
//        Collection<ForumAnswer> result = forumAnswerDAO.findAll();
        Collection<ForumAnswer> result = forumAnswerDAO.findTopByQuestionId(questionId, quantity);
        return result;
    }

}
