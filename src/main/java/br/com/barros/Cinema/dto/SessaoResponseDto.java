package br.com.barros.Cinema.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SessaoResponseDto (
        Long id,
        LocalDateTime hora,
        Integer sala,
        BigDecimal preco,
        Long filmeId
){}
