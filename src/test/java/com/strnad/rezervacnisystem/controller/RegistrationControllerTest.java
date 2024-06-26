package com.strnad.rezervacnisystem.controller;

import com.strnad.rezervacnisystem.model.Uzivatel;
import com.strnad.rezervacnisystem.repository.UzivatelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RegistrationControllerTest {

    @Mock
    private UzivatelRepository uzivatelRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationController registrationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_Success() {
        // Arrange
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setJmeno("Test User");
        registrationRequest.setEmail("test@example.com");
        registrationRequest.setHeslo("password");
        registrationRequest.setConfirmHeslo("password");

        when(uzivatelRepository.findByEmail(registrationRequest.getEmail())).thenReturn(null);
        when(passwordEncoder.encode(registrationRequest.getHeslo())).thenReturn("encodedPassword");

        // Act
        String response = registrationController.registerUser(registrationRequest);

        // Assert
        assertEquals("Registration successful", response);
    }

    @Test
    void registerUser_EmailInUse() {
        // Arrange
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setJmeno("Test User");
        registrationRequest.setEmail("test@example.com");
        registrationRequest.setHeslo("password");
        registrationRequest.setConfirmHeslo("password");

        Uzivatel existingUser = new Uzivatel();
        when(uzivatelRepository.findByEmail(registrationRequest.getEmail())).thenReturn(existingUser);

        // Act
        String response = registrationController.registerUser(registrationRequest);

        // Assert
        assertEquals("Email already in use", response);
    }

    @Test
    void registerUser_PasswordsDoNotMatch() {
        // Arrange
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setJmeno("Test User");
        registrationRequest.setEmail("test@example.com");
        registrationRequest.setHeslo("password");
        registrationRequest.setConfirmHeslo("differentPassword");

        // Act
        String response = registrationController.registerUser(registrationRequest);

        // Assert
        assertEquals("Passwords do not match", response);
    }
}
