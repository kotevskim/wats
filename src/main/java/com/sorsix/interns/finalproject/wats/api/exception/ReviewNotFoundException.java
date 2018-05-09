package com.sorsix.interns.finalproject.wats.api.exception;

public class ReviewNotFoundException extends RuntimeException implements NotFoundException {

    public ReviewNotFoundException(Long id) {
        super("Review with id: " + id + " does not exist");
    }


}
