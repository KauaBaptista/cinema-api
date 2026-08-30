package br.com.barros.Cinema.config;

import br.com.barros.Cinema.database.model.RoleEntity;
import br.com.barros.Cinema.database.model.UserEntity;
import br.com.barros.Cinema.database.repository.RoleRepository;
import br.com.barros.Cinema.database.repository.UserRepository;
import br.com.barros.Cinema.enums.RoleTypeEnum;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class AdminSeeder implements CommandLineRunner {

    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.password}")
    private String adminPassword;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String @NonNull ... args) {
        if (userRepository.findByEmail(adminEmail).isPresent()) {
            return;
        }

        RoleEntity adminRole = roleRepository.findByNome(RoleTypeEnum.ROLE_ADMIN.name())
                .orElseGet(() -> roleRepository.save(RoleEntity.builder()
                        .nome(RoleTypeEnum.ROLE_ADMIN.name())
                        .build()));

        userRepository.save(UserEntity.builder()
                .nome("Admin")
                .email(adminEmail)
                .senha(passwordEncoder.encode(adminPassword))
                .roles(List.of(adminRole))
                .build());
    }
}
