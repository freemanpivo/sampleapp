package com.freemanpivo.insurancechallenge.api.exception;

import com.freemanpivo.insurancechallenge.UnitTest;
import com.freemanpivo.insurancechallenge.core.domain.Price;
import com.freemanpivo.insurancechallenge.core.domain.Product;
import com.freemanpivo.insurancechallenge.core.domain.ProductCandidate;
import com.freemanpivo.insurancechallenge.core.domain.ProductIdentifier;
import com.freemanpivo.insurancechallenge.core.exception.ExceptionFlow;
import com.freemanpivo.insurancechallenge.core.exception.RequestValidationException;
import com.freemanpivo.insurancechallenge.core.exception.ResourceAlreadyCreatedException;
import com.freemanpivo.insurancechallenge.core.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.mock.web.MockHttpServletRequest;

import java.math.BigDecimal;
import java.util.List;

@UnitTest
class ApplicationControllerAdviceTest {
    private final ApplicationControllerAdvice handler = new ApplicationControllerAdvice();
    private final ProductCandidate sample = new ProductCandidate("Valid Name", "VIDA", BigDecimal.ONE);
    private final Product product = Product.create(sample, new Price(sample.basePrice(), sample.category()), new ProductIdentifier());

    @Test
    public void testValidationException() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        final var exception = new RequestValidationException(List.of(), "error message");

        final var expectedProblem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        expectedProblem.setTitle("The request body sent is wrong");
        expectedProblem.setDetail(exception.message());
        expectedProblem.setProperty("messages", exception.issues());

        Assertions.assertEquals(expectedProblem, handler.handleRequestValidation(request, exception));
    }

    @Test
    public void testResourceNotFoundException() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        final var exception = new ResourceNotFoundException(ExceptionFlow.SEARCH, product.id());

        final var expectedProblem = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        expectedProblem.setTitle("Not recognized product_id");
        expectedProblem.setDetail(exception.message());

        Assertions.assertEquals(expectedProblem, handler.handleResourceNotFoundException(request, exception));
    }

    @Test
    public void testResourceAlreadyCreatedException() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        final var exception = new ResourceAlreadyCreatedException(product, product.id());

        final var expectedProblem = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        expectedProblem.setTitle("The product can't be created");
        expectedProblem.setDetail(exception.message());

        Assertions.assertEquals(expectedProblem, handler.handleResourceAlreadyCreatedException(request, exception));
    }

}