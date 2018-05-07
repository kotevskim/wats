package com.sorsix.interns.finalproject.wats.api;

import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.forum.ForumAnswer;
import com.sorsix.interns.finalproject.wats.domain.forum.ForumQuestion;
import com.sorsix.interns.finalproject.wats.service.ForumService;
import com.sorsix.interns.finalproject.wats.service.UserService;
import org.hibernate.query.QueryParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/public/locations")
public class ForumController {
    Logger LOGGER = LoggerFactory.getLogger(ForumController.class);

    private ForumService forumService;
    private UserService userService;
    @Autowired
    public ForumController(ForumService forumService,
                           UserService userService) {
        this.forumService = forumService;
        this.userService = userService;
    }

    @GetMapping(value = "{locationId}/forum/questions")
    Page<ForumQuestion> getQuestionsForLocation(@PathVariable int locationId,
                                                Pageable pageable,
                                                Sort sort) {
        LOGGER.info("GET: getPageReviews: [{}]", locationId);

        Page<ForumQuestion> result = forumService.getQuestionsForLocation(locationId, pageable);
        return result;
    }

    @GetMapping(value = "{locationId}/forum/questions/{questionId}")
    ForumQuestion getCurrentQuestion(@PathVariable int locationId,
                                     @PathVariable int questionId) {
        LOGGER.info("GET: getCurrentQuestion: [{}]", questionId);
        ForumQuestion result = forumService.findQuestionById(questionId).orElseThrow(() -> new RuntimeException());
        return result;
    }

    @GetMapping(value = "{locationId}/forum/questions/{questionId}/answers")
    Page<ForumAnswer> getAnswersForQuestion(@PathVariable int questionId,
                                            Pageable pageable,
                                            Sort sort) {
        LOGGER.info("GET: getPageReviews: [{}]", questionId);
        Page<ForumAnswer> result = forumService.getAnswersForQuestion(questionId, pageable);
        return result;
    }

    @GetMapping(value = "{locationId}/forum/questions/{questionId}/answers/top")
    Collection<ForumAnswer> getTopAnswersForQuestion(@PathVariable int questionId,
                                               @RequestParam int quantity) {
        LOGGER.info("GET: getTopAnswersForQuestion: [{}]", questionId);
        Collection<ForumAnswer> result = forumService.getTopAnswersForQuestion(questionId, quantity);
        return result;
    }

    @PostMapping(value = "{locationId}/forum/questions/{questionId}/answers")
    ForumAnswer postAnswerForQuestion(Authentication authentication,
                                      @RequestBody String answer,
                                      @PathVariable long locationId,
                                      @PathVariable long questionId) {
        LOGGER.info("POST: postAnswerForQuestion: id:[{}], answer:[{}]", questionId, answer);

        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();
        User user = userService.findUserById(userId).get();
        ForumQuestion forumQuestion = forumService.findQuestionById(questionId).orElseThrow(() -> new RuntimeException());
        ForumAnswer result = forumService.createAnswerForQuestion(user, forumQuestion, answer);
        return result;
    }
}
