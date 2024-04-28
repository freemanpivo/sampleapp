package com.freemanpivo.insurancechallenge.database.repository;

import com.freemanpivo.insurancechallenge.database.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends CrudRepository<ProductEntity, UUID> {
    Optional<ProductEntity> findByNameIgnoreCase(String name);

}
