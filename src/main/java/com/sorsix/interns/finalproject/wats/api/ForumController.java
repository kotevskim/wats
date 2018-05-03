package com.sorsix.interns.finalproject.wats.api;

import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.forum.ForumAnswer;
import com.sorsix.interns.finalproject.wats.domain.forum.ForumQuestion;
import com.sorsix.interns.finalproject.wats.service.AuthService;
import com.sorsix.interns.finalproject.wats.service.ForumService;
import com.sorsix.interns.finalproject.wats.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/locations")
public class ForumController {
    Logger LOGGER = LoggerFactory.getLogger(ForumController.class);

    private ForumService forumService;
    private UserService userService;
    private AuthService authService;
    @Autowired
    public ForumController(ForumService forumService,
                           UserService userService,
                           AuthService authService) {
        this.forumService = forumService;
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping(value = "{locationId}/forum/questions")
    Collection<ForumQuestion> getQuestionsForLocation(@PathVariable int locationId){
        LOGGER.info("GET: getPageReviews: [{}]", locationId);
        Collection<ForumQuestion> result = forumService.getQuestionsForLocation(locationId);
        return result;
    }

    @GetMapping(value = "{locationId}/forum/questions/{questionId}/answers")
    Collection<ForumAnswer> getAnswersForQuestion(@PathVariable int questionId) {
        LOGGER.info("GET: getPageReviews: [{}]", questionId);
        Collection<ForumAnswer> result = forumService.getAnswersForQuestion(questionId);
        return result;
    }

    @PostMapping(value = "{locationId}/forum/questions/{questionId}/answers")
    ForumAnswer postAnswerForQuestion(Authentication authentication,
                                      @RequestBody String answer,
                                      @PathVariable long locationId,
                                      @PathVariable long questionId) {
        LOGGER.info("POST: postAnswerForQuestion: id:[{}], answer:[{}]", questionId, answer);

        long userId = authService.getCurrentUserId().orElseThrow(() -> new RuntimeException());

        User user = userService.findUserById(userId).orElseThrow(() -> new RuntimeException());
        ForumQuestion forumQuestion = forumService.findQuestionById(questionId).orElseThrow(() -> new RuntimeException());
        ForumAnswer result = forumService.createAnswerForQuestion(user, forumQuestion, answer);
        return result;
    }
}
