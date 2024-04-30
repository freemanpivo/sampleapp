package com.freemanpivo.insurancechallenge.core.usecase;

import com.freemanpivo.insurancechallenge.UnitTest;
import com.freemanpivo.insurancechallenge.core.domain.Price;
import com.freemanpivo.insurancechallenge.core.domain.Product;
import com.freemanpivo.insurancechallenge.core.domain.ProductCandidate;
import com.freemanpivo.insurancechallenge.core.domain.ProductIdentifier;
import com.freemanpivo.insurancechallenge.core.exception.ResourceAlreadyCreatedException;
import com.freemanpivo.insurancechallenge.core.exception.ResourceNotFoundException;
import com.freemanpivo.insurancechallenge.core.port.out.ProductDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;

@UnitTest
class ProductServiceTest {
    private final ProductDatabase repository = Mockito.mock(ProductDatabase.class);
    private final ProductService service = new ProductService(repository);

    private final ProductCandidate sample = new ProductCandidate("Valid Name", "VIDA", BigDecimal.ONE);
    private final Product product = Product.create(sample, new Price(sample.basePrice(), sample.category()), new ProductIdentifier());

    @Test
    void testCreationCaseWithSuccess() {
        when(repository.findByNameAndCategory(sample.name(), sample.category())).thenReturn(Optional.empty());
        final var product = service.create(sample);

        Assertions.assertNotNull(product.id());
        Assertions.assertNotNull(product.chargedPrice());
    }

    @Test
    void testCreationCaseWithAlreadyRegisteredProduct() {
        when(repository.findByNameAndCategory(sample.name(), sample.category())).thenReturn(Optional.of(product));

        Assertions.assertThrows(ResourceAlreadyCreatedException.class, () -> service.create(sample));
    }

    @Test
    void testUpdateCaseWithSuccess() {
        when(repository.findByNameAndCategory(sample.name(), sample.category())).thenReturn(Optional.empty());
        when(repository.findById(product.id())).thenReturn(Optional.of(product));

        Assertions.assertEquals(product, service.update(product.id(), sample));
    }

    @Test
    void testUpdateCaseWithAlreadyRegisteredProductName() {
        when(repository.findByNameAndCategory(sample.name(), sample.category())).thenReturn(Optional.of(product));
        when(repository.findById(product.id())).thenReturn(Optional.of(product));

        Assertions.assertThrows(ResourceAlreadyCreatedException.class, () -> service.update(product.id(), sample));
    }

    @Test
    void testUpdateCaseWithAlreadyRegisteredProductId() {
        when(repository.findByNameAndCategory(sample.name(), sample.category())).thenReturn(Optional.of(product));
        when(repository.findById(product.id())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.update(product.id(), sample));
    }

}