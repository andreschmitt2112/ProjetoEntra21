package com.entra21.grupo1.view.repository;

import com.entra21.grupo1.model.entity.GeneroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneroRepository extends JpaRepository<GeneroEntity, Long> {
    Optional<GeneroEntity> findByNome(String nome);
}
