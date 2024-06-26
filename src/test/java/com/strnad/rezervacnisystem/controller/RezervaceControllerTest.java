package com.strnad.rezervacnisystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.strnad.rezervacnisystem.model.Rezervace;
import com.strnad.rezervacnisystem.service.RezervaceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
public class RezervaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RezervaceService rezervaceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void getAllRezervace_shouldReturnAllRezervace() throws Exception {
        Rezervace rezervace1 = new Rezervace("1", "uzivatel1", "kurt1", LocalDateTime.now());
        Rezervace rezervace2 = new Rezervace("2", "uzivatel2", "kurt2", LocalDateTime.now());

        when(rezervaceService.getAllRezervace()).thenReturn(Arrays.asList(rezervace1, rezervace2));

        mockMvc.perform(get("/api/rezervace"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void getRezervaceById_shouldReturnRezervace() throws Exception {
        Rezervace rezervace = new Rezervace("1", "uzivatel1", "kurt1", LocalDateTime.now());

        when(rezervaceService.getRezervaceById("1")).thenReturn(Optional.of(rezervace));

        mockMvc.perform(get("/api/rezervace/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void createRezervace_shouldCreateRezervace() throws Exception {
        Rezervace rezervace = new Rezervace("1", "uzivatel1", "kurt1", LocalDateTime.now());

        when(rezervaceService.createRezervace(any(Rezervace.class))).thenReturn(rezervace);

        mockMvc.perform(post("/api/rezervace")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rezervace))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void updateRezervace_shouldUpdateRezervace() throws Exception {
        Rezervace rezervace = new Rezervace("1", "uzivatel1", "kurt1", LocalDateTime.now());

        when(rezervaceService.updateRezervace(anyString(), any(Rezervace.class))).thenReturn(Optional.of(rezervace));

        mockMvc.perform(put("/api/rezervace/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rezervace))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void deleteRezervace_shouldDeleteRezervace() throws Exception {
        Mockito.doNothing().when(rezervaceService).deleteRezervace("1");

        mockMvc.perform(delete("/api/rezervace/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }
}
