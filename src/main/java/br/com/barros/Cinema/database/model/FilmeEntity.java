package br.com.barros.Cinema.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "filme")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FilmeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String titulo;
    @Column
    private Integer duracao;
    @Column
    private String tema;
    @Column
    private String sinopse;
    @Column
    private String diretor;
    @Column
    private Integer classificacaoIndicativa;
    @Column
    private String produtora;

    @OneToMany(mappedBy = "filmeId", cascade = CascadeType.ALL)
    private List<SessaoEntity> sessoesId =  new ArrayList<>();
}
