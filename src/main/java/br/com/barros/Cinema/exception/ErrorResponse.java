package br.com.barros.Cinema.exception;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@ToString
public class ErrorResponse {
    LocalDateTime timestamp;
    String message;
    Integer status;
}
