package com.freemanpivo.insurancechallenge.api.dto;

import com.freemanpivo.insurancechallenge.UnitTest;
import com.freemanpivo.insurancechallenge.core.domain.ProductCandidate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

@UnitTest
class ProductRequestDtoTest {

    private final ProductRequestDto validDto = new ProductRequestDto("nome", "VIDA", new BigDecimal("10.00"));
    private final ProductRequestDto invalidDto = new ProductRequestDto(null, null, null);
    private final ProductCandidate candidate = validDto.toProductSample();

    @Test
    public void testDtoCreationWithValidFields() {
        Assertions.assertEquals(validDto, new ProductRequestDto("nome", "VIDA", new BigDecimal("10.00")));
    }

    @Test
    public void testDtoCreationWithInvalidFields() {
        Assertions.assertEquals(invalidDto, new ProductRequestDto(null, null, null));
    }

    @Test
    public void testValidCandidateCreationByDto() {
        Assertions.assertEquals(candidate, validDto.toProductSample());
    }
}