package com.freemanpivo.insurancechallenge.core.exception;

import com.freemanpivo.insurancechallenge.core.domain.Product;

public class ResourceAlreadyCreatedException extends RuntimeException {
    private final String message;
    public ResourceAlreadyCreatedException(Product product) {
        super();
        message = String.format("Product with name %s already created with id %s", product.name(), product.id());
    }

    public String message() { return message; }
}
