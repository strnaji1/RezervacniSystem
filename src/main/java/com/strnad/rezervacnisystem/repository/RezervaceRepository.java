package com.strnad.rezervacnisystem.repository;

import com.strnad.rezervacnisystem.model.Rezervace;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RezervaceRepository extends MongoRepository<Rezervace, String> {
    List<Rezervace> findByKurtId(String kurtId);
    List<Rezervace> findByUzivatelId(String uzivatelId);
}