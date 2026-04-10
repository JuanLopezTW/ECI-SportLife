package com.dosw.sportlife.sportlife.core.service;

import com.dosw.sportlife.sportlife.core.exception.ConflictException;
import com.dosw.sportlife.sportlife.core.model.User;
import com.dosw.sportlife.sportlife.core.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .name("Juan Perez")
                .email("juan@email.com")
                .password("Pass123!")
                .build();
    }

    @Test
    void register_ShouldReturnSavedUser_WhenEmailIsNotRegistered() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(UUID.randomUUID());
            return savedUser;
        });

        User result = userService.register(user);

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
                () -> userService.register(user));

        assertEquals("The email is already registered", exception.getMessage());

        verify(userRepository, times(1)).existsByEmail("juan@email.com");
        verify(userRepository, never()).save(any(User.class));
    }
}
