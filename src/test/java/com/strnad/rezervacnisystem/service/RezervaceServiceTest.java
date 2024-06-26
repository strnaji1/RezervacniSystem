package com.strnad.rezervacnisystem.service;

import com.strnad.rezervacnisystem.model.Rezervace;
import com.strnad.rezervacnisystem.repository.RezervaceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class RezervaceServiceTest {

    @Mock
    private RezervaceRepository rezervaceRepository;

    @InjectMocks
    private RezervaceService rezervaceService;

    public RezervaceServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRezervaceById() {
        Rezervace rezervace = new Rezervace();
        rezervace.setId("1");
        rezervace.setUzivatelId("user1");
        rezervace.setKurtId("court1");
        rezervace.setCas(LocalDateTime.now());

        when(rezervaceRepository.findById(anyString())).thenReturn(Optional.of(rezervace));

        Optional<Rezervace> found = rezervaceService.getRezervaceById("1");

        assertNotNull(found);
        assertEquals("1", found.get().getId());
        assertEquals("user1", found.get().getUzivatelId());
        assertEquals("court1", found.get().getKurtId());
    }
}
