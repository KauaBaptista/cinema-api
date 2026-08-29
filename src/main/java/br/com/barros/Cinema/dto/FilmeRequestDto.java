package br.com.barros.Cinema.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record FilmeRequestDto(
        @NotBlank
        String titulo,
        @NotNull
        @Positive
        Integer duracao,
        @NotBlank
        String tema,
        @NotBlank
        String sinopse,
        @NotBlank
        String diretor,
        @NotNull
        @Positive
        Integer classificacaoIndicativa,
        @NotBlank
        String produtora,
        List<Long> sessoesId
) {}
