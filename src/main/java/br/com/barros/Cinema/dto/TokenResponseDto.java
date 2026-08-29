package br.com.barros.Cinema.dto;

public record TokenResponseDto(String token, Long expirationTime) {
}
