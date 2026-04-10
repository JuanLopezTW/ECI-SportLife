package com.dosw.sportlife.sportlife.controller.mapper;

import com.dosw.sportlife.sportlife.controller.dto.request.RegisterRequest;
import com.dosw.sportlife.sportlife.controller.dto.response.RegisterResponse;
import com.dosw.sportlife.sportlife.core.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserControllerMapper {

    public User toModel(RegisterRequest request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }

    public RegisterResponse toResponse(User user) {
        return RegisterResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
