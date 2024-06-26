package com.strnad.rezervacnisystem.service;

import com.strnad.rezervacnisystem.model.Uzivatel;
import com.strnad.rezervacnisystem.repository.UzivatelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UzivatelService {

    private final UzivatelRepository uzivatelRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UzivatelService.class);

    @Autowired
    public UzivatelService(UzivatelRepository uzivatelRepository, PasswordEncoder passwordEncoder) {
        this.uzivatelRepository = uzivatelRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerUser(Uzivatel uzivatel) {
        logger.info("Registering user with email: {}", uzivatel.getEmail());
        if (uzivatelRepository.findByEmail(uzivatel.getEmail()) != null) {
            logger.warn("Email already in use: {}", uzivatel.getEmail());
            return "Email already in use";
        }
        uzivatel.setHeslo(passwordEncoder.encode(uzivatel.getHeslo()));
        uzivatel.setRole("ZAKAZNIK"); // nebo "ADMIN" podle potřeby
        uzivatelRepository.save(uzivatel);
        logger.info("Registration successful for email: {}", uzivatel.getEmail());
        return "Registration successful";
    }

    public String loginUser(String email, String heslo) {
        logger.info("Logging in user with email: {}", email);
        Uzivatel uzivatel = uzivatelRepository.findByEmail(email);
        if (uzivatel == null || !passwordEncoder.matches(heslo, uzivatel.getHeslo())) {
            logger.warn("Invalid email or password for email: {}", email);
            return "Invalid email or password";
        }
        logger.info("Login successful for email: {}", email);
        return "Login successful";
    }

    public Uzivatel createUzivatel(Uzivatel uzivatel) {
        logger.info("Creating user with email: {}", uzivatel.getEmail());
        uzivatel.setHeslo(passwordEncoder.encode(uzivatel.getHeslo()));
        Uzivatel createdUzivatel = uzivatelRepository.save(uzivatel);
        logger.info("User created with email: {}", createdUzivatel.getEmail());
        return createdUzivatel;
    }

    public Uzivatel updateUzivatel(Uzivatel uzivatel) {
        logger.info("Updating user with id: {}", uzivatel.getId());
        Uzivatel existingUzivatel = uzivatelRepository.findById(uzivatel.getId()).orElse(null);
        if (existingUzivatel != null) {
            existingUzivatel.setJmeno(uzivatel.getJmeno());
            existingUzivatel.setRole(uzivatel.getRole());
            if (!uzivatel.getHeslo().equals(existingUzivatel.getHeslo())) {
                existingUzivatel.setHeslo(passwordEncoder.encode(uzivatel.getHeslo()));
            }
            Uzivatel updatedUzivatel = uzivatelRepository.save(existingUzivatel);
            logger.info("User updated with id: {}", updatedUzivatel.getId());
            return updatedUzivatel;
        } else {
            logger.warn("User not found with id: {}", uzivatel.getId());
            return null;
        }
    }

    public Uzivatel getUzivatelById(String id) {
        logger.info("Fetching user with id: {}", id);
        return uzivatelRepository.findById(id).orElse(null);
    }

    public Uzivatel getUzivatelByEmail(String email) {
        logger.info("Fetching user with email: {}", email);
        return uzivatelRepository.findByEmail(email);
    }

    public List<Uzivatel> getAllUzivatele() {
        logger.info("Fetching all users");
        return uzivatelRepository.findAll();
    }

    public void deleteUzivatel(String id) {
        logger.info("Deleting user with id: {}", id);
        uzivatelRepository.deleteById(id);
    }
}
