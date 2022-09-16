package com.entra21.grupo1.view.repository;

import com.entra21.grupo1.model.entity.IngressoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngressoRepository extends JpaRepository<IngressoEntity, Long> {
    public List<IngressoEntity> findAllByIdPessoa(Long idPessoa);
    public List<IngressoEntity> findAllByIdSessao(Long idSessao);
}
