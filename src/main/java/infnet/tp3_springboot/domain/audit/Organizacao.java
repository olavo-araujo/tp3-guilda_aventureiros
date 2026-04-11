package infnet.tp3_springboot.domain.audit;

import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "organizacoes", schema = "audit")
public class Organizacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    private Boolean ativo;

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;
}