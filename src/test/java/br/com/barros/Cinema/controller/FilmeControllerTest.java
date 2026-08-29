package br.com.barros.Cinema.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class FilmeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Deve retornar Status 200 quando buscar a lista de filmes sem precisar de Token")
    void findAll_Sucesso() throws Exception {
        mockMvc.perform(get("/filmes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar Status 401 Unauthorized quando tentar cadastrar um filme sem Token")
    void save_SemToken_Unauthorized() throws Exception {
        String jsonBody = """
                {
                    "titulo": "titulo",
                    "duracao": 1,
                    "tema": "tema",
                    "sinopse": "sinopse",
                    "diretor": "diretor",
                    "classificacaoIndicativa": 1,
                    "produtora": "produtora"
                }
                """;

        mockMvc.perform(post("/filmes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Deve retornar Status 404 Not Found ao buscar ID inexistente")
    void findById_NaoEncontrado() throws Exception {
        mockMvc.perform(get("/filmes/99999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Deve retornar Status 403 Forbidden ao tentar salvar com perfil USER")
    void save_ComRoleUser_Forbidden() throws Exception {
        String jsonBody = """
                {
                    "titulo": "titulo",
                    "duracao": 1,
                    "tema": "tema",
                    "sinopse": "sinopse",
                    "diretor": "diretor",
                    "classificacaoIndicativa": 1,
                    "produtora": "produtora"
                }
                """;

        mockMvc.perform(post("/filmes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deve retornar Status 201 Created ao salvar com perfil ADMIN")
    void save_Admin_Sucesso() throws Exception {
        String jsonBody = """
                {
                    "titulo": "titulo",
                    "duracao": 1,
                    "tema": "tema",
                    "sinopse": "sinopse",
                    "diretor": "diretor",
                    "classificacaoIndicativa": 1,
                    "produtora": "produtora"
                }
                """;

        mockMvc.perform(post("/filmes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deve retornar Status 200 OK ao atualizar um filme existente no banco! Com perfil ADMIN")
    void update_Admin_Sucesso() throws Exception {
        String jsonBody = """
                {
                    "titulo": "tituloTrocado",
                    "duracao": 2,
                    "tema": "temaTrocado",
                    "sinopse": "sinopseTrocado",
                    "diretor": "diretorTrocado",
                    "classificacaoIndicativa": 2,
                    "produtora": "produtoraTrocado"
                }
                """;

        mockMvc.perform(put("/filmes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Deve retornar Status 403 Forbidden ao tentar deletar com perfil USER")
    void delete_ComRoleUser_Forbidden() throws Exception {
        mockMvc.perform(delete("/filmes/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deve deletar filme com sucesso ao usar perfil ADMIN")
    void delete_ComRoleAdmin_Sucesso() throws Exception {
        mockMvc.perform(delete("/filmes/1"))
                .andExpect(status().isNoContent());
    }
}