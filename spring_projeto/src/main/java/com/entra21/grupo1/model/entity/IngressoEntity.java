package com.entra21.grupo1.model.entity;

import com.entra21.grupo1.model.dto.IngressoDTO;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table(name = "ingresso")
public class IngressoEntity {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_sessao", referencedColumnName = "id")
    private SessaoEntity sessao;

    @ManyToOne
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
    private PessoaEntity pessoa;

    @ManyToOne
    @JoinColumn(name = "id_cadeira", referencedColumnName = "id")
    private CadeiraEntity cadeira;

    @Column(name = "data_compra")
    private LocalDate dataCompra;
}
