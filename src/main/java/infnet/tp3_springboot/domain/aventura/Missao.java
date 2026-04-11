package infnet.tp3_springboot.domain.aventura;

import infnet.tp3_springboot.domain.audit.Organizacao;
import infnet.tp3_springboot.enums.NivelPerigo;
import infnet.tp3_springboot.enums.StatusMissao;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "missoes", schema = "aventura")
public class Missao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_perigo", nullable = false)
    private NivelPerigo nivelPerigo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusMissao status;

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false)
    private OffsetDateTime dataCriacao;

    @Column(name = "data_inicio")
    private OffsetDateTime dataInicio;

    @Column(name = "data_termino")
    private OffsetDateTime dataTermino;

    @OneToMany(mappedBy = "missao")
    private List<ParticipacaoMissao> participacoes = new java.util.ArrayList<>();
}