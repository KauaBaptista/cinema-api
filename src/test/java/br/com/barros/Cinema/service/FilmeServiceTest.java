package br.com.barros.Cinema.service;

import br.com.barros.Cinema.database.model.FilmeEntity;
import br.com.barros.Cinema.database.repository.FilmeRepository;
import br.com.barros.Cinema.dto.FilmeResponseDto;
import br.com.barros.Cinema.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FilmeServiceTest {
    @Mock
    private FilmeRepository filmeRepository;

    @InjectMocks
    private FilmeService filmeService;

    @Test
    @DisplayName("Deve retornar um filme com sucesso quando o ID existir")
    void findById_Sucesso() {
        FilmeEntity filmeMock = new FilmeEntity(1L,"titulo", 1, "tema",
                "sinopse", "diretor", 1, "produtora", List.of());
        Mockito.when(filmeRepository.findById(1L))
                .thenReturn(Optional.of(filmeMock));

        FilmeResponseDto resultado = filmeService.findById(1L);
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("titulo", resultado.titulo());
        Mockito.verify(filmeRepository, Mockito.times(1)).findById(1L);

    }

    @Test
    @DisplayName("Deve lançar exceção quando o filme não for encontrado")
    void findById_NaoEncontrado() {
        Mockito.when(filmeRepository.findById(9999L))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> filmeService.findById(9999L));
    }
}
