package br.com.barros.Cinema.controller;

import br.com.barros.Cinema.dto.LoginRequestDto;
import br.com.barros.Cinema.dto.RegisterRequestDto;
import br.com.barros.Cinema.dto.TokenResponseDto;
import br.com.barros.Cinema.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@Valid @RequestBody RegisterRequestDto registerRequestDto) throws BadRequestException {
        return authService.register(registerRequestDto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponseDto login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }
}
