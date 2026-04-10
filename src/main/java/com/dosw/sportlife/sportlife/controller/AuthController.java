package com.dosw.sportlife.sportlife.controller;

import com.dosw.sportlife.sportlife.controller.dto.request.RegisterRequest;
import com.dosw.sportlife.sportlife.controller.dto.response.RegisterResponse;
import com.dosw.sportlife.sportlife.controller.mapper.UserControllerMapper;
import com.dosw.sportlife.sportlife.core.model.User;
import com.dosw.sportlife.sportlife.core.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Endpoints for user authentication and registration")
public class AuthController {

    private final UserService userService;
    private final UserControllerMapper userControllerMapper;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        User user = userControllerMapper.toModel(request);
        User savedUser = userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userControllerMapper.toResponse(savedUser));
    }
}
