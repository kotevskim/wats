package com.sorsix.interns.finalproject.wats.api;

import org.springframework.web.bind.annotation.*;

@RestController(value = "/api/locations")
public class ForumController {

    @GetMapping(value = "{id}/forum")
    String getPageReviews(@PathVariable(value = "id") int pageNumber){
        System.out.println("bla bla");
        return "Win";
    }
}
