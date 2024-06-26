package com.strnad.rezervacnisystem.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "uzivatele")
public class Uzivatel {
    @Id
    private String id;
    private String jmeno;
    private String email;
    private String heslo;
    private String role; // ADMIN nebo ZAKAZNIK

    // Bezparametrický konstruktor
    public Uzivatel() {
    }

    // Konstruktor s parametry
    public Uzivatel(String id, String jmeno, String email, String heslo, String role) {
        this.id = id;
        this.jmeno = jmeno;
        this.email = email;
        this.heslo = heslo;
        this.role = role;
    }

    // Gettery a settery
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
