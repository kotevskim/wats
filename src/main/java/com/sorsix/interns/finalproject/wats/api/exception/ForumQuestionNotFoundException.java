package com.sorsix.interns.finalproject.wats.api.exception;

public class ForumQuestionNotFoundException extends RuntimeException implements NotFoundException {

    public ForumQuestionNotFoundException(Long id) {
        super("Question with id: " + id + " does not exist");
    }
}
