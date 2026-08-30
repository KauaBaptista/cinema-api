package br.com.barros.Cinema.service;

import br.com.barros.Cinema.database.model.FilmeEntity;
import br.com.barros.Cinema.database.model.SessaoEntity;
import br.com.barros.Cinema.database.repository.FilmeRepository;
import br.com.barros.Cinema.dto.FilmeRequestDto;
import br.com.barros.Cinema.dto.FilmeResponseDto;
import br.com.barros.Cinema.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmeService {
    private final FilmeRepository filmeRepository;

    public FilmeResponseDto findById(Long id) {
        FilmeEntity filmeEntity = filmeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Filme não encontrado"));
        List<Long> sessoesId = new ArrayList<>();
        for (SessaoEntity sessoes : filmeEntity.getSessoes()) {
            sessoesId.add(sessoes.getId());
        }
        return new FilmeResponseDto(
                filmeEntity.getId(),
                filmeEntity.getTitulo(),
                filmeEntity.getDuracao(),
                filmeEntity.getTema(),
                filmeEntity.getSinopse(),
                filmeEntity.getDiretor(),
                filmeEntity.getClassificacaoIndicativa(),
                filmeEntity.getProdutora(),
                sessoesId);
    }

    public List<FilmeResponseDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FilmeEntity> filmePage = filmeRepository.findAll(pageable);
        List<FilmeEntity> filme = filmePage.getContent();

        List<FilmeResponseDto> filmesResponseDto = new ArrayList<>();
        for (FilmeEntity filmeEntity : filme) {
            FilmeResponseDto filmeResponseDto = new FilmeResponseDto(
                    filmeEntity.getId(),
                    filmeEntity.getTitulo(),
                    filmeEntity.getDuracao(),
                    filmeEntity.getTema(),
                    filmeEntity.getSinopse(),
                    filmeEntity.getDiretor(),
                    filmeEntity.getClassificacaoIndicativa(),
                    filmeEntity.getProdutora(),
                    filmeEntity.getSessoes().stream().map(SessaoEntity::getId).toList());
            filmesResponseDto.add(filmeResponseDto);
        }
        return filmesResponseDto;
    }

    @Transactional
    public FilmeResponseDto save(FilmeRequestDto filmeRequestDto) {
        FilmeEntity save = filmeRepository.save(FilmeEntity.builder()
                .tema(filmeRequestDto.tema())
                .diretor(filmeRequestDto.diretor())
                .duracao(filmeRequestDto.duracao())
                .sinopse(filmeRequestDto.sinopse())
                .titulo(filmeRequestDto.titulo())
                .produtora(filmeRequestDto.produtora())
                .classificacaoIndicativa(filmeRequestDto.classificacaoIndicativa())
                .build());
        List<Long> sessoesId = new ArrayList<>();
        return new FilmeResponseDto(
                save.getId(),
                save.getTitulo(),
                save.getDuracao(),
                save.getTema(),
                save.getSinopse(),
                save.getDiretor(),
                save.getClassificacaoIndicativa(),
                save.getProdutora(),
                sessoesId);
    }

    @Transactional
    public void delete(Long id) {
        filmeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Id não encontrado"));
        filmeRepository.deleteById(id);
    }

    @Transactional
    public FilmeResponseDto update(FilmeRequestDto filmeRequestDto, Long id) {
        FilmeEntity filmeEntity = filmeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Id não encontrado"));
        filmeEntity.setTitulo(filmeRequestDto.titulo());
        filmeEntity.setDuracao(filmeRequestDto.duracao());
        filmeEntity.setSinopse(filmeRequestDto.sinopse());
        filmeEntity.setDiretor(filmeRequestDto.diretor());
        filmeEntity.setTema(filmeRequestDto.tema());
        filmeEntity.setProdutora(filmeRequestDto.produtora());
        filmeEntity.setClassificacaoIndicativa(filmeRequestDto.classificacaoIndicativa());
        FilmeEntity save = filmeRepository.save(filmeEntity);

        return new FilmeResponseDto(
                save.getId(),
                save.getTitulo(),
                save.getDuracao(),
                save.getTema(),
                save.getSinopse(),
                save.getDiretor(),
                save.getClassificacaoIndicativa(),
                save.getProdutora(),
                save.getSessoes().stream().map(SessaoEntity::getId).toList());
    }
}
