package com.dosw.sportlife.sportlife.core.repository;

import com.dosw.sportlife.sportlife.core.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findById(UUID id);
}
