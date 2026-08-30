package br.com.barros.Cinema.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sessao")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SessaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime hora;
    @Column
    private BigDecimal preco;
    @Column
    private Integer sala;

    @ManyToOne
    @JoinColumn(name = "filme_id")
    private FilmeEntity filme;
}
