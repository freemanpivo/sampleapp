package com.freemanpivo.insurancechallenge.database.repository;

import com.freemanpivo.insurancechallenge.database.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, UUID> {
    Optional<ProductEntity> findByNameIgnoreCaseAndCategory(String name, String category);

}
