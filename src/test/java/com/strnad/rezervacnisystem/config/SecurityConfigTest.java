package com.strnad.rezervacnisystem.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenAccessPublicEndpoint_thenOk() throws Exception {
        mockMvc.perform(get("/api/test"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenAccessKurtyEndpointWithoutAuth_thenRedirectToLogin() throws Exception {
        mockMvc.perform(get("/api/kurty"))
                .andExpect(status().isFound())  // 302 Redirect to login page
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void whenAccessKurtyEndpointWithUserRole_thenOk() throws Exception {
        mockMvc.perform(get("/api/kurty"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenAccessUzivateleEndpointWithAdminRole_thenOk() throws Exception {
        mockMvc.perform(get("/api/uzivatele"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void whenAccessUzivateleEndpointWithUserRole_thenForbidden() throws Exception {
        mockMvc.perform(get("/api/uzivatele"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenUserLogsOut_thenRedirectToLoginPage() throws Exception {
        mockMvc.perform(post("/logout").with(csrf()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/login"));
    }
}
