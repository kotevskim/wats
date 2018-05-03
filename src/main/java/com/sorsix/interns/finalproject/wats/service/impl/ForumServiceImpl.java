package com.sorsix.interns.finalproject.wats.service.impl;

import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.forum.ForumAnswer;
import com.sorsix.interns.finalproject.wats.domain.forum.ForumQuestion;
import com.sorsix.interns.finalproject.wats.persistence.ForumAnswerDAO;
import com.sorsix.interns.finalproject.wats.persistence.ForumQuestionsDAO;
import com.sorsix.interns.finalproject.wats.persistence.UserDao;
import com.sorsix.interns.finalproject.wats.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ForumServiceImpl implements ForumService {

    private ForumQuestionsDAO forumQuestionsDAO;
    private ForumAnswerDAO forumAnswerDAO;
    private UserDao userDao;
    @Autowired
    public ForumServiceImpl(ForumQuestionsDAO forumQuestionsDAO,
                            ForumAnswerDAO forumAnswerDAO,
                            UserDao userDao) {
        this.forumQuestionsDAO = forumQuestionsDAO;
        this.forumAnswerDAO = forumAnswerDAO;
        this.userDao = userDao;
    }

    @Override
    public Collection<ForumQuestion> getQuestionsForLocation(long locationId) {
        Collection<ForumQuestion> result = forumQuestionsDAO.findByLocationId(locationId);
        return result;
    }

    @Override
    public Collection<ForumAnswer> getAnswersForQuestion(long questionId) {
        Collection<ForumAnswer> result = forumAnswerDAO.findAllByForumQuestionId(questionId);
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


}
