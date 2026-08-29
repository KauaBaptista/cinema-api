package br.com.barros.Cinema.service;

import br.com.barros.Cinema.config.TokenProvider;
import br.com.barros.Cinema.database.model.RoleEntity;
import br.com.barros.Cinema.database.model.UserEntity;
import br.com.barros.Cinema.database.repository.RoleRepository;
import br.com.barros.Cinema.database.repository.UserRepository;
import br.com.barros.Cinema.dto.LoginRequestDto;
import br.com.barros.Cinema.dto.RegisterRequestDto;
import br.com.barros.Cinema.dto.TokenResponseDto;
import br.com.barros.Cinema.enums.RoleTypeEnum;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthService {

    @Value("${jwt.expiration}")
    private Long expirationTime;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Transactional
    public String register(RegisterRequestDto registerRequestDto) throws BadRequestException {

        UserEntity email = userRepository.findByEmail(registerRequestDto.email())
                .orElse(null);
        if (email != null) {
            throw new BadRequestException("Usuário ja cadastrado");
        }

        RoleEntity roles = roleRepository.findByNome(RoleTypeEnum.ROLE_USER.name())
                .orElseGet(() -> roleRepository.save(RoleEntity.builder()
                        .nome(RoleTypeEnum.ROLE_USER.name())
                        .build()));

        userRepository.save(UserEntity.builder()
                .nome(registerRequestDto.nome())
                .email(registerRequestDto.email())
                .senha(passwordEncoder.encode(registerRequestDto.senha()))
                .roles(List.of(roles))
                .build());

        return "Usuário cadastrado com sucesso";
    }

    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        try {
            UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(loginRequestDto.email(), loginRequestDto.senha());
            Authentication auth = authenticationManager.authenticate(user);

            String token = tokenProvider.generateToken(auth);
            return new TokenResponseDto(token, expirationTime);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Credenciais incorretas");
        }
    }
}
