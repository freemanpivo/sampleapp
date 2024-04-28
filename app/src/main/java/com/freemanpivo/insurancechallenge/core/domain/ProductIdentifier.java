package com.freemanpivo.insurancechallenge.core.domain;

import java.util.UUID;

public class ProductIdentifier {
    private final String value;

    public ProductIdentifier() {
        this.value = fromUUID();
    }

    public String value() { return value; }

    private String fromUUID() {
        // TODO: Can be improved verifying the database!
        return UUID.randomUUID().toString();
    }
}
