package infnet.tp3_springboot.controller;

import infnet.tp3_springboot.domain.loja.ProdutoLoja;
import infnet.tp3_springboot.service.ProdutoLojaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/produtos")
public class ProdutoLojaController {
    private final ProdutoLojaService service;

    public ProdutoLojaController(ProdutoLojaService service) {
        this.service = service;
    }

    @GetMapping("/busca/nome")
    public List<ProdutoLoja> buscarPorNome(@RequestParam String termo) {
        return service.buscarPorNome(termo);
    }

    @GetMapping("/busca/descricao")
    public List<ProdutoLoja> buscarPorDescricao(@RequestParam String termo) {
        return service.buscarPorDescricao(termo);
    }

    @GetMapping("/busca/frase")
    public List<ProdutoLoja> buscarPorFrase(@RequestParam String termo) {
        return service.buscarPorFraseExata(termo);
    }

    @GetMapping("/busca/fuzzy")
    public List<ProdutoLoja> buscarFuzzy(@RequestParam String termo) {
        return service.buscarFuzzy(termo);
    }

    @GetMapping("/busca/multicampos")
    public List<ProdutoLoja> buscarMultiCampos(@RequestParam String termo) {
        return service.buscarMultiCampos(termo);
    }

    @GetMapping("/busca/com-filtro")
    public List<ProdutoLoja> buscarComFiltro(@RequestParam String termo, @RequestParam String categoria) {
        return service.buscarTextoComFiltro(termo, categoria);
    }

    @GetMapping("/busca/faixa-preco")
    public List<ProdutoLoja> buscarFaixaPreco(@RequestParam Double min, @RequestParam Double max) {
        return service.buscarPorFaixaPreco(min, max);
    }

    @GetMapping("/busca/avancada")
    public List<ProdutoLoja> buscarAvancada(
            @RequestParam String categoria,
            @RequestParam String raridade,
            @RequestParam Double min,
            @RequestParam Double max) {
        return service.buscarAvancada(categoria, raridade, min, max);
    }

    @GetMapping("/agregacoes/por-categoria")
    public Map<String, Long> agruparPorCategoria() {
        return service.agruparPorCategoria();
    }

    @GetMapping("/agregacoes/por-raridade")
    public Map<String, Long> agruparPorRaridade() {
        return service.agruparPorRaridade();
    }

    @GetMapping("/agregacoes/preco-medio")
    public Double obterPrecoMedio() {
        return service.obterPrecoMedio();
    }

    @GetMapping("/agregacoes/faixas-preco")
    public Map<String, Long> agruparPorFaixaPreco() {
        return service.agruparPorFaixaPreco();
    }
}