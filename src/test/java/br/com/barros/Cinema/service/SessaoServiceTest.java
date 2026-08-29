package br.com.barros.Cinema.service;

import br.com.barros.Cinema.database.model.FilmeEntity;
import br.com.barros.Cinema.database.model.SessaoEntity;
import br.com.barros.Cinema.database.repository.FilmeRepository;
import br.com.barros.Cinema.database.repository.SessaoRepository;
import br.com.barros.Cinema.dto.SessaoRequestDto;
import br.com.barros.Cinema.dto.SessaoResponseDto;
import br.com.barros.Cinema.exception.NotFoundExeption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SessaoServiceTest {

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private FilmeRepository filmeRepository;

    @InjectMocks
    private SessaoService sessaoService;

    @Test
    @DisplayName("Deve buscar sessão por ID com sucesso")
    void findById_Sucesso() {
        FilmeEntity filmeMock = new FilmeEntity(1L, "titulo", 1,
                "tema", "Sinopse", "Diretor", 1,
                "produtora", List.of());
        SessaoEntity sessaoMock = new SessaoEntity(1L, LocalDateTime.now(), 10.0, 1, filmeMock);

        Mockito.when(sessaoRepository.findById(1L)).thenReturn(Optional.of(sessaoMock));

        SessaoResponseDto resultado = sessaoService.findById(1L);
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(1L, resultado.id());
        Mockito.verify(sessaoRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar sessão com ID inexistente")
    void findById_NaoEncontrado() {
        Mockito.when(sessaoRepository.findById(9999L)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundExeption.class, () -> sessaoService.findById(9999L));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar sessão para filme inexistente")
    void save_FilmeNaoEncontrado() {
        SessaoRequestDto requestDto = new SessaoRequestDto(LocalDateTime.now(), 1, 10.0, 9999L);

        Mockito.when(filmeRepository.findById(9999L)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundExeption.class, () -> sessaoService.save(requestDto));
    }
}