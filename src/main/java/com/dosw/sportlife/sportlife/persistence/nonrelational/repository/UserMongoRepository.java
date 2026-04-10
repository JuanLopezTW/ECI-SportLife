package com.dosw.sportlife.sportlife.persistence.nonrelational.repository;

import com.dosw.sportlife.sportlife.persistence.nonrelational.document.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMongoRepository extends MongoRepository<UserDocument, String> {
    Optional<UserDocument> findByEmail(String email);
    boolean existsByEmail(String email);
}