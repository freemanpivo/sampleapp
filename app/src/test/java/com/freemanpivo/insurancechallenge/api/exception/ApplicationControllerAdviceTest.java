package com.freemanpivo.insurancechallenge.api.exception;

import com.freemanpivo.insurancechallenge.UnitTest;
import com.freemanpivo.insurancechallenge.core.exception.RequestValidationException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.List;

@UnitTest
class ApplicationControllerAdviceTest {
    private final ApplicationControllerAdvice handler = new ApplicationControllerAdvice();

    @Test
    public void testValidationException() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        final var exception = new RequestValidationException(List.of(), "error message");

        final var expectedProblem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        expectedProblem.setTitle("The request body sent is wrong");
        expectedProblem.setDetail(exception.getMessage());
        expectedProblem.setProperty("messages", exception.issues());

        Assertions.assertEquals(expectedProblem, handler.handleRequestValidation(request, exception));
    }

}