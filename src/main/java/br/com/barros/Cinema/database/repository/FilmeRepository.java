package br.com.barros.Cinema.database.repository;

import br.com.barros.Cinema.database.model.FilmeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmeRepository extends JpaRepository<FilmeEntity, Long> {
}
