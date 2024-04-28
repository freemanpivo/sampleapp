package com.freemanpivo.insurancechallenge.api.exception;

import com.freemanpivo.insurancechallenge.api.controller.ProductController;
import com.freemanpivo.insurancechallenge.core.exception.RequestValidationException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationControllerAdvice.class);

    @ExceptionHandler(RequestValidationException.class)
    public ProblemDetail handleRequestValidation(HttpServletRequest request, RequestValidationException exception) {
        logger.info("there was validation issue with request in endpoint {}", request.getRequestURI());
        final var response = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        response.setTitle("The request body sent is wrong");
        response.setDetail(exception.getMessage());
        response.setProperty("messages", exception.issues());
        logger.info("end {} {}", request.getMethod(), request.getRequestURI());

        return response;
    }

}
