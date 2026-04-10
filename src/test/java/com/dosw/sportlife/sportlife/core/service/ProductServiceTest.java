package com.dosw.sportlife.sportlife.core.service;

import com.dosw.sportlife.sportlife.core.exception.ConflictException;
import com.dosw.sportlife.sportlife.core.exception.ResourceNotFoundException;
import com.dosw.sportlife.sportlife.core.model.Product;
import com.dosw.sportlife.sportlife.core.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private UUID productId;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        product = Product.builder()
                .id(productId)
                .name("Running Shoes Pro")
                .description("High performance running shoes")
                .category("running")
                .price(250000.00)
                .stock(15)
                .status("active")
                .build();
    }


    @Test
    void findAll_ShouldReturnAllActiveProducts_WhenNoFilters() {
        when(productRepository.findAllActive()).thenReturn(List.of(product));

        List<Product> result = productService.findAll(null, null);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository, times(1)).findAllActive();
    }

    @Test
    void findAll_ShouldReturnProductsByCategory_WhenCategoryProvided() {
        when(productRepository.findActiveByCategory("running")).thenReturn(List.of(product));

        List<Product> result = productService.findAll("running", null);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository, times(1)).findActiveByCategory("running");
    }

    @Test
    void findAll_ShouldReturnProductsByName_WhenNameProvided() {
        when(productRepository.findActiveByNameContaining("Running")).thenReturn(List.of(product));

        List<Product> result = productService.findAll(null, "Running");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository, times(1)).findActiveByNameContaining("Running");
    }


    @Test
    void findById_ShouldReturnProduct_WhenProductExists() {
        when(productRepository.findActiveById(productId)).thenReturn(Optional.of(product));

        Product result = productService.findById(productId);

        assertNotNull(result);
        assertEquals(productId, result.getId());
        verify(productRepository, times(1)).findActiveById(productId);
    }

    @Test
    void findById_ShouldThrowResourceNotFoundException_WhenProductNotFound() {
        when(productRepository.findActiveById(productId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> productService.findById(productId));

        assertEquals("Product not found", exception.getMessage());
    }


    @Test
    void create_ShouldReturnSavedProduct_WhenNameIsNotDuplicated() {
        when(productRepository.existsByName(anyString())).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.create(product);

        assertNotNull(result);
        assertEquals("active", result.getStatus());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void create_ShouldThrowConflictException_WhenNameAlreadyExists() {
        when(productRepository.existsByName(anyString())).thenReturn(true);

        ConflictException exception = assertThrows(ConflictException.class,
                () -> productService.create(product));

        assertEquals("A product with that name already exists", exception.getMessage());
        verify(productRepository, never()).save(any(Product.class));
    }


    @Test
    void update_ShouldReturnUpdatedProduct_WhenProductExists() {
        when(productRepository.findActiveById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.update(productId, product);

        assertNotNull(result);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void update_ShouldThrowResourceNotFoundException_WhenProductNotFound() {
        when(productRepository.findActiveById(productId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> productService.update(productId, product));

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, never()).save(any(Product.class));
    }


    @Test
    void delete_ShouldDeactivateProduct_WhenProductExists() {
        when(productRepository.findActiveById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product p = invocation.getArgument(0);
            return p;
        });

        Product result = productService.delete(productId);

        assertEquals("inactive", result.getStatus());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void delete_ShouldThrowResourceNotFoundException_WhenProductNotFound() {
        when(productRepository.findActiveById(productId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> productService.delete(productId));

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, never()).save(any(Product.class));
    }
}