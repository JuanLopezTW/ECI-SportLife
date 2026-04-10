package com.dosw.sportlife.sportlife.persistence.nonrelational.mapper;

import com.dosw.sportlife.sportlife.core.model.Product;
import com.dosw.sportlife.sportlife.persistence.nonrelational.document.ProductDocument;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductNonRelationalMapper {

    public ProductDocument toDocument(Product product) {
        return ProductDocument.builder()
                .id(product.getId() != null ? product.getId().toString() : null)
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .stock(product.getStock())
                .images(product.getImages())
                .status(product.getStatus())
                .build();
    }

    public Product toModel(ProductDocument document) {
        return Product.builder()
                .id(document.getId() != null ? UUID.nameUUIDFromBytes(document.getId().getBytes()) : null)
                .name(document.getName())
                .description(document.getDescription())
                .category(document.getCategory())
                .price(document.getPrice())
                .stock(document.getStock())
                .images(document.getImages())
                .status(document.getStatus())
                .build();
    }
}
