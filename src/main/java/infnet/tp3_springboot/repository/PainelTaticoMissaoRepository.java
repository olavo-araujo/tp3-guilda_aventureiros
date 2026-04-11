package infnet.tp3_springboot.repository;

import infnet.tp3_springboot.domain.operacoes.PainelTaticoMissao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface PainelTaticoMissaoRepository extends JpaRepository<PainelTaticoMissao, Long> {
    List<PainelTaticoMissao> findTop10ByUltimaAtualizacaoAfterOrderByIndiceProntidaoDesc(LocalDateTime dataLimite);
}