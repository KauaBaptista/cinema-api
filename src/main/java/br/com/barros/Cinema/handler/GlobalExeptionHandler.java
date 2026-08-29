package br.com.barros.Cinema.handler;

import br.com.barros.Cinema.exception.ErrorResponse;
import br.com.barros.Cinema.exception.NotFoundExeption;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExeptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundExeption.class)
    public ResponseEntity<ErrorResponse> handleNotFoundExeption(NotFoundExeption exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<FieldError> errorResponses = exception.getFieldErrors();

        List<ErrorResponse> responses = errorResponses.stream()
                .map(error -> ErrorResponse.builder()
                        .message(error.getField()+" "+error.getDefaultMessage())
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .build())
                .toList();
        return new ResponseEntity<>(responses, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handlerHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(
                        exception.getMessage())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handlerDataIntegrityViolationException(DataIntegrityViolationException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("Erro de integridade de dados na operação, verifique se as informações passadas estão corretas.\n"
                +exception.getMessage())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

}
