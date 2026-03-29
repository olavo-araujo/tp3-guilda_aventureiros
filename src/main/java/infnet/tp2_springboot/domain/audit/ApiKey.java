package infnet.tp2_springboot.domain.audit;

import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(
        name = "api_keys",
        schema = "audit",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"organizacao_id", "nome"})
        }
)
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @Column(nullable = false)
    private String nome;

    @Column(name = "key_hash", nullable = false)
    private String keyHash;

    private Boolean ativo;

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "last_used_at")
    private OffsetDateTime lastUsedAt;
}