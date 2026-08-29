package br.com.barros.Cinema.dto;

import java.time.LocalDateTime;

public record SessaoResponseDto (
        Long id,
        LocalDateTime hora,
        Integer sala,
        Double preco,
        Long filmeId
){}
