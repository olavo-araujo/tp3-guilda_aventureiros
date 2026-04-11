package infnet.tp3_springboot.repository;

import infnet.tp3_springboot.domain.aventura.Missao;
import infnet.tp3_springboot.dto.MissaoResumoDTO;
import infnet.tp3_springboot.dto.RelatorioMissaoDTO;
import infnet.tp3_springboot.enums.NivelPerigo;
import infnet.tp3_springboot.enums.StatusMissao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;

public interface MissaoRepository extends JpaRepository<Missao, Long> {
    @Query("SELECT new infnet.tp3_springboot.dto.MissaoResumoDTO(m.id, m.titulo, m.nivelPerigo, m.status) " +
            "FROM Missao m WHERE " +
            "(:status IS NULL OR m.status = :status) AND " +
            "(:perigo IS NULL OR m.nivelPerigo = :perigo) AND " +
            "(CAST(:inicio AS timestamp) IS NULL OR m.dataCriacao >= :inicio) AND " +
            "(CAST(:fim AS timestamp) IS NULL OR m.dataCriacao <= :fim)")
    Page<MissaoResumoDTO> buscarComFiltros(@Param("status") StatusMissao status,
                                           @Param("perigo") NivelPerigo perigo,
                                           @Param("inicio") OffsetDateTime inicio,
                                           @Param("fim") OffsetDateTime fim,
                                           Pageable pageable);

    @Query("SELECT new infnet.tp3_springboot.dto.RelatorioMissaoDTO(m.titulo, m.status, m.nivelPerigo, COUNT(p), SUM(p.recompensaOuro)) " +
            "FROM Missao m LEFT JOIN m.participacoes p " +
            "WHERE m.dataCriacao BETWEEN :inicio AND :fim " +
            "GROUP BY m.id, m.titulo, m.status, m.nivelPerigo")
    Page<RelatorioMissaoDTO> gerarRelatorioMetricas(@Param("inicio") OffsetDateTime inicio,
                                                    @Param("fim") OffsetDateTime fim,
                                                    Pageable pageable);
}