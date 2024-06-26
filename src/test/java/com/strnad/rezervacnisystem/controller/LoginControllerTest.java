package com.strnad.rezervacnisystem.controller;

import com.strnad.rezervacnisystem.security.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loginUser_Success() {
        // Arrange
        LoginController.LoginRequest loginRequest = new LoginController.LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setHeslo("password");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        // Act
        ResponseEntity<?> response = loginController.loginUser(loginRequest);

        // Assert
        assertEquals(HttpServletResponse.SC_OK, response.getStatusCodeValue());
        LoginController.LoginResponse loginResponse = (LoginController.LoginResponse) response.getBody();
        assertEquals("Login successful", loginResponse.getMessage());
        assertEquals("/rezervacni-tabulka", loginResponse.getRedirectUrl());
    }

    @Test
    void loginUser_Failure() {
        // Arrange
        LoginController.LoginRequest loginRequest = new LoginController.LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setHeslo("wrongpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Authentication failed"));

        // Act
        ResponseEntity<?> response = loginController.loginUser(loginRequest);

        // Assert
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatusCodeValue());
        assertEquals("Login failed. Please check your credentials.", response.getBody());
    }
}
