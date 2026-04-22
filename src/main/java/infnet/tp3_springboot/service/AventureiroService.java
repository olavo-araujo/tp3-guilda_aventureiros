package infnet.tp3_springboot.service;

import infnet.tp3_springboot.domain.aventura.Aventureiro;
import infnet.tp3_springboot.domain.aventura.Companheiro;
import infnet.tp3_springboot.domain.aventura.ParticipacaoMissao;
import infnet.tp3_springboot.dto.AventureiroDetalheDTO;
import infnet.tp3_springboot.dto.AventureiroResumoDTO;
import infnet.tp3_springboot.dto.RankingAventureiroDTO;
import infnet.tp3_springboot.enums.StatusMissao;
import infnet.tp3_springboot.repository.AventureiroRepository;
import infnet.tp3_springboot.repository.ParticipacaoMissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class AventureiroService {
    @Autowired
    private AventureiroRepository aventureiroRepository;

    @Autowired
    private ParticipacaoMissaoRepository participacaoMissaoRepository;

    public AventureiroDetalheDTO obterPerfilCompleto(Long id) {
        Aventureiro aventureiro = aventureiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aventureiro não encontrado!"));

        AventureiroDetalheDTO dto = new AventureiroDetalheDTO();
        dto.setId(aventureiro.getId());
        dto.setNome(aventureiro.getNome());
        dto.setClasse(aventureiro.getClasse());
        dto.setNivel(aventureiro.getNivel());
        dto.setAtivo(aventureiro.getAtivo());

        if (aventureiro.getCompanheiro() != null) {
            dto.setNomeCompanheiro(aventureiro.getCompanheiro().getNome());
            dto.setEspecieCompanheiro(aventureiro.getCompanheiro().getEspecie());
        }

        long totalParticipacoes = participacaoMissaoRepository.countByAventureiroId(id);
        dto.setTotalParticipacoes(totalParticipacoes);

        if (totalParticipacoes > 0) {
            ParticipacaoMissao ultima = participacaoMissaoRepository.findFirstByAventureiroIdOrderByDataRegistroDesc(id);
            dto.setTituloUltimaMissao(ultima.getMissao().getTitulo());
            dto.setDataUltimaMissao(ultima.getDataRegistro());
        }
        return dto;
    }

    public Aventureiro registrar(Aventureiro aventureiro) {
        aventureiro.setAtivo(true);
        return aventureiroRepository.save(aventureiro);
    }

    public Page<AventureiroResumoDTO> listarComFiltros(String classe, Boolean ativo, Integer nivelMinimo, Pageable pageable) {
        return aventureiroRepository.buscarComFiltros(ativo, classe, nivelMinimo, pageable);
    }

    public Aventureiro atualizar(Long id, Aventureiro dadosAtualizados) {
        Aventureiro existente = aventureiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aventureiro não encontrado!"));

        existente.setNome(dadosAtualizados.getNome());
        existente.setClasse(dadosAtualizados.getClasse());
        existente.setNivel(dadosAtualizados.getNivel());

        return aventureiroRepository.save(existente);
    }

    public void alterarStatus(Long id, boolean status) {
        Aventureiro existente = aventureiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aventureiro não encontrado!"));

        existente.setAtivo(status);
        aventureiroRepository.save(existente);
    }

    public Page<Aventureiro> buscarPorNome(String nome, Pageable pageable) {
        return aventureiroRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Aventureiro definirCompanheiro(Long aventureiroId, Companheiro companheiro) {
        Aventureiro aventureiro = aventureiroRepository.findById(aventureiroId)
                .orElseThrow(() -> new RuntimeException("Aventureiro não encontrado!"));

        companheiro.setAventureiro(aventureiro);
        aventureiro.setCompanheiro(companheiro);
        return aventureiroRepository.save(aventureiro);
    }

    public void removerCompanheiro(Long aventureiroId) {
        Aventureiro aventureiro = aventureiroRepository.findById(aventureiroId)
                .orElseThrow(() -> new RuntimeException("Aventureiro não encontrado!"));

        if (aventureiro.getCompanheiro() != null) {
            aventureiro.getCompanheiro().setAventureiro(null);
            aventureiro.setCompanheiro(null);
            aventureiroRepository.save(aventureiro);
        }
    }

    public Page<RankingAventureiroDTO> gerarRanking(OffsetDateTime inicio, OffsetDateTime fim, StatusMissao status, Pageable pageable) {
        return aventureiroRepository.gerarRankingAventureiros(inicio, fim, status, pageable);
    }
}