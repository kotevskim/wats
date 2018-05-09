package com.sorsix.interns.finalproject.wats.api.exception;

public class ForumAnswerNotFoundException extends RuntimeException implements NotFoundException {

    public ForumAnswerNotFoundException(Long id) {
        super("Answer with id: " + id + " does not exist");
    }
}
