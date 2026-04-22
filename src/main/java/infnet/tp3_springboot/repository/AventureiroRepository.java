package infnet.tp3_springboot.repository;

import infnet.tp3_springboot.domain.aventura.Aventureiro;
import infnet.tp3_springboot.dto.AventureiroResumoDTO;
import infnet.tp3_springboot.dto.RankingAventureiroDTO;
import infnet.tp3_springboot.enums.StatusMissao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;

public interface AventureiroRepository extends JpaRepository<Aventureiro, Long> {
    Page<Aventureiro> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    @Query("SELECT new infnet.tp3_springboot.dto.AventureiroResumoDTO(a.id, a.nome, a.classe, a.nivel, a.ativo) " +
            "FROM Aventureiro a WHERE " +
            "(:ativo IS NULL OR a.ativo = :ativo) AND " +
            "(:classe IS NULL OR a.classe = :classe) AND " +
            "(:nivelMinimo IS NULL OR a.nivel >= :nivelMinimo)")
    Page<AventureiroResumoDTO> buscarComFiltros(@Param("ativo") Boolean ativo,
                                                @Param("classe") String classe,
                                                @Param("nivelMinimo") Integer nivelMinimo,
                                                Pageable pageable);

    @Query("SELECT new infnet.tp3_springboot.dto.RankingAventureiroDTO(a.nome, COUNT(p), SUM(p.recompensaOuro), SUM(CASE WHEN p.destaque = true THEN 1L ELSE 0L END)) " +
            "FROM ParticipacaoMissao p JOIN p.aventureiro a JOIN p.missao m " +
            "WHERE (CAST(:inicio AS timestamp) IS NULL OR m.dataCriacao >= :inicio) " +
            "AND (CAST(:fim AS timestamp) IS NULL OR m.dataCriacao <= :fim) " +
            "AND (:status IS NULL OR m.status = :status) " +
            "GROUP BY a.id, a.nome " +
            "ORDER BY COUNT(p) DESC, SUM(p.recompensaOuro) DESC")
    Page<RankingAventureiroDTO> gerarRankingAventureiros(
            @Param("inicio") OffsetDateTime inicio,
            @Param("fim") OffsetDateTime fim,
            @Param("status") StatusMissao status,
            Pageable pageable);
}