package com.freemanpivo.insurancechallenge.core.domain;

import com.freemanpivo.insurancechallenge.UnitTest;
import com.freemanpivo.insurancechallenge.core.exception.RequestValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@UnitTest
class ProductIdentifierTest {

    @Test
    void testCreationWithDefaultConstructor() {
        Assertions.assertDoesNotThrow(ProductIdentifier::new);
    }

    @Test
    void testCreationWithStaticFromStringMethodWithSuccess() {
        String uuidString = UUID.randomUUID().toString();
        Assertions.assertDoesNotThrow(() -> ProductIdentifier.from(uuidString));
    }

    @Test
    void testCreationWithStaticFromStringMethodWithError() {
        String uuidString = "MY FAKE ID";
        Assertions.assertThrows(RequestValidationException.class, () -> ProductIdentifier.from(uuidString));
    }

}