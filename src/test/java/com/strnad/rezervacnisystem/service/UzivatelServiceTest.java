package com.strnad.rezervacnisystem.service;


import com.strnad.rezervacnisystem.model.Uzivatel;
import com.strnad.rezervacnisystem.repository.UzivatelRepository;
import com.strnad.rezervacnisystem.service.UzivatelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;


public class UzivatelServiceTest {

    @Mock
    private UzivatelRepository uzivatelRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UzivatelService uzivatelService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUzivatel() {
        Uzivatel uzivatel = new Uzivatel();
        uzivatel.setId("1");
        uzivatel.setJmeno("Jan Novák");
        uzivatel.setEmail("jan.novak@example.com");
        uzivatel.setHeslo("heslo123");

        when(passwordEncoder.encode(any())).thenReturn("hashedPassword");
        when(uzivatelRepository.save(any())).thenReturn(uzivatel);

        Uzivatel createdUzivatel = uzivatelService.createUzivatel(uzivatel);

        assertEquals("1", createdUzivatel.getId());
        assertEquals("Jan Novák", createdUzivatel.getJmeno());
        assertEquals("jan.novak@example.com", createdUzivatel.getEmail());
        assertEquals("hashedPassword", createdUzivatel.getHeslo());

        verify(passwordEncoder, times(1)).encode("heslo123");
        verify(uzivatelRepository, times(1)).save(any());
    }

    @Test
    public void testUpdateUzivatel() {
        Uzivatel existingUzivatel = new Uzivatel();
        existingUzivatel.setId("1");
        existingUzivatel.setJmeno("Jan Novák");
        existingUzivatel.setEmail("jan.novak@example.com");
        existingUzivatel.setHeslo("hashedPassword");

        Uzivatel updatedUzivatel = new Uzivatel();
        updatedUzivatel.setId("1");
        updatedUzivatel.setJmeno("Nový Jméno");
        updatedUzivatel.setEmail("jan.novak@example.com");
        updatedUzivatel.setHeslo("novyHeslo456");

        when(uzivatelRepository.findById("1")).thenReturn(Optional.of(existingUzivatel));
        when(passwordEncoder.encode("novyHeslo456")).thenReturn("novyHashedPassword");
        when(uzivatelRepository.save(any())).thenReturn(updatedUzivatel);

        Uzivatel result = uzivatelService.updateUzivatel(updatedUzivatel);

        assertEquals("1", result.getId());
        assertEquals("Nový Jméno", result.getJmeno());
        assertEquals("jan.novak@example.com", result.getEmail());
        assertEquals("novyHeslo456", result.getHeslo());

        verify(uzivatelRepository, times(1)).findById("1");
        verify(uzivatelRepository, times(1)).save(any());
        verify(passwordEncoder, times(1)).encode("novyHeslo456");
    }

    @Test
    public void testGetUzivatelById() {
        Uzivatel uzivatel = new Uzivatel("1", "Jan Novák", "email@example.com", "password", "USER");

        // Mockujeme volání findById tak, aby vrátilo Optional obsahující uzivatele
        when(uzivatelRepository.findById("1")).thenReturn(Optional.of(uzivatel));

        // Volání service metody, která by měla vrátit nalezeného uzivatele
        Uzivatel foundUzivatel = uzivatelService.getUzivatelById("1");

        // Ověření, že výsledek je správný
        assertEquals("1", foundUzivatel.getId());
        assertEquals("Jan Novák", foundUzivatel.getJmeno());

        // Ověření, že metoda findById byla zavolána právě jednou
        verify(uzivatelRepository, times(1)).findById("1");
    }
    @Test
    public void testGetUzivatelByIdNotFound() {
        when(uzivatelRepository.findById("2")).thenReturn(Optional.empty());

        Uzivatel notFoundUzivatel = uzivatelService.getUzivatelById("2");

        assertNull(notFoundUzivatel);
        verify(uzivatelRepository, times(1)).findById("2");
    }


    @Test
    public void testGetUzivatelByEmail() {
        Uzivatel uzivatel = new Uzivatel();
        uzivatel.setId("1");
        uzivatel.setJmeno("Jan Novák");
        uzivatel.setEmail("jan.novak@example.com");

        when(uzivatelRepository.findByEmail("jan.novak@example.com")).thenReturn(uzivatel);

        Uzivatel foundUzivatel = uzivatelService.getUzivatelByEmail("jan.novak@example.com");

        assertEquals("1", foundUzivatel.getId());
        assertEquals("Jan Novák", foundUzivatel.getJmeno());
        assertEquals("jan.novak@example.com", foundUzivatel.getEmail());

        verify(uzivatelRepository, times(1)).findByEmail("jan.novak@example.com");
    }

    @Test
    public void testGetAllUzivatele() {
        Uzivatel uzivatel1 = new Uzivatel();
        uzivatel1.setId("1");
        uzivatel1.setJmeno("Jan Novák");

        Uzivatel uzivatel2 = new Uzivatel();
        uzivatel2.setId("2");
        uzivatel2.setJmeno("Petr Nový");

        when(uzivatelRepository.findAll()).thenReturn(Collections.singletonList(uzivatel1));

        assertEquals(1, uzivatelService.getAllUzivatele().size());
        assertEquals("Jan Novák", uzivatelService.getAllUzivatele().get(0).getJmeno());

        verify(uzivatelRepository, times(2)).findAll(); // called twice due to assertions
    }

    @Test
    public void testDeleteUzivatel() {
        Uzivatel uzivatel = new Uzivatel();
        uzivatel.setId("1");
        uzivatel.setJmeno("Jan Novák");

        when(uzivatelRepository.findById("1")).thenReturn(Optional.of(uzivatel));

        uzivatelService.deleteUzivatel("1");

        verify(uzivatelRepository, times(1)).deleteById("1");
    }
}
