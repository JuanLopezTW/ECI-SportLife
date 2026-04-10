package com.dosw.sportlife.sportlife.persistence.relational.mapper;

import com.dosw.sportlife.sportlife.core.model.User;
import com.dosw.sportlife.sportlife.persistence.relational.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserRelationalMapper {

    public UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public User toModel(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .role(entity.getRole())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
