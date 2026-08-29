package br.com.barros.Cinema.service;

import br.com.barros.Cinema.database.model.FilmeEntity;
import br.com.barros.Cinema.database.model.SessaoEntity;
import br.com.barros.Cinema.database.repository.FilmeRepository;
import br.com.barros.Cinema.dto.FilmeRequestDto;
import br.com.barros.Cinema.dto.FilmeResponseDto;
import br.com.barros.Cinema.exception.NotFoundExeption;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmeService {
    private final FilmeRepository filmeRepository;

    public FilmeResponseDto findById(Long id) {
        FilmeEntity filmeEntity = filmeRepository.findById(id)
                .orElseThrow(() -> new NotFoundExeption("Filme não encontrado"));
        List<Long> sessoesId = new ArrayList<>();
        for (SessaoEntity sessoes : filmeEntity.getSessoesId()) {
            sessoesId.add(sessoes.getId());
        }
        return new FilmeResponseDto(filmeEntity.getId(),
                filmeEntity.getTitulo(),
                filmeEntity.getDuracao(),
                filmeEntity.getTema(),
                filmeEntity.getSinopse(),
                filmeEntity.getDiretor(),
                filmeEntity.getClassificacaoIndicativa(),
                filmeEntity.getProdutora(),
                sessoesId);
    }

    public List<FilmeResponseDto> findAll() {
        List<FilmeResponseDto> filmesResponseDto = new ArrayList<>();
        List<FilmeEntity> filmes = filmeRepository.findAll();
        for (FilmeEntity filme : filmes) {
            FilmeResponseDto filmeResponseDto = new FilmeResponseDto(
                    filme.getId(),
                    filme.getTitulo(),
                    filme.getDuracao(),
                    filme.getTema(),
                    filme.getSinopse(),
                    filme.getDiretor(),
                    filme.getClassificacaoIndicativa(),
                    filme.getProdutora(),
                    filme.getSessoesId().stream()
                            .map(SessaoEntity::getId)
                            .toList());
            filmesResponseDto.add(filmeResponseDto);
        }
        return filmesResponseDto;
    }

    @Transactional
    public FilmeEntity save(FilmeRequestDto filmeRequestDto) {

        return filmeRepository.save(FilmeEntity.builder()
                .tema(filmeRequestDto.tema())
                .diretor(filmeRequestDto.diretor())
                .duracao(filmeRequestDto.duracao())
                .sinopse(filmeRequestDto.sinopse())
                .titulo(filmeRequestDto.titulo())
                .produtora(filmeRequestDto.produtora())
                .classificacaoIndicativa(filmeRequestDto.classificacaoIndicativa())
                .build());
    }

    @Transactional
    public void delete(Long id) {
        filmeRepository.findById(id)
                .orElseThrow(() -> new NotFoundExeption("Id não encontrado"));
        filmeRepository.deleteById(id);
    }

    @Transactional
    public FilmeEntity update(FilmeRequestDto filmeRequestDto, Long id) {
        FilmeEntity filmeEntity = filmeRepository.findById(id)
                .orElseThrow(() -> new NotFoundExeption("Id não encontrado"));
        filmeEntity.setTitulo(filmeRequestDto.titulo());
        filmeEntity.setDuracao(filmeRequestDto.duracao());
        filmeEntity.setSinopse(filmeRequestDto.sinopse());
        filmeEntity.setDiretor(filmeRequestDto.diretor());
        filmeEntity.setTema(filmeRequestDto.tema());
        filmeEntity.setProdutora(filmeRequestDto.produtora());
        filmeEntity.setClassificacaoIndicativa(filmeRequestDto.classificacaoIndicativa());
        return filmeRepository.save(filmeEntity);
    }
}
