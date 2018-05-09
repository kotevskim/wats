package com.sorsix.interns.finalproject.wats.api;

import com.sorsix.interns.finalproject.wats.api.exception.ForumAnswerNotFoundException;
import com.sorsix.interns.finalproject.wats.api.exception.ForumQuestionNotFoundException;
import com.sorsix.interns.finalproject.wats.api.exception.LocationNotFoundException;
import com.sorsix.interns.finalproject.wats.api.exception.ReviewCommentNotFoundException;
import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.forum.ForumAnswer;
import com.sorsix.interns.finalproject.wats.domain.forum.ForumQuestion;
import com.sorsix.interns.finalproject.wats.service.AuthenticationService;
import com.sorsix.interns.finalproject.wats.service.ForumService;
import com.sorsix.interns.finalproject.wats.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class ForumController {

    private Logger LOGGER = LoggerFactory.getLogger(ForumController.class);

    private final ForumService forumService;
    private final AuthenticationService authenticationService;
    private final LocationService locationService;

    @Autowired
    public ForumController(ForumService forumService,
                           AuthenticationService authenticationService,
                           LocationService locationService) {
        this.forumService = forumService;
        this.authenticationService = authenticationService;
        this.locationService = locationService;
    }

    @GetMapping("public/locations/{locationId}/forum/questions/{questionId}")
    ForumQuestion getQuestion(@PathVariable Long questionId) {
        return forumService.findQuestion(questionId)
                .orElseThrow(() -> new ForumQuestionNotFoundException(questionId));
    }

    @GetMapping("public/locations/{locationId}/forum/questions")
    Page<ForumQuestion> getQuestionsForLocation(@PathVariable Long locationId,
                                                Pageable pageable,
                                                Sort sort) {
        return this.locationService.findLocation(locationId)
                .map(it -> forumService.getQuestionsForLocation(locationId, pageable))
                .orElseThrow(() -> new LocationNotFoundException(locationId));
    }

    @GetMapping("public/locations/{locationId}/forum/questions/{questionId}/answers")
    Page<ForumAnswer> getQuestionAnswers(@PathVariable Long questionId,
                                         Pageable pageable,
                                         Sort sort) {
        return this.forumService.findQuestion(questionId)
                .map(it -> forumService.getQuestionAnswers(questionId, pageable))
                .orElseThrow(() -> new ForumQuestionNotFoundException(questionId));
    }

    @GetMapping("public/locations/{locationId}/forum/questions/{questionId}/top-answers")
    Collection<ForumAnswer> getTopAnswersForQuestion(@PathVariable Long questionId,
                                                     @RequestParam int limit) {
        return this.forumService.findQuestion(questionId)
                .map(it -> forumService.getTopAnswersForQuestion(questionId, limit))
                .orElseThrow(() -> new ForumQuestionNotFoundException(questionId));
    }

    @GetMapping("public/locations/{locationId}/forum/questions/{questionId}/answers/{answersId}/likes")
    Collection<User> getUsersForLikesOnForumAnswer(@PathVariable Long answersId) {
        return this.forumService.findAnswer(answersId)
                .map(this.forumService::mapAnswerLikesToUsers)
                .orElseThrow(() -> new ReviewCommentNotFoundException(answersId));
    }

    @PostMapping("locations/{locationId}/forum/questions")
    ForumQuestion postQuestion(@RequestBody String question,
                               @PathVariable Long locationId) {
        return this.locationService.findLocation(locationId)
                .map(location -> {
                    User user = this.authenticationService.getUser();
                    return forumService.createQuestion(user, location, question);
                })
                .orElseThrow(() -> new RuntimeException("invalid location id"));
    }

    @PostMapping("locations/{locationId}/forum/questions/{questionId}/answers")
    ForumAnswer postAnswerForQuestion(@RequestBody String answer,
                                      @PathVariable Long questionId) {
        return this.forumService.findQuestion(questionId)
                .map(question -> {
                    User user = this.authenticationService.getUser();
                    return forumService.createAnswerForQuestion(user, question, answer);
                })
                .orElseThrow(() -> new ForumQuestionNotFoundException(questionId));
    }

    @PostMapping("locations/{locationId}/forum/questions/{questionId}/answers/{answerId}/likes")
    boolean likeAnswer(@PathVariable Long answerId) {
        return this.forumService.findAnswer(answerId)
                .map(answer -> {
                    User user = authenticationService.getUser();
                    return this.forumService.postLikeForAnswer(answer, user);
                })
                .orElseThrow(() -> new ForumAnswerNotFoundException(answerId));
    }
}
