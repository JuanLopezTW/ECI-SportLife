package com.dosw.sportlife.sportlife.controller.mapper;

import com.dosw.sportlife.sportlife.controller.dto.request.ProductRequest;
import com.dosw.sportlife.sportlife.controller.dto.response.ProductResponse;
import com.dosw.sportlife.sportlife.core.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductControllerMapper {

    public Product toModel(ProductRequest request) {
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .price(request.getPrice())
                .stock(request.getStock())
                .images(request.getImages())
                .status(request.getStatus())
                .build();
    }

    public ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .stock(product.getStock())
                .images(product.getImages())
                .status(product.getStatus())
                .build();
    }
}