package com.dosw.sportlife.sportlife.core.service;

import com.dosw.sportlife.sportlife.core.exception.ConflictException;
import com.dosw.sportlife.sportlife.core.exception.UnauthorizedException;
import com.dosw.sportlife.sportlife.core.model.User;
import com.dosw.sportlife.sportlife.core.repository.UserRepository;
import com.dosw.sportlife.sportlife.core.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(UUID.randomUUID())
                .name("Juan Perez")
                .email("juan@email.com")
                .password("encodedPassword")
                .role("USER")
                .build();
    }

    // RF-01 Register tests
    @Test
    void register_ShouldReturnSavedUser_WhenEmailIsNotRegistered() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(UUID.randomUUID());
            return savedUser;
        });

        User result = userService.register(User.builder()
                .name("Juan Perez")
                .email("juan@email.com")
                .password("Pass123!")
                .build());

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("juan@email.com", result.getEmail());
        assertEquals("USER", result.getRole());
        assertEquals("encodedPassword", result.getPassword());
        assertNotNull(result.getCreatedAt());

        verify(userRepository, times(1)).existsByEmail("juan@email.com");
        verify(passwordEncoder, times(1)).encode("Pass123!");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void register_ShouldThrowConflictException_WhenEmailIsAlreadyRegistered() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        ConflictException exception = assertThrows(ConflictException.class,
                () -> userService.register(User.builder()
                        .name("Juan Perez")
                        .email("juan@email.com")
                        .password("Pass123!")
                        .build()));

        assertEquals("The email is already registered", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    // RF-02 Login tests
    @Test
    void login_ShouldReturnToken_WhenCredentialsAreValid() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtUtil.generateToken(anyString(), anyString())).thenReturn("jwt-token");

        String token = userService.login("juan@email.com", "Pass123!");

        assertNotNull(token);
        assertEquals("jwt-token", token);
        verify(userRepository, times(1)).findByEmail("juan@email.com");
        verify(passwordEncoder, times(1)).matches("Pass123!", "encodedPassword");
        verify(jwtUtil, times(1)).generateToken("juan@email.com", "USER");
    }

    @Test
    void login_ShouldThrowUnauthorizedException_WhenEmailNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        UnauthorizedException exception = assertThrows(UnauthorizedException.class,
                () -> userService.login("notfound@email.com", "Pass123!"));

        assertEquals("Invalid credentials", exception.getMessage());
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void login_ShouldThrowUnauthorizedException_WhenPasswordIsWrong() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        UnauthorizedException exception = assertThrows(UnauthorizedException.class,
                () -> userService.login("juan@email.com", "wrongPassword"));

        assertEquals("Invalid credentials", exception.getMessage());
        verify(jwtUtil, never()).generateToken(anyString(), anyString());
    }
}