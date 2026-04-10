package com.dosw.sportlife.sportlife.persistence.relational.repository;

import com.dosw.sportlife.sportlife.core.model.User;
import com.dosw.sportlife.sportlife.core.repository.UserRepository;
import com.dosw.sportlife.sportlife.persistence.relational.mapper.UserRelationalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserRelationalMapper userRelationalMapper;

    @Override
    public User save(User user) {
        return userRelationalMapper.toModel(
                userJpaRepository.save(userRelationalMapper.toEntity(user)));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(userRelationalMapper::toModel);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userJpaRepository.findById(id)
                .map(userRelationalMapper::toModel);
    }
}
