package com.freemanpivo.insurancechallenge.database.operation;

import com.freemanpivo.insurancechallenge.IntegrationTest;
import com.freemanpivo.insurancechallenge.core.domain.Category;
import com.freemanpivo.insurancechallenge.core.exception.ResourceAlreadyCreatedException;
import com.freemanpivo.insurancechallenge.core.exception.ResourceNotFoundException;
import com.freemanpivo.insurancechallenge.database.entity.ProductEntity;
import com.freemanpivo.insurancechallenge.database.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@IntegrationTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("integration-test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatabaseManipulationIntegrationTest {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private DatabaseManipulation database = new DatabaseManipulation(repository);
    private final UUID idEntityA = UUID.randomUUID();
    private final ProductEntity entityA = new ProductEntity(idEntityA, "Produto A", "VIDA", BigDecimal.ONE, BigDecimal.ONE);
    private final UUID idEntityB = UUID.randomUUID();
    private final ProductEntity entityB = new ProductEntity(idEntityB, "Produto B", "VIDA", BigDecimal.TEN, BigDecimal.TEN);


    @BeforeAll
    void setup() {
        repository.save(entityA);
    }

    @AfterAll
    void tearDown() {
        repository.deleteById(idEntityA);
        repository.deleteById(idEntityB);
    }

    @Test
    void findByNameAndCategory() {
        final var isFound = database.findByNameAndCategory("produto a", Category.from("vida"));

        Assertions.assertTrue(isFound.isPresent());
    }

    @Test
    void findById() {
        final var isFound = database.findById(idEntityA.toString());

        Assertions.assertTrue(isFound.isPresent());
    }

    @Test
    void save() {
        Assertions.assertDoesNotThrow(() -> database.save(ProductEntity.to(entityB)));
    }
}