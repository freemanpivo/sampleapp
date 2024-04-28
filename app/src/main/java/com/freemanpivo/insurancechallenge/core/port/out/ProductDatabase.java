package com.freemanpivo.insurancechallenge.core.port.out;

import com.freemanpivo.insurancechallenge.core.domain.Product;

import java.util.Optional;

public interface ProductDatabase {
    Optional<Product> findByName(String name);
    Optional<Product> findById(String id);
    void save(Product product);
    void update(Product product);
}
