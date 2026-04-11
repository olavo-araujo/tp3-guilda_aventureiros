package infnet.tp3_springboot.domain.aventura;

import infnet.tp3_springboot.domain.audit.Organizacao;
import infnet.tp3_springboot.domain.audit.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "aventureiros", schema = "aventura")
public class Aventureiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuarioResponsavel;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false)
    private String classe;

    @Min(1)
    @Column(nullable = false)
    private Integer nivel;

    @Column(nullable = false)
    private Boolean ativo;

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false)
    private OffsetDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private OffsetDateTime dataAtualizacao;

    @OneToOne(mappedBy = "aventureiro", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private Companheiro companheiro;
}