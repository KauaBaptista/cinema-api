package br.com.barros.Cinema.dto;

import java.util.List;

public record FilmeResponseDto (
        Long id,
        String titulo,
        Integer duracao,
        String tema,
        String sinopse,
        String diretor,
        Integer classificacaoIndicativa,
        String produtora,
        List<Long> sessoesId
){}
