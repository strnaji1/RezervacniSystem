package com.strnad.rezervacnisystem.repository;

import com.strnad.rezervacnisystem.model.Rezervace;
import com.strnad.rezervacnisystem.repository.RezervaceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;

@SpringJUnitConfig
@DataMongoTest
public class RezervaceRepositoryTest {

    @Autowired
    private RezervaceRepository rezervaceRepository;

    @Test
    public void testSaveRezervace() {
        // Given
        Rezervace rezervace = new Rezervace();
        rezervace.setUzivatelId("1");
        rezervace.setKurtId("2");
        rezervace.setCas(LocalDateTime.now());

        // When
        Rezervace savedRezervace = rezervaceRepository.save(rezervace);

        // Then
        Assertions.assertNotNull(savedRezervace.getId());
        Assertions.assertEquals(rezervace.getUzivatelId(), savedRezervace.getUzivatelId());
        Assertions.assertEquals(rezervace.getKurtId(), savedRezervace.getKurtId());
        Assertions.assertEquals(rezervace.getCas(), savedRezervace.getCas());
    }

    // Add more test methods as needed
}
