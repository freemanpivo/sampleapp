package com.freemanpivo.insurancechallenge.core.exception;

import com.freemanpivo.insurancechallenge.core.commom.ValidationIssue;

import java.util.List;

public class RequestValidationException extends RuntimeException {
    private final List<ValidationIssue> issues;
    private final String message;
    public RequestValidationException(List<ValidationIssue> issues, String message) {
        super();
        this.issues = issues;
        this.message = message;
    }

    public List<ValidationIssue> issues() { return issues; }
    public String message() { return message; }
}
