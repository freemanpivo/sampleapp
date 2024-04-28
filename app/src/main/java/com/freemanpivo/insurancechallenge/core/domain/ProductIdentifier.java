package com.freemanpivo.insurancechallenge.core.domain;

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
        return new ProductIdentifier(id);
    }

    public String value() { return value; }

    private String fromUUID() {
        // TODO: Can be improved verifying the database!
        return UUID.randomUUID().toString();
    }
}
