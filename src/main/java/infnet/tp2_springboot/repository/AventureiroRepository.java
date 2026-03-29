package infnet.tp2_springboot.repository;

import infnet.tp2_springboot.domain.aventura.Aventureiro;
import infnet.tp2_springboot.dto.AventureiroResumoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AventureiroRepository extends JpaRepository<Aventureiro, Long> {
    Page<Aventureiro> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    @Query("SELECT new infnet.tp2_springboot.dto.AventureiroResumoDTO(a.id, a.nome, a.classe, a.nivel, a.ativo) " +
            "FROM Aventureiro a WHERE " +
            "(:ativo IS NULL OR a.ativo = :ativo) AND " +
            "(:classe IS NULL OR a.classe = :classe) AND " +
            "(:nivelMinimo IS NULL OR a.nivel >= :nivelMinimo)")

    Page<AventureiroResumoDTO> buscarComFiltros(@Param("ativo") Boolean ativo,
                                                @Param("classe") String classe,
                                                @Param("nivelMinimo") Integer nivelMinimo,
                                                Pageable pageable);
}