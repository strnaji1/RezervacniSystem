package com.strnad.rezervacnisystem.repository;


import com.strnad.rezervacnisystem.model.Uzivatel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
public interface UzivatelRepository extends MongoRepository<Uzivatel, String> {
    Uzivatel findByEmail(String email);
}