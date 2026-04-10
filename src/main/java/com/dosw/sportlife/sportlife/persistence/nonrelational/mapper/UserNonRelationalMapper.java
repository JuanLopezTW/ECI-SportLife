package com.dosw.sportlife.sportlife.persistence.nonrelational.mapper;

import com.dosw.sportlife.sportlife.core.model.User;
import com.dosw.sportlife.sportlife.persistence.nonrelational.document.UserDocument;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserNonRelationalMapper {

    public UserDocument toDocument(User user) {
        return UserDocument.builder()
                .id(user.getId() != null ? user.getId().toString() : null)
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public User toModel(UserDocument document) {
        return User.builder()
                .id(document.getId() != null ? UUID.fromString(document.getId()) : null)
                .name(document.getName())
                .email(document.getEmail())
                .password(document.getPassword())
                .role(document.getRole())
                .createdAt(document.getCreatedAt())
                .build();
    }
}
