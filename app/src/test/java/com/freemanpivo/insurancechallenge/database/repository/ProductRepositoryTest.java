package com.freemanpivo.insurancechallenge.database.repository;

import com.freemanpivo.insurancechallenge.IntegrationTest;
import com.freemanpivo.insurancechallenge.database.entity.ProductEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@IntegrationTest
@ActiveProfiles("integration-test")
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @Test
    void testProductCreation() {
        final var id = UUID.randomUUID();
        final var entity = new ProductEntity(id, "Produto A", "VIDA", BigDecimal.ONE, BigDecimal.ONE);
        repository.save(entity);

        final var isFound = repository.existsById(id);
        Assertions.assertTrue(isFound);

        repository.deleteById(id);
        final var isDeleted = repository.findById(id);
        Assertions.assertEquals(Optional.empty(), isDeleted);
    }

    @Test
    void testProductSearchByName() {
        final var id = UUID.randomUUID();
        final var entity = new ProductEntity(id, "Produto B", "VIDA", BigDecimal.ONE, BigDecimal.ONE);
        repository.save(entity);

        final var isFound = repository.existsById(id);
        final var isFoundByName = repository.findByNameIgnoreCaseAndCategory("ProDUto b", entity.getCategory());
        Assertions.assertTrue(isFound);
        Assertions.assertTrue(isFoundByName.isPresent());

        repository.deleteById(id);
        final var isDeleted = repository.findById(id);
        Assertions.assertEquals(Optional.empty(), isDeleted);
    }
}