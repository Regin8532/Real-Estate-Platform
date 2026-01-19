package com.example.realestate.service;

import com.example.realestate.entity.User;
import com.example.realestate.repository.UserRepository;
import com.example.realestate.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        User user = new User();
        user.setName("John");
        user.setEmail("john@example.com");
        user.setPassword("plain");

        when(passwordEncoder.encode("plain")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User saved = userService.register(user);

        assertEquals("encoded", saved.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetByEmailFound() {
        User user = new User();
        user.setEmail("john@example.com");
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        User found = userService.getByEmail("john@example.com");

        assertEquals("john@example.com", found.getEmail());
    }

    @Test
    void testGetByEmailNotFound() {
        when(userRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.getByEmail("missing@example.com"));
    }
}
