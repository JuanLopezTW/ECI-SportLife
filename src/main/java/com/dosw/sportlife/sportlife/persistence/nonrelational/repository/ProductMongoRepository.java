package com.dosw.sportlife.sportlife.persistence.nonrelational.repository;

import com.dosw.sportlife.sportlife.persistence.nonrelational.document.ProductDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductMongoRepository extends MongoRepository<ProductDocument, String> {
    List<ProductDocument> findByStatusAndCategoryIgnoreCase(String status, String category);
    List<ProductDocument> findByStatusAndNameContainingIgnoreCase(String status, String name);
    List<ProductDocument> findByStatus(String status);
    boolean existsByName(String name);
    Optional<ProductDocument> findByIdAndStatus(String id, String status);
}
