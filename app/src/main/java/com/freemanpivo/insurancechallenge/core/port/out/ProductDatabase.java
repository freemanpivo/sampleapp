package com.freemanpivo.insurancechallenge.core.port.out;

import com.freemanpivo.insurancechallenge.core.domain.Category;
import com.freemanpivo.insurancechallenge.core.domain.Product;

import java.util.Optional;

public interface ProductDatabase {
    Optional<Product> findByNameAndCategory(String name, Category category);
    Optional<Product> findById(String id);
    void save(Product product);
}
