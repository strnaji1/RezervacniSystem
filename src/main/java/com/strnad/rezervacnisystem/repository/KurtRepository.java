package com.strnad.rezervacnisystem.repository;

import com.strnad.rezervacnisystem.model.Kurt;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface KurtRepository extends MongoRepository<Kurt, String> {
    List<Kurt> findByNazev(String nazev);
}
