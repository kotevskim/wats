package com.sorsix.interns.finalproject.wats.api.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String entityType, Long id) {
        super(entityType + "  with id: " + id + " does not exist");
    }

    public EntityNotFoundException(String entityType, String name) {
        super(entityType + "  with name: " + name + " does not exist");
    }
}
