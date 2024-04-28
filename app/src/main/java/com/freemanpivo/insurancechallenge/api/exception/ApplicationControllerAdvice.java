package com.freemanpivo.insurancechallenge.api.exception;

import com.freemanpivo.insurancechallenge.core.exception.RequestValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RequestValidationException.class)
    public ProblemDetail handleRequestValidation(RequestValidationException exception) {
        final var response = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        response.setTitle("The request body sent is wrong");
        response.setDetail(exception.getMessage());
        response.setProperty("messages", exception.issues());

        return response;
    }

}
