package infnet.tp3_springboot.controller;

import infnet.tp3_springboot.dto.PainelTaticoMissaoDTO;
import infnet.tp3_springboot.service.PainelTaticoMissaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/missoes")
public class PainelTaticoMissaoController {

    private final PainelTaticoMissaoService service;

    public PainelTaticoMissaoController(PainelTaticoMissaoService service) {
        this.service = service;
    }

    @GetMapping("/top15dias")
    public ResponseEntity<List<PainelTaticoMissaoDTO>> listarTop15Dias() {
        List<PainelTaticoMissaoDTO> ranking = service.obterTop10MissoesRecentes()
                .stream()
                .map(PainelTaticoMissaoDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ranking);
    }
}