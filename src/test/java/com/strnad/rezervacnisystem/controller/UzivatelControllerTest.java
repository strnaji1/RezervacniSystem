package com.strnad.rezervacnisystem.controller;

import com.strnad.rezervacnisystem.model.Uzivatel;
import com.strnad.rezervacnisystem.service.UzivatelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
public class UzivatelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UzivatelService uzivatelService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetAllUzivatele() throws Exception {
        Uzivatel uzivatel1 = new Uzivatel("1", "Jmeno1", "email1@test.com", "heslo1", "ROLE_ADMIN");
        Uzivatel uzivatel2 = new Uzivatel("2", "Jmeno2", "email2@test.com", "heslo2", "ROLE_USER");

        when(uzivatelService.getAllUzivatele()).thenReturn(Arrays.asList(uzivatel1, uzivatel2));

        mockMvc.perform(get("/api/uzivatele"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].jmeno").value("Jmeno1"))
                .andExpect(jsonPath("$[1].jmeno").value("Jmeno2"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreateUzivatel() throws Exception {
        Uzivatel uzivatel = new Uzivatel("1", "Jmeno", "email@test.com", "heslo", "ROLE_USER");

        when(uzivatelService.createUzivatel(any(Uzivatel.class))).thenReturn(uzivatel);

        mockMvc.perform(post("/api/uzivatele")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"jmeno\":\"Jmeno\", \"email\":\"email@test.com\", \"heslo\":\"heslo\", \"role\":\"ROLE_USER\"}")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jmeno").value("Jmeno"))
                .andExpect(jsonPath("$.email").value("email@test.com"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetUzivatelById() throws Exception {
        Uzivatel uzivatel = new Uzivatel("1", "Jmeno", "email@test.com", "heslo", "ROLE_USER");

        when(uzivatelService.getUzivatelById("1")).thenReturn(uzivatel);

        mockMvc.perform(get("/api/uzivatele/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jmeno").value("Jmeno"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testUpdateUzivatel() throws Exception {
        Uzivatel uzivatel = new Uzivatel("1", "UpdatedJmeno", "email@test.com", "heslo", "ROLE_USER");

        when(uzivatelService.updateUzivatel(any(Uzivatel.class))).thenReturn(uzivatel);

        mockMvc.perform(put("/api/uzivatele/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"jmeno\":\"UpdatedJmeno\", \"email\":\"email@test.com\", \"heslo\":\"heslo\", \"role\":\"ROLE_USER\"}")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jmeno").value("UpdatedJmeno"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteUzivatel() throws Exception {
        doNothing().when(uzivatelService).deleteUzivatel("1");

        mockMvc.perform(delete("/api/uzivatele/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }
}
