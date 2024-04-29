package com.freemanpivo.insurancechallenge.database.operation;

import com.freemanpivo.insurancechallenge.IntegrationTest;
import com.freemanpivo.insurancechallenge.core.exception.ResourceAlreadyCreatedException;
import com.freemanpivo.insurancechallenge.core.exception.ResourceNotFoundException;
import com.freemanpivo.insurancechallenge.database.entity.ProductEntity;
import com.freemanpivo.insurancechallenge.database.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
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
    private final UUID idEntityC = UUID.randomUUID();
    private final ProductEntity entityC = new ProductEntity(idEntityC, "Produto C", "VIDA", BigDecimal.ONE, BigDecimal.TEN);
    private final UUID idEntityD = UUID.randomUUID();
    private final ProductEntity entityD = new ProductEntity(idEntityD, "Produto D", "VIDA", BigDecimal.ONE, BigDecimal.TEN);

    @BeforeAll
    void setup() {
        repository.save(entityA);
    }

    @AfterAll
    void tearDown() {
        repository.deleteById(idEntityA);
        repository.deleteById(idEntityB);
        repository.deleteById(idEntityC);
    }

    @Test
    void findByName() {
        final var isFound = database.findByName("produto a");

        Assertions.assertTrue(isFound.isPresent());
    }

    @Test
    void findById() {
        final var isFound = database.findById(idEntityA.toString());

        Assertions.assertTrue(isFound.isPresent());
    }

    @Test
    void saveNonExisting() {
        Assertions.assertDoesNotThrow(() -> database.save(ProductEntity.to(entityB)));
    }

    @Test
    void saveExisting() {
        Assertions.assertThrows(ResourceAlreadyCreatedException.class, () -> database.save(ProductEntity.to(entityB)));
    }

    @Test
    void updateExisting() {
        repository.save(entityC);
        Assertions.assertDoesNotThrow(() -> database.update(ProductEntity.to(entityC)));
    }

    @Test
    void updateNonExisting() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> database.update(ProductEntity.to(entityD)));
    }
}