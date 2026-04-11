package infnet.tp3_springboot.repository;

import infnet.tp3_springboot.domain.aventura.ParticipacaoMissao;
import infnet.tp3_springboot.dto.RankingAventureiroDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;

public interface ParticipacaoMissaoRepository extends JpaRepository<ParticipacaoMissao, Long> {
    long countByAventureiroId(Long aventureiroId);

    ParticipacaoMissao findFirstByAventureiroIdOrderByDataRegistroDesc(Long aventureiroId);
    List<ParticipacaoMissao> findByMissaoId(Long missaoId);

    @Query("SELECT new infnet.tp3_springboot.dto.RankingAventureiroDTO(a.nome, COUNT(p), SUM(p.recompensaOuro), COUNT(CASE WHEN p.destaqueMvp = true THEN 1 END)) " +
            "FROM ParticipacaoMissao p JOIN p.aventureiro a " +
            "WHERE (CAST(:inicio AS timestamp) IS NULL OR p.dataRegistro >= :inicio) " +
            "AND (CAST(:fim AS timestamp) IS NULL OR p.dataRegistro <= :fim) " +
            "GROUP BY a.id, a.nome")
    Page<RankingAventureiroDTO> obterRanking(@Param("inicio") OffsetDateTime inicio, @Param("fim") OffsetDateTime fim, Pageable pageable);
}