package com.strnad.rezervacnisystem.service;

import com.strnad.rezervacnisystem.model.Kurt;
import com.strnad.rezervacnisystem.repository.KurtRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class KurtServiceTest {

    @Mock
    private KurtRepository kurtRepository;

    @InjectMocks
    private KurtService kurtService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllKurty() {
        Kurt kurt = new Kurt();
        kurt.setId("1");
        kurt.setNazev("Tenisový kurt");

        when(kurtRepository.findAll()).thenReturn(Collections.singletonList(kurt));

        assertEquals(1, kurtService.getAllKurty().size());
        assertEquals("Tenisový kurt", kurtService.getAllKurty().get(0).getNazev());

        verify(kurtRepository, times(2)).findAll(); // called twice due to assertions
    }

    @Test
    public void testCreateKurt() {
        Kurt kurt = new Kurt();
        kurt.setId("1");
        kurt.setNazev("Tenisový kurt");

        when(kurtRepository.save(any())).thenReturn(kurt);

        Kurt createdKurt = kurtService.createKurt(kurt);

        assertEquals("1", createdKurt.getId());
        assertEquals("Tenisový kurt", createdKurt.getNazev());

        verify(kurtRepository, times(1)).save(any());
    }

    @Test
    public void testGetKurtById() {
        Kurt kurt = new Kurt();
        kurt.setId("1");
        kurt.setNazev("Tenisový kurt");

        when(kurtRepository.findById("1")).thenReturn(Optional.of(kurt));

        Optional<Kurt> foundKurt = kurtService.getKurtById("1");

        assertEquals("Tenisový kurt", foundKurt.get().getNazev());

        verify(kurtRepository, times(1)).findById("1");
    }

    @Test
    public void testUpdateKurt() {
        Kurt existingKurt = new Kurt();
        existingKurt.setId("1");
        existingKurt.setNazev("Tenisový kurt");

        Kurt updatedKurt = new Kurt();
        updatedKurt.setId("1");
        updatedKurt.setNazev("Nový tenisový kurt");

        when(kurtRepository.findById("1")).thenReturn(Optional.of(existingKurt));
        when(kurtRepository.save(any())).thenReturn(updatedKurt);

        Optional<Kurt> result = kurtService.updateKurt("1", updatedKurt);

        assertEquals("Nový tenisový kurt", result.get().getNazev());

        verify(kurtRepository, times(1)).findById("1");
        verify(kurtRepository, times(1)).save(any());
    }

    @Test
    public void testDeleteKurt() {
        Kurt kurt = new Kurt();
        kurt.setId("1");
        kurt.setNazev("Tenisový kurt");

        when(kurtRepository.findById("1")).thenReturn(Optional.of(kurt));

        kurtService.deleteKurt("1");

        verify(kurtRepository, times(1)).deleteById("1");
    }
}
