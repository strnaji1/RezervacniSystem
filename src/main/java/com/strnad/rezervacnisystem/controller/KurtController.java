package com.strnad.rezervacnisystem.controller;

import com.strnad.rezervacnisystem.model.Kurt;
import com.strnad.rezervacnisystem.service.KurtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/kurty")
public class KurtController {

    @Autowired
    private KurtService kurtService;

    @GetMapping
    public ResponseEntity<List<Kurt>> getAllKurty() {
        List<Kurt> kurty = kurtService.getAllKurty();
        return ResponseEntity.ok(kurty);
    }

    @PostMapping
    public ResponseEntity<Kurt> createKurt(@RequestBody Kurt kurt) {
        Kurt createdKurt = kurtService.createKurt(kurt);
        return ResponseEntity.ok(createdKurt);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kurt> getKurtById(@PathVariable String id) {
        Optional<Kurt> kurt = kurtService.getKurtById(id);
        if (kurt.isPresent()) {
            return ResponseEntity.ok(kurt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kurt> updateKurt(@PathVariable String id, @RequestBody Kurt updatedKurt) {
        Optional<Kurt> kurt = kurtService.updateKurt(id, updatedKurt);
        if (kurt.isPresent()) {
            return ResponseEntity.ok(kurt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKurt(@PathVariable String id) {
        kurtService.deleteKurt(id);
        return ResponseEntity.noContent().build();
    }
}
