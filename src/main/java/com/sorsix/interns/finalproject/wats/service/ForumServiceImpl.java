package com.sorsix.interns.finalproject.wats.service;

import com.sorsix.interns.finalproject.wats.domain.Forum.ForumAnswer;
import com.sorsix.interns.finalproject.wats.domain.Forum.ForumQuestion;
import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.persistence.ForumAnswerDAO;
import com.sorsix.interns.finalproject.wats.persistence.ForumQuestionsDAO;
import com.sorsix.interns.finalproject.wats.persistence.UserDao;
import org.dom4j.util.UserDataDocumentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

@Service
public class ForumServiceImpl implements ForumService{

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

//    @Override
//    public ForumAnswer createAnswerForQuestion(long userId, long questionId, String text) {
//        return answer;
//    }
}
