package br.com.barros.Cinema.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDto(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String senha
) {
}
