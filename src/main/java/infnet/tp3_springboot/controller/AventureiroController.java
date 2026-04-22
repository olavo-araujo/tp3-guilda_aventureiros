package infnet.tp3_springboot.controller;

import infnet.tp3_springboot.domain.aventura.Aventureiro;
import infnet.tp3_springboot.domain.aventura.Companheiro;
import infnet.tp3_springboot.dto.AventureiroDetalheDTO;
import infnet.tp3_springboot.dto.AventureiroResumoDTO;
import infnet.tp3_springboot.service.AventureiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aventureiros")
public class AventureiroController {

    @Autowired
    private AventureiroService aventureiroService;

    @PostMapping
    public ResponseEntity<Aventureiro> registrarAventureiro(@RequestBody Aventureiro aventureiro) {
        Aventureiro novoAventureiro = aventureiroService.registrar(aventureiro);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAventureiro);
    }

    @GetMapping
    public ResponseEntity<List<AventureiroResumoDTO>> listarAventureiros(
            @RequestParam(required = false) String classe,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) Integer nivelMinimo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<AventureiroResumoDTO> pagina = aventureiroService.listarComFiltros(classe, ativo, nivelMinimo, PageRequest.of(page, size));

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(pagina.getTotalElements()));
        headers.add("X-Page", String.valueOf(pagina.getNumber()));
        headers.add("X-Size", String.valueOf(pagina.getSize()));
        headers.add("X-Total-Pages", String.valueOf(pagina.getTotalPages()));

        return ResponseEntity.ok().headers(headers).body(pagina.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AventureiroDetalheDTO> consultarPorId(@PathVariable Long id) {
        AventureiroDetalheDTO dto = aventureiroService.obterPerfilCompleto(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aventureiro> atualizarAventureiro(@PathVariable Long id, @RequestBody Aventureiro dadosAtualizados) {
        Aventureiro aventureiro = aventureiroService.atualizar(id, dadosAtualizados);
        return ResponseEntity.ok(aventureiro);
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Void> inativarAventureiro(@PathVariable Long id) {
        aventureiroService.alterarStatus(id, false);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> recrutarAventureiro(@PathVariable Long id) {
        aventureiroService.alterarStatus(id, true);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/busca")
    public ResponseEntity<Page<Aventureiro>> buscarPorNome(
            @RequestParam String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Aventureiro> resultado = aventureiroService.buscarPorNome(nome, PageRequest.of(page, size));
        return ResponseEntity.ok(resultado);
    }

    @PutMapping("/{id}/companheiro")
    public ResponseEntity<Aventureiro> definirCompanheiro(@PathVariable Long id, @RequestBody Companheiro companheiro) {
        Aventureiro atualizado = aventureiroService.definirCompanheiro(id, companheiro);
        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/{id}/remover-companheiro")
    public ResponseEntity<Void> removerCompanheiro(@PathVariable Long id) {
        aventureiroService.removerCompanheiro(id);
        return ResponseEntity.noContent().build();
    }
}