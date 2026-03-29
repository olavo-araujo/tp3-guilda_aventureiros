package infnet.tp2_springboot.domain.aventura;

import infnet.tp2_springboot.enums.PapelMissao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(
        name = "participacoes_missao",
        schema = "aventura",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"missao_id", "aventureiro_id"})
        }
)
public class ParticipacaoMissao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "missao_id", nullable = false)
    private Missao missao;

    @ManyToOne
    @JoinColumn(name = "aventureiro_id", nullable = false)
    private Aventureiro aventureiro;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel_missao", nullable = false)
    private PapelMissao papel;

    @Min(0)
    @Column(name = "recompensa_ouro")
    private Integer recompensaOuro;

    @Column(name = "destaque_mvp", nullable = false)
    private Boolean destaqueMvp;

    @CreationTimestamp
    @Column(name = "data_registro", updatable = false)
    private OffsetDateTime dataRegistro;
}