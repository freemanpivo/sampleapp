package com.freemanpivo.insurancechallenge.database.operation;

import com.freemanpivo.insurancechallenge.api.controller.ProductController;
import com.freemanpivo.insurancechallenge.core.domain.Product;
import com.freemanpivo.insurancechallenge.core.exception.ExceptionFlow;
import com.freemanpivo.insurancechallenge.core.exception.ResourceAlreadyCreatedException;
import com.freemanpivo.insurancechallenge.core.exception.ResourceNotFoundException;
import com.freemanpivo.insurancechallenge.core.port.out.ProductDatabase;
import com.freemanpivo.insurancechallenge.database.entity.ProductEntity;
import com.freemanpivo.insurancechallenge.database.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class DatabaseManipulation implements ProductDatabase {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseManipulation.class);
    private final ProductRepository repository;

    public DatabaseManipulation(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Product> findByName(String name) {
        logger.info("querying products table by name...");
        final var isProduct = repository.findByNameIgnoreCase(name);
        if (isProduct.isEmpty()) return Optional.empty();
        final var product = ProductEntity.to(isProduct.get());
        logger.info("end querying products table by name.");

        return Optional.of(product);
    }

    @Override
    public Optional<Product> findById(String id) {
        logger.info("querying products table by ID...");
        final var isProduct = repository.findById(UUID.fromString(id));
        if (isProduct.isEmpty()) return Optional.empty();
        final var product = ProductEntity.to(isProduct.get());
        logger.info("end querying products table by ID.");

        return Optional.of(product);
    }

    @Override
    public void save(Product product) {
        logger.info("start entitity with name [{}] creation...", product.name());
        final var entity = repository.findById(UUID.fromString(product.id()));
        if (entity.isPresent()) throw new ResourceAlreadyCreatedException(product);
        repository.save(ProductEntity.from(product));
        logger.info("the entity [{}] was successfully created with id {}", product.name(), product.id());
    }

    @Override
    public void update(Product product) {
        logger.info("start entitity with name [{}] update...", product.name());
        final var entity = repository.findById(UUID.fromString(product.id()));
        if (entity.isEmpty()) throw new ResourceNotFoundException(ExceptionFlow.UPDATE, product.id());
        repository.save(ProductEntity.from(product));
        logger.info("the entity [{}] was successfully updated with id {}", product.name(), product.id());
    }
}
