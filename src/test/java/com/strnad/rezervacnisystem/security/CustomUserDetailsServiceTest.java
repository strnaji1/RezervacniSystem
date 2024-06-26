package com.strnad.rezervacnisystem.security;

import com.strnad.rezervacnisystem.model.Uzivatel;
import com.strnad.rezervacnisystem.repository.UzivatelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomUserDetailsServiceTest {

    @MockBean
    private UzivatelRepository uzivatelRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void testLoadUserByUsername_UserFound() {
        // Příprava
        String email = "test@example.com";
        Uzivatel mockUzivatel = new Uzivatel();
        mockUzivatel.setEmail(email);
        mockUzivatel.setHeslo("password");
        mockUzivatel.setRole("USER");
        when(uzivatelRepository.findByEmail(email)).thenReturn(mockUzivatel);

        // Akce
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Ověření
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Příprava
        String email = "nonexistent@example.com";
        when(uzivatelRepository.findByEmail(email)).thenReturn(null);

        // Akce & ověření
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername(email);
        });
    }
}
