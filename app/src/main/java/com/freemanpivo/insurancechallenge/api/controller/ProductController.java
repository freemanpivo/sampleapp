package com.freemanpivo.insurancechallenge.api.controller;

import com.freemanpivo.insurancechallenge.api.dto.ProductRequestDto;
import com.freemanpivo.insurancechallenge.api.dto.ProductResponseDto;
import com.freemanpivo.insurancechallenge.core.port.CreateProduct;
import com.freemanpivo.insurancechallenge.core.port.UpdateProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final CreateProduct productRegistration;
    private final UpdateProduct productUpdate;

    public ProductController(CreateProduct createUsecase, UpdateProduct updateProduct) {
        this.productRegistration = createUsecase;
        this.productUpdate = updateProduct;
    }

    @PostMapping(name = "createProduct", value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponseDto> create(@RequestBody ProductRequestDto productBody) throws Exception {
        logger.info("start POST /products...");
        final var product = productRegistration.create(productBody.toProductSample());
        final var response = ProductResponseDto.from(product);
        logger.info("end POST /products.");

        return ResponseEntity.ok(response);
    }

    @PutMapping(name = "updateProduct", value = "/products/{product_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@PathVariable("product_id") String id) {
        return ResponseEntity.ok("OK -- TBD");
    }
}
