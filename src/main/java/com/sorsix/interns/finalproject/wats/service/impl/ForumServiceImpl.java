package com.sorsix.interns.finalproject.wats.service.impl;

import com.sorsix.interns.finalproject.wats.domain.Location;
import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.forum.ForumAnswer;
import com.sorsix.interns.finalproject.wats.domain.forum.ForumQuestion;
import com.sorsix.interns.finalproject.wats.persistence.ForumAnswerDAO;
import com.sorsix.interns.finalproject.wats.persistence.ForumQuestionsDAO;
import com.sorsix.interns.finalproject.wats.persistence.UserDAO;
import com.sorsix.interns.finalproject.wats.service.ForumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Service
public class ForumServiceImpl implements ForumService {

    private Logger LOGGER = LoggerFactory.getLogger(ForumServiceImpl.class);

    private ForumQuestionsDAO forumQuestionsDAO;
    private ForumAnswerDAO forumAnswerDAO;

    @Autowired
    public ForumServiceImpl(ForumQuestionsDAO forumQuestionsDAO,
                            ForumAnswerDAO forumAnswerDAO) {
        this.forumQuestionsDAO = forumQuestionsDAO;
        this.forumAnswerDAO = forumAnswerDAO;
    }

    @Override
    public Page<ForumQuestion> getQuestionsForLocation(Long locationId, Pageable pageable) {
        return forumQuestionsDAO.findByLocationId(locationId, pageable);
    }

    @Override
    public Page<ForumAnswer> getQuestionAnswers(Long questionId, Pageable pageable) {
        return forumAnswerDAO.findAllByForumQuestionId(questionId, pageable);
    }

    @Override
    public Optional<ForumQuestion> findQuestion(Long questionId) {
        return forumQuestionsDAO.findById(questionId);
    }

    @Override
    public Optional<ForumAnswer> findAnswer(Long answerId) {
        return this.forumAnswerDAO.findById(answerId);
    }

    @Override
    public Collection<ForumAnswer> getTopAnswersForQuestion(Long questionId, int quantity) {
        return forumAnswerDAO.findTopByQuestionId(questionId, quantity);
    }

    @Override
    public ForumQuestion createQuestion(User user, Location location, String question) {
        LocalDateTime datePublished = LocalDateTime.now();
        ForumQuestion forumQuestion = new ForumQuestion(question, datePublished, user, location);
        ForumQuestion q = this.forumQuestionsDAO.save(forumQuestion);
        LOGGER.info("Created question with id: {}, for location [id: {}, name: {}]",
                q.getId(), q.getLocation().getId(), q.getLocation().getName());
        return q;
    }

    @Override
    public ForumAnswer createAnswerForQuestion(User user, ForumQuestion forumQuestion, String answer) {
        ForumAnswer forumAnswer = new ForumAnswer(user, forumQuestion, answer);
        ForumAnswer a = forumAnswerDAO.save(forumAnswer);
        LOGGER.info("Created comment with id: {}, for review with id: {}", a.getId(), a.getForumQuestion().getId());
        return a;
    }

    @Override
    @Transactional
    public boolean postLikeForAnswer(ForumAnswer answer, User user) {
        boolean res = answer.getLikes().add(user);
        if (res) {
            LOGGER.info("Posted like for comment with id: {}, from user with id: {}",
                    answer.getId(), user.getId());
        } else {
            LOGGER.info("Removed like for comment with id: {}, from user with id: {}",
                    answer.getId(), user.getId());
        }
        return res;
    }

    @Override
    public Collection<User> mapAnswerLikesToUsers(ForumAnswer answer) {
        return answer.getLikes();
    }

}
