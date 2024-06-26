package com.strnad.rezervacnisystem.controller;

import com.strnad.rezervacnisystem.model.Uzivatel;
import com.strnad.rezervacnisystem.service.UzivatelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/uzivatele")
public class UzivatelController {

    @Autowired
    private UzivatelService uzivatelService;

    @GetMapping
    public List<Uzivatel> getAllUzivatele() {
        return uzivatelService.getAllUzivatele();
    }

    @GetMapping("/{id}")
    public Uzivatel getUzivatelById(@PathVariable String id) {
        return uzivatelService.getUzivatelById(id);
    }

    @PostMapping
    public Uzivatel createUzivatel(@RequestBody Uzivatel uzivatel) {
        return uzivatelService.createUzivatel(uzivatel);
    }

    @PutMapping("/{id}")
    public Uzivatel updateUzivatel(@PathVariable String id, @RequestBody Uzivatel uzivatel) {
        uzivatel.setId(id);
        return uzivatelService.updateUzivatel(uzivatel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUzivatel(@PathVariable String id) {
        uzivatelService.deleteUzivatel(id);
        return ResponseEntity.noContent().build();
    }
}
