package com.entra21.grupo1.model.entity;

import com.entra21.grupo1.model.dto.SessaoDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sessao")
public class SessaoEntity {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_sessao")
    private LocalDateTime dataSessao;

    @ManyToOne
    @JoinColumn(name = "id_sala", referencedColumnName = "id")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private SalaEntity sala;

    @ManyToOne
    @JoinColumn(name = "id_filme", referencedColumnName = "id")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private FilmeEntity filme;

    @Column(name = "valor_inteira")
    private String valorInteira;

    @Column(name = "valor_meia")
    private String valorMeia;

    @Column(name = "tipo_sessao")
    private String tipoSessao;

    public SessaoDTO toDTO(){
        SessaoDTO sessaoDTO = new SessaoDTO();
        sessaoDTO.setId(this.getId());
        sessaoDTO.setDataSessao(this.getDataSessao());
        sessaoDTO.setValorInteira(this.getValorInteira());
        sessaoDTO.setValorMeia(this.getValorMeia());
        sessaoDTO.setTipoSessao(this.getTipoSessao());
        sessaoDTO.setIdSala(this.getSala().getId());
        return sessaoDTO;
    }
}
