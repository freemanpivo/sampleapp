package com.freemanpivo.insurancechallenge.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.freemanpivo.insurancechallenge.core.domain.Product;
import com.freemanpivo.insurancechallenge.core.domain.ProductCandidate;

import java.math.BigDecimal;

public record ProductResponseDto(
        @JsonProperty("id") String id,
        @JsonProperty("nome") String name,
        @JsonProperty("categoria") String category,
        @JsonProperty("preco_base") BigDecimal basePrice,
        @JsonProperty("preco_tarifado") BigDecimal chargedPrice
) {
    public static ProductResponseDto from(Product product) {
        return new ProductResponseDto(
                product.id(),
                product.name(),
                product.category(),
                product.basePrice(),
                product.chargedPrice()
        );
    }

}
