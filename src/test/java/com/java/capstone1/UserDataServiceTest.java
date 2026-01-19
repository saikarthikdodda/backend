package com.java.capstone1;


import com.java.capstone1.model.User;
import com.java.capstone1.repository.UserRepository;
import com.java.capstone1.service.UserDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDataServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDataService userDataService;

    // ---------- LOAD USER (SUCCESS) ----------
    @Test
    void loadUserByUsername_shouldReturnUserDetails_whenUserExistsAndActive() {

        User user = new User();
        user.setUsername("hello");
        user.setPassword("encodedPassword");
        user.setRole("USER");
        user.setActive(true);

        when(userRepository.findByUsername("hello"))
                .thenReturn(Optional.of(user));

        UserDetails userDetails = userDataService.loadUserByUsername("hello");

        assertNotNull(userDetails);
        assertEquals("hello", userDetails.getUsername());
        assertEquals("encodedPassword", userDetails.getPassword());
        assertTrue(
                userDetails.getAuthorities()
                        .stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_USER"))
        );
        assertTrue(userDetails.isEnabled());

        verify(userRepository).findByUsername("hello");
    }

    // ---------- LOAD USER (INACTIVE USER) ----------
    @Test
    void loadUserByUsername_shouldReturnDisabledUser_whenUserIsInactive() {

        User user = new User();
        user.setUsername("hello");
        user.setPassword("encodedPassword");
        user.setRole("ADMIN");
        user.setActive(false);

        when(userRepository.findByUsername("hello"))
                .thenReturn(Optional.of(user));

        UserDetails userDetails = userDataService.loadUserByUsername("hello");

        assertFalse(userDetails.isEnabled());
        assertTrue(
                userDetails.getAuthorities()
                        .stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
        );
    }

    // ---------- LOAD USER (NOT FOUND) ----------
    @Test
    void loadUserByUsername_shouldThrowException_whenUserNotFound() {

        when(userRepository.findByUsername("unknown"))
                .thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userDataService.loadUserByUsername("unknown")
        );

        assertEquals("User not found", exception.getMessage());
    }
}

