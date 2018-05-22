package com.sorsix.interns.finalproject.wats.api;

import com.sorsix.interns.finalproject.wats.api.exception.*;
import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.forum.ForumAnswer;
import com.sorsix.interns.finalproject.wats.domain.forum.ForumQuestion;
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
@RequestMapping("/api/public")
public class PublicForumController {

    private Logger LOGGER = LoggerFactory.getLogger(PublicForumController.class);

    private final ForumService forumService;
    private final LocationService locationService;

    @Autowired
    public PublicForumController(ForumService forumService,
                                 LocationService locationService) {
        this.forumService = forumService;
        this.locationService = locationService;
    }

    @GetMapping("locations/{locationId}/forum/questions/{questionId}")
    ForumQuestion getQuestion(@PathVariable Long questionId) {
        return forumService.findQuestion(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Forum question", questionId));
    }

    @GetMapping("locations/{locationId}/forum/questions")
    Page<ForumQuestion> getQuestionsForLocation(@PathVariable Long locationId,
                                                Pageable pageable,
                                                Sort sort) {
        return this.locationService.findLocation(locationId)
                .map(it -> forumService.getQuestionsForLocation(locationId, pageable))
                .orElseThrow(() -> new EntityNotFoundException("Location", locationId));
    }

    @GetMapping("locations/{locationId}/forum/questions/{questionId}/answers")
    Page<ForumAnswer> getQuestionAnswers(@PathVariable Long questionId,
                                         Pageable pageable,
                                         Sort sort) {
        return this.forumService.findQuestion(questionId)
                .map(it -> forumService.getQuestionAnswers(questionId, pageable))
                .orElseThrow(() -> new EntityNotFoundException("Forum question", questionId));
    }

    @GetMapping("locations/{locationId}/forum/questions/{questionId}/top-answers")
    Collection<ForumAnswer> getTopAnswersForQuestion(@PathVariable Long questionId,
                                                     @RequestParam int limit) {
        return this.forumService.findQuestion(questionId)
                .map(it -> forumService.getTopAnswersForQuestion(questionId, limit))
                .orElseThrow(() -> new EntityNotFoundException("Forum question", questionId));
    }

    @GetMapping("locations/{locationId}/forum/questions/{questionId}/answers/{answersId}/likes")
    Collection<User> getUsersForLikesOnForumAnswer(@PathVariable Long answersId) {
        return this.forumService.findAnswer(answersId)
                .map(this.forumService::mapAnswerLikesToUsers)
                .orElseThrow(() -> new EntityNotFoundException("Answer", answersId));
    }
}
