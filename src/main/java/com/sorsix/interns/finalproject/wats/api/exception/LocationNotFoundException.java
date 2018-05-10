package com.sorsix.interns.finalproject.wats.api.exception;

public class LocationNotFoundException extends RuntimeException implements NotFoundException {

    public LocationNotFoundException(Long id) {
        super("Location with id: " + id + " does not exist");
    }

    public LocationNotFoundException(String name) {
        super("Location with name: " + name + " does not exist");
    }
}
