package com.freemanpivo.insurancechallenge.api.dto;

import com.freemanpivo.insurancechallenge.core.domain.Price;
import com.freemanpivo.insurancechallenge.core.domain.Product;
import com.freemanpivo.insurancechallenge.core.domain.ProductCandidate;
import com.freemanpivo.insurancechallenge.core.domain.ProductIdentifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ProductResponseDtoTest {

    private final ProductCandidate candidate = new ProductCandidate("Seguro X", "VIDA", BigDecimal.ONE);
    private final Product product = Product.create(
            candidate,
            new Price(candidate.basePrice(), candidate.category()),
            new ProductIdentifier()
    );
    private final ProductResponseDto validResponse = ProductResponseDto.from(product);

    @Test
    public void testDtoResponseCreation() {
        Assertions.assertEquals(validResponse, ProductResponseDto.from(product));
    }

}