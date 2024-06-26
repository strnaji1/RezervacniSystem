package com.strnad.rezervacnisystem.repository;

import com.strnad.rezervacnisystem.model.Kurt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class KurtRepositoryTest {

    @Autowired
    private KurtRepository kurtRepository;

    @BeforeEach
    public void setup() {
        kurtRepository.deleteAll();
    }

    @Test
    public void testSaveAndGetKurt() {
        // Vytvoření instance Kurt
        Kurt kurt = new Kurt();
        kurt.setNazev("Tenisový kurt");

        // Uložení Kurta do databáze
        Kurt savedKurt = kurtRepository.save(kurt);
        Assertions.assertNotNull(savedKurt.getId(), "ID should not be null after saving");

        // Získání Kurta podle ID
        Optional<Kurt> fetchedKurt = kurtRepository.findById(savedKurt.getId());
        Assertions.assertTrue(fetchedKurt.isPresent(), "Kurt with ID " + savedKurt.getId() + " should be present");
        Assertions.assertEquals("Tenisový kurt", fetchedKurt.get().getNazev(), "Nazev should match");
    }

    @Test
    public void testFindByNazev() {
        // Vytvoření instance Kurt
        Kurt kurt = new Kurt();
        kurt.setNazev("Fotbalové hřiště");

        // Uložení Kurta do databáze
        kurtRepository.save(kurt);

        // Vyhledání Kurta podle názvu
        List<Kurt> foundKurty = kurtRepository.findByNazev("Fotbalové hřiště");
        Assertions.assertEquals(1, foundKurty.size(), "There should be one Kurt found");
        Assertions.assertEquals("Fotbalové hřiště", foundKurty.get(0).getNazev(), "Nazev should match");
    }

    @Test
    public void testDeleteKurt() {
        // Vytvoření instance Kurt
        Kurt kurt = new Kurt();
        kurt.setNazev("Basketbalový kurt");

        // Uložení Kurta do databáze
        Kurt savedKurt = kurtRepository.save(kurt);
        Assertions.assertNotNull(savedKurt.getId(), "ID should not be null after saving");

        // Smazání Kurta
        kurtRepository.deleteById(savedKurt.getId());

        // Ověření, že Kurt již není v databázi
        Optional<Kurt> deletedKurt = kurtRepository.findById(savedKurt.getId());
        Assertions.assertFalse(deletedKurt.isPresent(), "Kurt should be deleted");
    }
}
