package com.dosw.sportlife.sportlife.controller;

import com.dosw.sportlife.sportlife.controller.dto.request.ProductRequest;
import com.dosw.sportlife.sportlife.controller.dto.response.ProductResponse;
import com.dosw.sportlife.sportlife.controller.mapper.ProductControllerMapper;
import com.dosw.sportlife.sportlife.core.model.Product;
import com.dosw.sportlife.sportlife.core.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Endpoints for product management")
public class ProductController {

    private final ProductService productService;
    private final ProductControllerMapper productControllerMapper;

    @GetMapping
    @Operation(summary = "Get all active products with optional filters")
    public ResponseEntity<List<ProductResponse>> findAll(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String name) {
        List<ProductResponse> products = productService.findAll(category, name)
                .stream().map(productControllerMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by id")
    public ResponseEntity<ProductResponse> findById(@PathVariable UUID id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok(productControllerMapper.toResponse(product));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new product", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        Product product = productService.create(productControllerMapper.toModel(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(productControllerMapper.toResponse(product));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a product", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<ProductResponse> update(@PathVariable UUID id,
                                                  @Valid @RequestBody ProductRequest request) {
        Product product = productService.update(id, productControllerMapper.toModel(request));
        return ResponseEntity.ok(productControllerMapper.toResponse(product));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deactivate a product", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<ProductResponse> delete(@PathVariable UUID id) {
        Product product = productService.delete(id);
        return ResponseEntity.ok(productControllerMapper.toResponse(product));
    }
}