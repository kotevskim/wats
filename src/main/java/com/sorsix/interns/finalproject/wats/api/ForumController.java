package com.sorsix.interns.finalproject.wats.api;

import com.sorsix.interns.finalproject.wats.domain.Forum.ForumAnswer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController(value = "/api/locations")
public class ForumController {

    @GetMapping(value = "{id}/forum")
    String getPageReviews(@PathVariable(value = "id") int pageNumber){
        System.out.println("bla bla");
        return "Win";
    }
}
