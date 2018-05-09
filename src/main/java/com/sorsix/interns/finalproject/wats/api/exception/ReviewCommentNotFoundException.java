package com.sorsix.interns.finalproject.wats.api.exception;

public class ReviewCommentNotFoundException extends RuntimeException implements  NotFoundException {

    public ReviewCommentNotFoundException(Long id) {
        super("Review comment with id: " + id + " does not exist");
    }
}
