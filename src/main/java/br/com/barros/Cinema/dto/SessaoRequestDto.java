package br.com.barros.Cinema.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SessaoRequestDto(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime hora,
        @NotNull
        @Positive
        Integer sala,
        @NotNull
        @Positive
        BigDecimal preco,
        @NotNull
        Long filmeId
) {}
