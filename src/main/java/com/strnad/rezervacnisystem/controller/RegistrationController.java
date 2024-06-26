package com.strnad.rezervacnisystem.controller;

import com.strnad.rezervacnisystem.model.Uzivatel;
import com.strnad.rezervacnisystem.repository.UzivatelRepository;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth")
public class RegistrationController {

    @Autowired
    private UzivatelRepository uzivatelRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String registerUser(@RequestBody RegistrationRequest registrationRequest) {
        System.out.println("Received registration request");
        System.out.println("Jmeno: " + registrationRequest.getJmeno());
        System.out.println("Email: " + registrationRequest.getEmail());

        if (uzivatelRepository.findByEmail(registrationRequest.getEmail()) != null) {
            System.out.println("Email already in use: " + registrationRequest.getEmail());
            return "Email already in use";
        }

        if (!registrationRequest.getHeslo().equals(registrationRequest.getConfirmHeslo())) {
            System.out.println("Passwords do not match");
            return "Passwords do not match";
        }

        Uzivatel uzivatel = new Uzivatel();
        uzivatel.setJmeno(registrationRequest.getJmeno());
        uzivatel.setEmail(registrationRequest.getEmail());
        uzivatel.setHeslo(passwordEncoder.encode(registrationRequest.getHeslo()));
        uzivatel.setRole("ZAKAZNIK"); // nebo "ADMIN" podle potřeby
        uzivatelRepository.save(uzivatel);

        System.out.println("Registration successful for email: " + registrationRequest.getEmail());
        return "Registration successful";
    }
}

class RegistrationRequest {
    private String jmeno;
    private String email;
    private String heslo;
    private String confirmHeslo;

    // Gettery a settery

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

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

    public String getConfirmHeslo() {
        return confirmHeslo;
    }

    public void setConfirmHeslo(String confirmHeslo) {
        this.confirmHeslo = confirmHeslo;
    }
}
