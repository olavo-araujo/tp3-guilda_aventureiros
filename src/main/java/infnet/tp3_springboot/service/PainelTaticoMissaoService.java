package infnet.tp3_springboot.service;

import infnet.tp3_springboot.domain.operacoes.PainelTaticoMissao;
import infnet.tp3_springboot.repository.PainelTaticoMissaoRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PainelTaticoMissaoService {

    private final PainelTaticoMissaoRepository repository;

    public PainelTaticoMissaoService(PainelTaticoMissaoRepository repository) {
        this.repository = repository;
    }

    @Cacheable(value = "rankingTatico")
    public List<PainelTaticoMissao> obterTop10MissoesRecentes() {
        LocalDateTime dataCorte = LocalDateTime.now().minusDays(15);
        return repository.findTop10ByUltimaAtualizacaoAfterOrderByIndiceProntidaoDesc(dataCorte);
    }

    @CacheEvict(value = "rankingTatico", allEntries = true)
    @Scheduled(fixedRate = 60000)
    public void atualizarCacheRanking() {}
}