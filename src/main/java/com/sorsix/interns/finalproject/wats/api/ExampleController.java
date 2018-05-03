package com.sorsix.interns.finalproject.wats.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("proba")
public class ExampleController {

    @RequestMapping(value = "meth", method = RequestMethod.GET)
    String getPageReviews(){
        System.out.println("bla bla");
        return "Win";
    }
}
