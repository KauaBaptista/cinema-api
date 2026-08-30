package br.com.barros.Cinema.service;

import br.com.barros.Cinema.database.model.RoleEntity;
import br.com.barros.Cinema.database.model.UserEntity;
import br.com.barros.Cinema.database.repository.RoleRepository;
import br.com.barros.Cinema.database.repository.UserRepository;
import br.com.barros.Cinema.enums.RoleTypeEnum;
import br.com.barros.Cinema.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public void adminPromote(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        RoleEntity adminRole = roleRepository.findByNome(RoleTypeEnum.ROLE_ADMIN.name())
                .orElseThrow(() -> new NotFoundException("Role ADMIN não configurada"));

        if (!user.getRoles().contains(adminRole)) {
            user.getRoles().add(adminRole);
            userRepository.save(user);
        }
    }
}
