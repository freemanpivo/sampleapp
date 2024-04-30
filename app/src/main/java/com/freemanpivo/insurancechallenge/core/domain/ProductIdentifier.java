package com.freemanpivo.insurancechallenge.core.domain;

import com.freemanpivo.insurancechallenge.core.commom.ValidationIssue;
import com.freemanpivo.insurancechallenge.core.exception.RequestValidationException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ProductIdentifier {
    private final String value;

    public ProductIdentifier() {
        this.value = fromUUID();
    }

    private ProductIdentifier(String id) {
        this.value = id;
    }

    public static ProductIdentifier from(String id) {
        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            final var issue = new ValidationIssue("product_id", "invalid uuid hash", List.of());
            throw new RequestValidationException(
                    List.of(issue),
                    "Invalid parameter at request path"
            );
        }

        return new ProductIdentifier(id);
    }

    public String value() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductIdentifier that = (ProductIdentifier) o;

        return Objects.equals(value, that.value);
    }

    private String fromUUID() {
        // TODO: Can be improved verifying the database!
        return UUID.randomUUID().toString();
    }
}
