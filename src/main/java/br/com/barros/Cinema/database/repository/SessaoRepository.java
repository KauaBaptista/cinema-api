package br.com.barros.Cinema.database.repository;

import br.com.barros.Cinema.database.model.SessaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessaoRepository extends JpaRepository<SessaoEntity, Long> {
}
