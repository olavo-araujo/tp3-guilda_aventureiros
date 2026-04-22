package infnet.tp3_springboot.controller;

import infnet.tp3_springboot.dto.RankingAventureiroDTO;
import infnet.tp3_springboot.dto.RelatorioMissaoDTO;
import infnet.tp3_springboot.enums.StatusMissao;
import infnet.tp3_springboot.service.AventureiroService;
import infnet.tp3_springboot.service.MissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private MissaoService missaoService;

    @Autowired
    private AventureiroService aventureiroService;

    @GetMapping("/missoes-metricas")
    public ResponseEntity<Page<RelatorioMissaoDTO>> relatorioMissoes(
            @RequestParam String inicio,
            @RequestParam String fim,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        OffsetDateTime dataInicio = OffsetDateTime.parse(inicio);
        OffsetDateTime dataFim = OffsetDateTime.parse(fim);

        Page<RelatorioMissaoDTO> relatorio = missaoService.gerarRelatorioMetricas(dataInicio, dataFim, PageRequest.of(page, size));
        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/ranking-participacao")
    public ResponseEntity<Page<RankingAventureiroDTO>> rankingParticipacao(
            @RequestParam String inicio,
            @RequestParam String fim,
            @RequestParam(required = false) StatusMissao status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        OffsetDateTime dataInicio = OffsetDateTime.parse(inicio);
        OffsetDateTime dataFim = OffsetDateTime.parse(fim);

        Page<RankingAventureiroDTO> ranking = aventureiroService.gerarRanking(dataInicio, dataFim, status, PageRequest.of(page, size));
        return ResponseEntity.ok(ranking);
    }
}