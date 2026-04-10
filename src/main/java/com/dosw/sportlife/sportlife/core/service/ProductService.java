package com.dosw.sportlife.sportlife.core.service;

import com.dosw.sportlife.sportlife.core.exception.ConflictException;
import com.dosw.sportlife.sportlife.core.exception.ResourceNotFoundException;
import com.dosw.sportlife.sportlife.core.model.Product;
import com.dosw.sportlife.sportlife.core.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll(String category, String name) {
        if (category != null && !category.isBlank()) {
            return productRepository.findActiveByCategory(category);
        }
        if (name != null && !name.isBlank()) {
            return productRepository.findActiveByNameContaining(name);
        }
        return productRepository.findAllActive();
    }

    public Product findById(UUID id) {
        return productRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public Product create(Product product) {
        if (productRepository.existsByName(product.getName())) {
            throw new ConflictException("A product with that name already exists");
        }
        product.setStatus("active");
        return productRepository.save(product);
    }

    public Product update(UUID id, Product product) {
        productRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setId(id);
        product.setStatus("active");
        return productRepository.save(product);
    }

    public Product delete(UUID id) {
        Product product = productRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setStatus("inactive");
        return productRepository.save(product);
    }
}