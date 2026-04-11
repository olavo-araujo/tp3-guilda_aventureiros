package infnet.tp3_springboot.controller;

import infnet.tp3_springboot.domain.operacoes.PainelTaticoMissao;
import infnet.tp3_springboot.service.PainelTaticoMissaoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/missoes")
public class PainelTaticoMissaoController {
    private final PainelTaticoMissaoService service;

    public PainelTaticoMissaoController(PainelTaticoMissaoService service) {
        this.service = service;
    }

    @GetMapping("/top15dias")
    public List<PainelTaticoMissao> listarTop15Dias() {
        return service.obterTop10MissoesRecentes();
    }
}