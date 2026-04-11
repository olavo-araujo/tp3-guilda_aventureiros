package infnet.tp3_springboot.service;

import infnet.tp3_springboot.domain.aventura.Aventureiro;
import infnet.tp3_springboot.domain.aventura.ParticipacaoMissao;
import infnet.tp3_springboot.dto.AventureiroDetalheDTO;
import infnet.tp3_springboot.repository.AventureiroRepository;
import infnet.tp3_springboot.repository.ParticipacaoMissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}