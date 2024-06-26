package com.strnad.rezervacnisystem.repository;

import com.strnad.rezervacnisystem.model.Uzivatel;
import com.strnad.rezervacnisystem.repository.UzivatelRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

@SpringJUnitConfig
@DataMongoTest
public class UzivatelRepositoryTest {

    @Autowired
    private UzivatelRepository uzivatelRepository;

    @Test
    public void testSaveUzivatel() {
        // Given
        Uzivatel uzivatel = new Uzivatel();
        uzivatel.setJmeno("Jan Novak");
        uzivatel.setHeslo("heslo123");
        uzivatel.setRole("ZAKAZNIK");

        // When
        Uzivatel savedUzivatel = uzivatelRepository.save(uzivatel);

        // Then
        Assertions.assertNotNull(savedUzivatel.getId());
        Assertions.assertEquals(uzivatel.getJmeno(), savedUzivatel.getJmeno());
        Assertions.assertEquals(uzivatel.getHeslo(), savedUzivatel.getHeslo());
        Assertions.assertEquals(uzivatel.getRole(), savedUzivatel.getRole());
    }

    @Test
    public void testFindUzivatelById() {
        // Given
        Uzivatel uzivatel = new Uzivatel();
        uzivatel.setJmeno("Petr Novotny");
        uzivatel.setHeslo("heslo456");
        uzivatel.setRole("ADMIN");
        Uzivatel savedUzivatel = uzivatelRepository.save(uzivatel);

        // When
        Optional<Uzivatel> foundUzivatelOptional = uzivatelRepository.findById(savedUzivatel.getId());

        // Then
        Assertions.assertTrue(foundUzivatelOptional.isPresent());
        Uzivatel foundUzivatel = foundUzivatelOptional.get();
        Assertions.assertEquals(savedUzivatel.getId(), foundUzivatel.getId());
        Assertions.assertEquals(savedUzivatel.getJmeno(), foundUzivatel.getJmeno());
        Assertions.assertEquals(savedUzivatel.getHeslo(), foundUzivatel.getHeslo());
        Assertions.assertEquals(savedUzivatel.getRole(), foundUzivatel.getRole());
    }

    @Test
    public void testDeleteUzivatel() {
        // Given
        Uzivatel uzivatel = new Uzivatel();
        uzivatel.setJmeno("Eva Vlckova");
        uzivatel.setHeslo("heslo789");
        uzivatel.setRole("ZAKAZNIK");
        Uzivatel savedUzivatel = uzivatelRepository.save(uzivatel);

        // When
        uzivatelRepository.deleteById(savedUzivatel.getId());

        // Then
        Optional<Uzivatel> deletedUzivatelOptional = uzivatelRepository.findById(savedUzivatel.getId());
        Assertions.assertFalse(deletedUzivatelOptional.isPresent());
    }

    // Add more test methods as needed
}
