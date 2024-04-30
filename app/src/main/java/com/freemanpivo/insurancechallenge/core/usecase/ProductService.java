package com.freemanpivo.insurancechallenge.core.usecase;

import com.freemanpivo.insurancechallenge.core.domain.*;
import com.freemanpivo.insurancechallenge.core.exception.ExceptionFlow;
import com.freemanpivo.insurancechallenge.core.exception.ResourceAlreadyCreatedException;
import com.freemanpivo.insurancechallenge.core.exception.ResourceNotFoundException;
import com.freemanpivo.insurancechallenge.core.port.in.CreateProduct;
import com.freemanpivo.insurancechallenge.core.port.in.UpdateProduct;
import com.freemanpivo.insurancechallenge.core.port.out.ProductDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements CreateProduct, UpdateProduct {
    private static final Logger logger = LoggerFactory.getLogger(ProductCandidate.class);
    private final ProductDatabase database;

    public ProductService(ProductDatabase database) {
        this.database = database;
    }

    @Override
    public Product create(ProductCandidate sample) {
        logger.info("Creating product DOMAIN OBJ with name [{}] ...", sample.name());
        final var price = new Price(sample.basePrice(), sample.category());
        final var identifier = new ProductIdentifier();
        final var product = Product.create(sample, price, identifier);
        logger.info("Product DOMAIN OBJ with name [{}] was successfully created with id={}", sample.name(), identifier.value());

        logger.info("verifying existence of product with id [{}]...", product.id());
        productVerificationAtSaveFlow(sample, product);
        database.save(product);

        return product;
    }

    @Override
    public Product update(String id, ProductCandidate sample) {
        logger.info("Updating product DOMAIN OBJ with name=[{}] and  id=[{}] ...", sample.name(), id);
        final var price = new Price(sample.basePrice(), sample.category());
        final var identifier = ProductIdentifier.from(id);
        final var product = Product.create(sample, price, identifier);
        logger.info("Product DOMAIN OBJ with name [{}] was successfully created with id={}", sample.name(), identifier.value());

        logger.info("verifying existence of product with id [{}]...", product.id());
        productVerificationAtUpdateFlow(product);

        database.save(product);

        return product;
    }

    private void productVerificationAtSaveFlow(ProductCandidate sample, Product product) {
        database.findByNameAndCategory(sample.name(), sample.category()).ifPresentOrElse(
                (value) -> {
                    // TODO: metrificar -> clientes impactados
                    logger.warn("product with id [{}] already exists!", product.id());
                    throw new ResourceAlreadyCreatedException(product, value.id());
                },
                () -> {}
        );
    }

    private void productVerificationAtUpdateFlow(Product product) {
        database.findById(product.id()).ifPresentOrElse(
                (value) -> {},
                () -> {
                    // TODO: metrificar -> clientes impactados
                    logger.warn("product with id [{}] doesn't exists!", product.id());
                    throw new ResourceNotFoundException(ExceptionFlow.UPDATE, product.id());
                });

        database.findByNameAndCategory(product.name(), Category.from(product.category())).ifPresentOrElse(
                (value) -> {
                    // TODO: metrificar -> clientes impactados
                    logger.warn("product with id [{}] already exists!", product.id());
                    throw new ResourceAlreadyCreatedException(product, value.id());
                },
                () -> {}
        );
    }
}
