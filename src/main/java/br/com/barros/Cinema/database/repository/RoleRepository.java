package br.com.barros.Cinema.database.repository;

import br.com.barros.Cinema.database.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByNome(String nome);
}
