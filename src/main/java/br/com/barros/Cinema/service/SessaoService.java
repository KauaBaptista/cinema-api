package br.com.barros.Cinema.service;

import br.com.barros.Cinema.database.model.FilmeEntity;
import br.com.barros.Cinema.database.model.SessaoEntity;
import br.com.barros.Cinema.database.repository.FilmeRepository;
import br.com.barros.Cinema.database.repository.SessaoRepository;
import br.com.barros.Cinema.dto.SessaoRequestDto;
import br.com.barros.Cinema.dto.SessaoResponseDto;
import br.com.barros.Cinema.exception.NotFoundExeption;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
                .orElseThrow(() -> new NotFoundExeption("Sessão não encontrada"));
        return new SessaoResponseDto(sessaoEntity.getId(),
                sessaoEntity.getHora(),
                sessaoEntity.getSala(),
                sessaoEntity.getPreco(),
                sessaoEntity.getFilmeId().getId());
    }

    public List<SessaoResponseDto> findAll() {
        List<SessaoResponseDto> sessoesResponseDto = new ArrayList<>();
        List<SessaoEntity> sessoes = sessaoRepository.findAll();
        for (SessaoEntity sessao : sessoes) {
            SessaoResponseDto sessaoResponseDto = new SessaoResponseDto(
                    sessao.getId(),
                    sessao.getHora(),
                    sessao.getSala(),
                    sessao.getPreco(),
                    sessao.getFilmeId().getId());
            sessoesResponseDto.add(sessaoResponseDto);
        }
        return sessoesResponseDto;
    }

    @Transactional
    public SessaoEntity save(SessaoRequestDto sessaoRequestDto) {

        filmeRepository.findById(sessaoRequestDto.filmeId())
                .orElseThrow(() -> new NotFoundExeption("filmeId não encontrado"));

        return sessaoRepository.save(SessaoEntity.builder()
                .hora(sessaoRequestDto.hora())
                .sala(sessaoRequestDto.sala())
                .preco(sessaoRequestDto.preco())
                .filmeId(FilmeEntity.builder().id(sessaoRequestDto.filmeId()).build())
                .build());
    }

    @Transactional
    public void delete(Long id) {
        sessaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundExeption("Id não encontrado"));
        sessaoRepository.deleteById(id);
    }

    @Transactional
    public SessaoResponseDto update(SessaoRequestDto sessaoRequestDto, Long id) {
        SessaoEntity sessao = sessaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundExeption("Id não encontrado"));
        sessao.setHora(sessaoRequestDto.hora());
        sessao.setSala(sessaoRequestDto.sala());
        sessao.setPreco(sessaoRequestDto.preco());
        sessao.setFilmeId(FilmeEntity.builder().id(sessaoRequestDto.filmeId()).build());
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
