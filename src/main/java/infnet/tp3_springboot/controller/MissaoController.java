package infnet.tp3_springboot.controller;

import infnet.tp3_springboot.dto.MissaoDetalheDTO;
import infnet.tp3_springboot.dto.MissaoResumoDTO;
import infnet.tp3_springboot.enums.NivelPerigo;
import infnet.tp3_springboot.enums.StatusMissao;
import infnet.tp3_springboot.service.MissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/missoes")
public class MissaoController {

    @Autowired
    private MissaoService missaoService;

    @GetMapping
    public ResponseEntity<List<MissaoResumoDTO>> listarMissoes(
            @RequestParam(required = false) StatusMissao status,
            @RequestParam(required = false) NivelPerigo perigo,
            @RequestParam(required = false) String inicio,
            @RequestParam(required = false) String fim,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        OffsetDateTime dataInicio = inicio != null? OffsetDateTime.parse(inicio): null;
        OffsetDateTime dataFim = fim != null? OffsetDateTime.parse(fim): null;

        Page<MissaoResumoDTO> pagina = missaoService.listarComFiltros(status, perigo, dataInicio, dataFim, PageRequest.of(page, size));

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(pagina.getTotalElements()));
        headers.add("X-Page", String.valueOf(pagina.getNumber()));
        headers.add("X-Size", String.valueOf(pagina.getSize()));
        headers.add("X-Total-Pages", String.valueOf(pagina.getTotalPages()));

        return ResponseEntity.ok().headers(headers).body(pagina.getContent());
    }

    @GetMapping("/{id}/detalhe")
    public ResponseEntity<MissaoDetalheDTO> obterDetalhes(@PathVariable Long id) {
        MissaoDetalheDTO dto = missaoService.obterDetalhes(id);
        return ResponseEntity.ok(dto);
    }
}