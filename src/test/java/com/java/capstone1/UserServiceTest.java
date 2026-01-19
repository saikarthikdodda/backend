package com.java.capstone1;


import com.java.capstone1.dto.UserRequest;
import com.java.capstone1.model.User;
import com.java.capstone1.repository.UserRepository;
import com.java.capstone1.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserService userService;

    // ---------- CREATE USER ----------
    @Test
    void createUser_shouldEncodePasswordAndSaveUser() {

        UserRequest request = new UserRequest();
        request.setUsername("hello");
        request.setEmail("hello@gmail.com");
        request.setPassword("hello@123");
        request.setRole("USER");

        when(encoder.encode("hello@123")).thenReturn("encodedPassword");
        when(repository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = userService.createUser(request);

        assertNotNull(savedUser);
        assertEquals("hello", savedUser.getUsername());
        assertEquals("hello@gmail.com", savedUser.getEmail());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals("USER", savedUser.getRole());

        verify(encoder).encode("hello@123");
        verify(repository).save(any(User.class));
    }

    // ---------- SHOW ALL USERS ----------
    @Test
    void show_shouldReturnUserList() {

        when(repository.findAll())
                .thenReturn(List.of(new User(), new User()));

        List<User> users = userService.show();

        assertEquals(2, users.size());
        verify(repository).findAll();
    }

    // ---------- UPDATE STATUS (SUCCESS) ----------
    @Test
    void updateStatus_shouldUpdateUserActiveStatus() {

        User user = new User();
        user.setActive(false);

        when(repository.findById("1"))
                .thenReturn(Optional.of(user));
        when(repository.save(user))
                .thenReturn(user);

        User updated = userService.updateStatus("1", true);

        assertTrue(updated.isActive());
        verify(repository).save(user);
    }

    // ---------- UPDATE STATUS (USER NOT FOUND) ----------
    @Test
    void updateStatus_shouldThrowException_whenUserNotFound() {

        when(repository.findById("1"))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.updateStatus("1", true)
        );

        assertEquals("User not found", exception.getMessage());
    }
}

