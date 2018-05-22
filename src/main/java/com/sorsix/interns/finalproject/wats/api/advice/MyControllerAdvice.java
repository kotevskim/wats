package com.sorsix.interns.finalproject.wats.api.advice;

import com.sorsix.interns.finalproject.wats.api.exception.*;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler({
            EntityNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    VndErrors.VndError entityNotFoundExceptionHandler(EntityNotFoundException ex) {
        return new VndErrors.VndError("error", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    VndErrors.VndError genericExceptionHandler(Exception ex) {
        return new VndErrors.VndError("error", ex.getMessage());
    }
}
