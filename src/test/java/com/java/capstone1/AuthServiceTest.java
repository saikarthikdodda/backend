package com.java.capstone1;

import com.java.capstone1.config.JwtService;
import com.java.capstone1.dto.LoginRequest;
import com.java.capstone1.dto.LoginResponse;
import com.java.capstone1.model.User;
import com.java.capstone1.repository.UserRepository;
import com.java.capstone1.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    // ---------- SUCCESS CASE ----------
    @Test
    void login_shouldReturnToken_whenCredentialsAreValid() {

        LoginRequest request = new LoginRequest("hello", "hello@123");

        User user = new User();
        user.setUsername("hello");
        user.setPassword("encodedPassword");
        user.setRole("USER");
        user.setActive(true);

        when(userRepository.findByUsername("hello"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("hello@123", "encodedPassword"))
                .thenReturn(true);
        when(jwtService.generateToken("hello", "USER"))
                .thenReturn("jwt-token");

        LoginResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("USER", response.getRole());

        verify(userRepository).findByUsername("hello");
        verify(jwtService).generateToken("hello", "USER");
    }

    // ---------- USER NOT FOUND ----------
    @Test
    void login_shouldThrowException_whenUserNotFound() {

        LoginRequest request = new LoginRequest("hello", "hello@123");

        when(userRepository.findByUsername("hello"))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authService.login(request)
        );

        assertEquals("User not found", exception.getMessage());
    }

    // ---------- USER INACTIVE ----------
    @Test
    void login_shouldThrowException_whenUserIsInactive() {

        LoginRequest request = new LoginRequest("hello", "hello@123");

        User user = new User();
        user.setUsername("hello");
        user.setPassword("encodedPassword");
        user.setRole("USER");
        user.setActive(false);

        when(userRepository.findByUsername("hello"))
                .thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authService.login(request)
        );

        assertEquals("Invalid credentials", exception.getMessage());
    }

    // ---------- WRONG PASSWORD ----------
    @Test
    void login_shouldThrowException_whenPasswordIsWrong() {

        LoginRequest request = new LoginRequest("hello", "hello@123");

        User user = new User();
        user.setUsername("hello");
        user.setPassword("encodedPassword");
        user.setRole("USER");
        user.setActive(true);

        when(userRepository.findByUsername("hello"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("hello@123", "encodedPassword"))
                .thenReturn(false);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authService.login(request)
        );

        assertEquals("Invalid credentials", exception.getMessage());
    }
}
