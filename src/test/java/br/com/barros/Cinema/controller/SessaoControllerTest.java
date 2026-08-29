package br.com.barros.Cinema.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SessaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Deve retornar Status 200 OK ao listar todas as sessões sem autenticação")
    void findAll_Sucesso() throws Exception {
        mockMvc.perform(get("/sessoes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar Status 401 Unauthorized ao criar sessão sem token")
    void save_Token_Unauthorized() throws Exception {
        String jsonBody = """
                {
                    "dataHora": "2026-10-10T20:00:00",
                    "sala": 1,
                    "precoIngresso": 25.0,
                    "filmeId": 1
                }
                """;

        mockMvc.perform(post("/sessoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Deve retornar Status 403 Forbidden ao tentar criar sessão com perfil USER")
    void save_User_Forbidden() throws Exception {
        String jsonBody = """
                {
                    "dataHora": "2026-10-10T20:00:00",
                    "sala": 1,
                    "precoIngresso": 25.0,
                    "filmeId": 1
                }
                """;

        mockMvc.perform(post("/sessoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deve retornar Status 404 ao tentar deletar sessão inexistente como ADMIN")
    void delete_Admin_NaoEncontrado() throws Exception {
        mockMvc.perform(delete("/sessoes/9999"))
                .andExpect(status().isNotFound());
    }
}
