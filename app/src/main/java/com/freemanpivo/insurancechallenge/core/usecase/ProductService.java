package com.freemanpivo.insurancechallenge.core.usecase;

import com.freemanpivo.insurancechallenge.core.domain.Price;
import com.freemanpivo.insurancechallenge.core.domain.Product;
import com.freemanpivo.insurancechallenge.core.domain.ProductCandidate;
import com.freemanpivo.insurancechallenge.core.domain.ProductIdentifier;
import com.freemanpivo.insurancechallenge.core.port.in.CreateProduct;
import com.freemanpivo.insurancechallenge.core.port.in.UpdateProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements CreateProduct, UpdateProduct {
    private static final Logger logger = LoggerFactory.getLogger(ProductCandidate.class);
    @Override
    public Product create(ProductCandidate sample) {
        logger.info("Creating product with name [{}] ...", sample.name());
        final var price = new Price(sample.basePrice(), sample.category());
        final var identifier = new ProductIdentifier();
        final var product = Product.create(sample, price, identifier);
        logger.info("Product with name [{}] was successfully created with id={}", sample.name(), identifier.value());

        // TODO: Call repository!

        return product;
    }

    @Override
    public Product update(String id, ProductCandidate sample) {
        return null;
    }
}
