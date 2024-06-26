package com.strnad.rezervacnisystem.controller;

import com.strnad.rezervacnisystem.model.Rezervace;
import com.strnad.rezervacnisystem.service.RezervaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservation")
public class RezervaceController {

    @Autowired
    private RezervaceService rezervaceService;

    @GetMapping
    public List<Rezervace> getAllRezervace() {
        return rezervaceService.getAllRezervace();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rezervace> getRezervaceById(@PathVariable String id) {
        Optional<Rezervace> rezervace = rezervaceService.getRezervaceById(id);
        if (rezervace.isPresent()) {
            return ResponseEntity.ok(rezervace.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Rezervace createRezervace(@RequestBody Rezervace rezervace) {
        return rezervaceService.createRezervace(rezervace);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rezervace> updateRezervace(@PathVariable String id, @RequestBody Rezervace updatedRezervace) {
        Optional<Rezervace> rezervace = rezervaceService.updateRezervace(id, updatedRezervace);
        if (rezervace.isPresent()) {
            return ResponseEntity.ok(rezervace.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRezervace(@PathVariable String id) {
        rezervaceService.deleteRezervace(id);
        return ResponseEntity.noContent().build();
    }
}
