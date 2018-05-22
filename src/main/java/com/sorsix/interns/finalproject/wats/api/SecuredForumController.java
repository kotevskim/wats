package com.sorsix.interns.finalproject.wats.api;

import com.sorsix.interns.finalproject.wats.api.exception.EntityNotFoundException;
import com.sorsix.interns.finalproject.wats.domain.User;
import com.sorsix.interns.finalproject.wats.domain.forum.ForumAnswer;
import com.sorsix.interns.finalproject.wats.domain.forum.ForumQuestion;
import com.sorsix.interns.finalproject.wats.service.AuthenticationService;
import com.sorsix.interns.finalproject.wats.service.ForumService;
import com.sorsix.interns.finalproject.wats.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SecuredForumController {

    private Logger LOGGER = LoggerFactory.getLogger(PublicForumController.class);

    private final ForumService forumService;
    private final LocationService locationService;
    private final AuthenticationService authenticationService;

    @Autowired
    public SecuredForumController(ForumService forumService,
                                  LocationService locationService,
                                  AuthenticationService authenticationService) {
        this.forumService = forumService;
        this.locationService = locationService;
        this.authenticationService = authenticationService;
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
                .orElseThrow(() -> new EntityNotFoundException("Forum question", questionId));
    }

    @PostMapping("locations/{locationId}/forum/questions/{questionId}/answers/{answerId}/likes")
    boolean likeAnswer(@PathVariable Long answerId) {
        return this.forumService.findAnswer(answerId)
                .map(answer -> {
                    User user = authenticationService.getUser();
                    boolean hasLiked = forumService.hasUserLikedForumAnswer(user.getId(), answerId);
                    if (hasLiked) forumService.dislikeForumAnswer(user.getId(), answerId);
                    else forumService.likeForumAnswer(user.getId(), answerId);
                    return !hasLiked;
                })
                .orElseThrow(() -> new EntityNotFoundException("Forum answer", answerId));
    }


}
