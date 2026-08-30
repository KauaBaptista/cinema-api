package br.com.barros.Cinema.service;

import br.com.barros.Cinema.database.model.FilmeEntity;
import br.com.barros.Cinema.database.model.SessaoEntity;
import br.com.barros.Cinema.database.repository.FilmeRepository;
import br.com.barros.Cinema.database.repository.SessaoRepository;
import br.com.barros.Cinema.dto.SessaoRequestDto;
import br.com.barros.Cinema.dto.SessaoResponseDto;
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
public class SessaoService {

    private final SessaoRepository sessaoRepository;
    private final FilmeRepository filmeRepository;

    public SessaoResponseDto findById(Long id) {
        SessaoEntity sessaoEntity = sessaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sessão não encontrada"));
        return new SessaoResponseDto(sessaoEntity.getId(),
                sessaoEntity.getHora(),
                sessaoEntity.getSala(),
                sessaoEntity.getPreco(),
                sessaoEntity.getFilme().getId());
    }

    public List<SessaoResponseDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SessaoEntity> sessaoPage = sessaoRepository.findAll(pageable);
        List<SessaoEntity> sessao = sessaoPage.getContent();

        List<SessaoResponseDto> sessoesResponseDto = new ArrayList<>();
        for (SessaoEntity sessaoEntity : sessao) {
            SessaoResponseDto sessaoResponseDto = new SessaoResponseDto(
                    sessaoEntity.getId(),
                    sessaoEntity.getHora(),
                    sessaoEntity.getSala(),
                    sessaoEntity.getPreco(),
                    sessaoEntity.getFilme().getId());
            sessoesResponseDto.add(sessaoResponseDto);
        }
        return sessoesResponseDto;
    }

    @Transactional
    public SessaoResponseDto save(SessaoRequestDto sessaoRequestDto) {

        filmeRepository.findById(sessaoRequestDto.filmeId())
                .orElseThrow(() -> new NotFoundException("filmeId não encontrado"));

        SessaoEntity save = sessaoRepository.save(SessaoEntity.builder()
                .hora(sessaoRequestDto.hora())
                .sala(sessaoRequestDto.sala())
                .preco(sessaoRequestDto.preco())
                .filme(FilmeEntity.builder().id(sessaoRequestDto.filmeId()).build())
                .build());

        return new  SessaoResponseDto(
                save.getId(),
                save.getHora(),
                save.getSala(),
                save.getPreco(),
                save.getFilme().getId()
        );
    }

    @Transactional
    public void delete(Long id) {
        sessaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Id não encontrado"));
        sessaoRepository.deleteById(id);
    }

    @Transactional
    public SessaoResponseDto update(SessaoRequestDto sessaoRequestDto, Long id) {
        SessaoEntity sessao = sessaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Id não encontrado"));
        sessao.setHora(sessaoRequestDto.hora());
        sessao.setSala(sessaoRequestDto.sala());
        sessao.setPreco(sessaoRequestDto.preco());
        sessao.setFilme(FilmeEntity.builder().id(sessaoRequestDto.filmeId()).build());
        sessaoRepository.save(sessao);

        return new SessaoResponseDto(
                id,
                sessaoRequestDto.hora(),
                sessaoRequestDto.sala(),
                sessaoRequestDto.preco(),
                sessaoRequestDto.filmeId()
        );

    }
}
