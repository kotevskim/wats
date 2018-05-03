package com.sorsix.interns.finalproject.wats.api;

import com.sorsix.interns.finalproject.wats.domain.Forum.ForumAnswer;
import com.sorsix.interns.finalproject.wats.domain.Forum.ForumQuestion;
import com.sorsix.interns.finalproject.wats.service.ForumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/locations")
public class ForumController {
    Logger LOGGER = LoggerFactory.getLogger(ForumController.class);

    private ForumService forumService;

    @Autowired
    public ForumController(ForumService forumService) {
        this.forumService = forumService;
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

//    @PostMapping(value = "{locationId}/forum/questions/{questionId}/answers")
//    ForumAnswer postAnswerForQuestion(Authentication authentication,
//                                      @RequestBody String answer,
//                                      @PathVariable long locationId,
//                                      @PathVariable long questionId) {
//        LOGGER.info("POST: postAnswerForQuestion: id:[{}], answer:[{}]", questionId, answer);
//        String userIdText = ((Map<String, Object>)authentication.getDetails()).get("userId").toString();
//        long userId = Long.parseLong(userIdText);
//        ForumAnswer result = forumService.createAnswerForQuestion(userId, questionId, answer);
//        return new ForumAnswer();
//    }
}
