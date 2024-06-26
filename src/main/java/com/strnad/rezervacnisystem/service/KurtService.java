package com.strnad.rezervacnisystem.service;

import com.strnad.rezervacnisystem.model.Kurt;
import com.strnad.rezervacnisystem.repository.KurtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KurtService {

    @Autowired
    private KurtRepository kurtRepository;

    public List<Kurt> getAllKurty() {
        return kurtRepository.findAll();
    }

    public Kurt createKurt(Kurt kurt) {
        return kurtRepository.save(kurt);
    }

    public Optional<Kurt> getKurtById(String id) {
        return kurtRepository.findById(id);
    }

    public Optional<Kurt> updateKurt(String id, Kurt updatedKurt) {
        return kurtRepository.findById(id)
                .map(kurt -> {
                    kurt.setNazev(updatedKurt.getNazev());
                    // Set other fields as needed
                    return kurtRepository.save(kurt);
                });
    }

    public void deleteKurt(String id) {
        kurtRepository.deleteById(id);
    }
}
