package br.edu.faculdade.clash_of_clans_app.repository;

import br.edu.faculdade.clash_of_clans_app.entity.CartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartaRepository extends JpaRepository<CartaEntity, Long> {
    
    boolean existsByNomeIgnoreCase(String nome);
    
    Optional<CartaEntity> findByNomeIgnoreCase(String nome);
}
