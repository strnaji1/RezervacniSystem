package com.strnad.rezervacnisystem.service;

import com.strnad.rezervacnisystem.model.Rezervace;
import com.strnad.rezervacnisystem.repository.RezervaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RezervaceService {

    @Autowired
    private RezervaceRepository rezervaceRepository;

    public List<Rezervace> getAllRezervace() {
        return rezervaceRepository.findAll();
    }

    public Rezervace createRezervace(Rezervace rezervace) {
        return rezervaceRepository.save(rezervace);
    }

    public Optional<Rezervace> getRezervaceById(String id) {
        return rezervaceRepository.findById(id);
    }

    public Optional<Rezervace> updateRezervace(String id, Rezervace updatedRezervace) {
        return rezervaceRepository.findById(id)
                .map(rezervace -> {
                    rezervace.setUzivatelId(updatedRezervace.getUzivatelId());
                    rezervace.setKurtId(updatedRezervace.getKurtId());
                    rezervace.setCas(updatedRezervace.getCas());
                    return rezervaceRepository.save(rezervace);
                });
    }

    public void deleteRezervace(String id) {
        rezervaceRepository.deleteById(id);
    }
}
