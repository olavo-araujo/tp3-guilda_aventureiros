package infnet.tp2_springboot.domain.audit;

import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Entity
@Table(
        name = "roles",
        schema = "audit",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"organizacao_id", "nome"})
        }
)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "role_permissions",
            schema = "audit",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<Permission> permissions;
}