package com.dosw.sportlife.sportlife.core.repository;

import com.dosw.sportlife.sportlife.core.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(UUID id);
    Optional<Product> findActiveById(UUID id);
    List<Product> findAllActive();
    List<Product> findActiveByCategory(String category);
    List<Product> findActiveByNameContaining(String name);
    boolean existsByName(String name);
    void deleteById(UUID id);
}
