package com.strnad.rezervacnisystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.strnad.rezervacnisystem.model.Kurt;
import com.strnad.rezervacnisystem.service.KurtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(KurtController.class)
public class KurtControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KurtService kurtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetAllKurty() throws Exception {
        Kurt kurt1 = new Kurt();
        kurt1.setId("1");
        kurt1.setNazev("Kurt1");

        Kurt kurt2 = new Kurt();
        kurt2.setId("2");
        kurt2.setNazev("Kurt2");

        when(kurtService.getAllKurty()).thenReturn(Arrays.asList(kurt1, kurt2));

        mockMvc.perform(get("/api/kurty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nazev").value("Kurt1"))
                .andExpect(jsonPath("$[1].nazev").value("Kurt2"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreateKurt() throws Exception {
        Kurt kurt = new Kurt();
        kurt.setId("1");
        kurt.setNazev("Kurt");

        when(kurtService.createKurt(any(Kurt.class))).thenReturn(kurt);

        mockMvc.perform(post("/api/kurty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(kurt))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nazev").value("Kurt"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetKurtById() throws Exception {
        Kurt kurt = new Kurt();
        kurt.setId("1");
        kurt.setNazev("Kurt");

        when(kurtService.getKurtById("1")).thenReturn(Optional.of(kurt));

        mockMvc.perform(get("/api/kurty/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nazev").value("Kurt"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testUpdateKurt() throws Exception {
        Kurt kurt = new Kurt();
        kurt.setId("1");
        kurt.setNazev("Updated");

        when(kurtService.updateKurt(anyString(), any(Kurt.class))).thenReturn(Optional.of(kurt));

        mockMvc.perform(put("/api/kurty/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nazev\":\"Updated\"}")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nazev").value("Updated"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteKurt() throws Exception {
        doNothing().when(kurtService).deleteKurt("1");

        mockMvc.perform(delete("/api/kurty/1").with(csrf()))
                .andExpect(status().isNoContent());
    }
}
