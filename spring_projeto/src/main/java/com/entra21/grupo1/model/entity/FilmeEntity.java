package com.entra21.grupo1.model.entity;

import com.entra21.grupo1.model.dto.FilmeDTO;
import com.entra21.grupo1.model.dto.GeneroDTO;
import com.entra21.grupo1.model.dto.SessaoDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "filme")
public class FilmeEntity {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "duracao")
    private LocalTime duracao;

    @Column(name = "sinopse")
    private String sinopse;

    @Column(name = "diretor")
    private String diretor;

    @Column(name = "cartaz")
    private String cartaz;

    @OneToMany(mappedBy = "filme")
    @EqualsAndHashCode.Exclude
    private Set<SessaoEntity> sessoes;

    @OneToMany(mappedBy = "filme")
    @EqualsAndHashCode.Exclude
    private Set<AvaliacaoEntity> avaliacoes;

    @ManyToMany
    @JoinTable(
            name = "filme_genero",
            joinColumns = @JoinColumn(name = "id_filme"),
            inverseJoinColumns = @JoinColumn(name = "id_genero")
    )
    @EqualsAndHashCode.Exclude
    private Set<GeneroEntity> generos;

    private Double getMediaNotas(){
        Double soma = 0.0;
        if(!this.avaliacoes.isEmpty()){
            for(AvaliacaoEntity avaliacao : this.avaliacoes){
                soma += avaliacao.getRating();
            }
            return soma/avaliacoes.toArray().length;
        }else {
            return null;
        }
    }

    public FilmeDTO toDTO() {
        FilmeDTO filmeDTO = new FilmeDTO();
        filmeDTO.setId(this.id);
        filmeDTO.setNome(this.nome);
        filmeDTO.setCartaz(this.cartaz);
//        if(!this.getSessoes().isEmpty()){
//            filmeDTO.setSessoes(
//                    this.getSessoes().stream().map( s -> {
//                        SessaoDTO sessaoDTO = s.toDTO();
//                        if(s.getDataSessao().toLocalDate().isAfter(LocalDateTime.now().toLocalDate()) || s.getDataSessao().toLocalDate().equals(LocalDateTime.now().toLocalDate())){
//                            return sessaoDTO;
//                        }else{
//                            return null;
//                        }
//                    }).filter(Objects::nonNull).collect(Collectors.toList())
//            );
//        }
        return filmeDTO;
    }

    public FilmeDTO toDTOWithDetails() {
        FilmeDTO filmeDTO = this.toDTO();
        filmeDTO.setDuracao(this.getDuracao());
        filmeDTO.setSinopse(this.getSinopse());
        filmeDTO.setDiretor(this.getDiretor());
        filmeDTO.setMediaNotas(this.getMediaNotas());
        filmeDTO.setGeneros(
            this.getGeneros().stream().map(GeneroEntity::toDTO).collect(Collectors.toList())
        );
        filmeDTO.setAvaliacoes(
                this.getAvaliacoes().stream().map(AvaliacaoEntity::toDTO).collect(Collectors.toList())
        );
        return filmeDTO;
    }
}
