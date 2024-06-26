package com.strnad.rezervacnisystem.controller;

import com.strnad.rezervacnisystem.security.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        logger.info("Received login request: {}", loginRequest);

        if (loginRequest.getEmail() == null || loginRequest.getEmail().isEmpty()) {
            logger.error("Email is missing in the request");
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Email is required");
        }

        try {
            logger.info("Attempting to authenticate user: {}", loginRequest.getEmail());
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getHeslo());
            logger.info("Authentication token: {}", authenticationToken);

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("Authentication successful for user: {}", loginRequest.getEmail());
            return ResponseEntity.ok(new LoginResponse("Login successful"));
        } catch (Exception e) {
            logger.error("Authentication failed for user: {}", loginRequest.getEmail(), e);
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Špatné přihlašovací údaje");
        }
    }

    // Inner classes for request and response
    public static class LoginRequest {
        private String email;
        private String heslo;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getHeslo() {
            return heslo;
        }

        public void setHeslo(String heslo) {
            this.heslo = heslo;
        }
    }

    public static class LoginResponse {
        private String message;

        public LoginResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

