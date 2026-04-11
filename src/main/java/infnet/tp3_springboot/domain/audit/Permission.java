package infnet.tp3_springboot.domain.audit;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "permissions", schema = "audit")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    private String descricao;
}