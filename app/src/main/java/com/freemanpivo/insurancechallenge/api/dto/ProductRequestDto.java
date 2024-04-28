package com.freemanpivo.insurancechallenge.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.freemanpivo.insurancechallenge.core.domain.ProductCandidate;
import com.freemanpivo.insurancechallenge.core.exception.RequestValidationException;

import java.math.BigDecimal;

public record ProductRequestDto(
        @JsonProperty("nome") String name,
        @JsonProperty("categoria") String category,
        @JsonProperty("preco_base") BigDecimal basePrice
) {
    public ProductCandidate toProductSample() throws RequestValidationException {
        return new ProductCandidate(name, category, basePrice);
    }

}
