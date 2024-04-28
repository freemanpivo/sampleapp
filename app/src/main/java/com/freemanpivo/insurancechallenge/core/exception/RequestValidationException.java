package com.freemanpivo.insurancechallenge.core.exception;

import com.freemanpivo.insurancechallenge.core.commom.ValidationIssue;

import java.util.List;

public class RequestValidationException extends RuntimeException {
    private final List<ValidationIssue> issues;
    public RequestValidationException(List<ValidationIssue> issues, String message) {
        super(message);
        this.issues = issues;
    }

    public List<ValidationIssue> issues() { return issues; }
}
