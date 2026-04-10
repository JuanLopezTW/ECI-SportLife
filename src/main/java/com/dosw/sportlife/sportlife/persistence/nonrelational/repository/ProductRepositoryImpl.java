package com.dosw.sportlife.sportlife.persistence.nonrelational.repository;

import com.dosw.sportlife.sportlife.core.model.Product;
import com.dosw.sportlife.sportlife.core.repository.ProductRepository;
import com.dosw.sportlife.sportlife.persistence.nonrelational.mapper.ProductNonRelationalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductMongoRepository productMongoRepository;
    private final ProductNonRelationalMapper productNonRelationalMapper;

    @Override
    public Product save(Product product) {
        return productNonRelationalMapper.toModel(
                productMongoRepository.save(productNonRelationalMapper.toDocument(product)));
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return productMongoRepository.findById(id.toString())
                .map(productNonRelationalMapper::toModel);
    }

    @Override
    public Optional<Product> findActiveById(UUID id) {
        return productMongoRepository.findByIdAndStatus(id.toString(), "active")
                .map(productNonRelationalMapper::toModel);
    }

    @Override
    public List<Product> findAllActive() {
        return productMongoRepository.findByStatus("active")
                .stream().map(productNonRelationalMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findActiveByCategory(String category) {
        return productMongoRepository.findByStatusAndCategoryIgnoreCase("active", category)
                .stream().map(productNonRelationalMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findActiveByNameContaining(String name) {
        return productMongoRepository.findByStatusAndNameContainingIgnoreCase("active", name)
                .stream().map(productNonRelationalMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByName(String name) {
        return productMongoRepository.existsByName(name);
    }

    @Override
    public void deleteById(UUID id) {
        productMongoRepository.deleteById(id.toString());
    }
}
