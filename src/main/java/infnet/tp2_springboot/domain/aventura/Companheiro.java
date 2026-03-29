package infnet.tp2_springboot.domain.aventura;

import infnet.tp2_springboot.enums.Especie;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Entity
@Table(name = "companheiros", schema = "aventura")
public class Companheiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "aventureiro_id", nullable = false, unique = true)
    private Aventureiro aventureiro;

    @Column(nullable = false, length = 120)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Especie especie;

    @Min(0)
    @Max(100)
    @Column(name = "indice_lealdade", nullable = false)
    private Integer indiceLealdade;
}