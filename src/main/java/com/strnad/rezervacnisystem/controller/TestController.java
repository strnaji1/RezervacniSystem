package com.strnad.rezervacnisystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @PostMapping("/echo")
    public ResponseEntity<LoginController.LoginRequest> echo(@RequestBody LoginController.LoginRequest loginRequest) {
        logger.info("Received test request");
        logger.info("Email: {}", loginRequest.getEmail());
        logger.info("Password: {}", loginRequest.getHeslo());
        return ResponseEntity.ok(loginRequest);
    }
}
