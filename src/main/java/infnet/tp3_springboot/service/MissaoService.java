package infnet.tp3_springboot.service;

import infnet.tp3_springboot.domain.aventura.Missao;
import infnet.tp3_springboot.domain.aventura.ParticipacaoMissao;
import infnet.tp3_springboot.dto.MissaoDetalheDTO;
import infnet.tp3_springboot.dto.MissaoResumoDTO;
import infnet.tp3_springboot.dto.RelatorioMissaoDTO;
import infnet.tp3_springboot.enums.NivelPerigo;
import infnet.tp3_springboot.enums.StatusMissao;
import infnet.tp3_springboot.repository.MissaoRepository;
import infnet.tp3_springboot.repository.ParticipacaoMissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MissaoService {
    @Autowired
    private MissaoRepository missaoRepository;

    @Autowired
    private ParticipacaoMissaoRepository participacaoMissaoRepository;

    public MissaoDetalheDTO obterDetalhes(Long id) {
        Missao missao = missaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Missão não encontrada!"));

        MissaoDetalheDTO dto = new MissaoDetalheDTO();
        dto.setId(missao.getId());
        dto.setTitulo(missao.getTitulo());
        dto.setNivelPerigo(missao.getNivelPerigo());
        dto.setStatus(missao.getStatus());

        List<ParticipacaoMissao> participacoes = participacaoMissaoRepository.findByMissaoId(id);

        List<MissaoDetalheDTO.ParticipanteDTO> participantesDTO = participacoes.stream()
                .map(p -> new MissaoDetalheDTO.ParticipanteDTO(p.getAventureiro().getNome(), p.getPapel().name()))
                .collect(Collectors.toList());

        dto.setParticipantes(participantesDTO);
        return dto;
    }

    public Page<MissaoResumoDTO> listarComFiltros(StatusMissao status, NivelPerigo perigo, OffsetDateTime inicio, OffsetDateTime fim, Pageable pageable) {
        return missaoRepository.buscarComFiltros(status, perigo, inicio, fim, pageable);
    }

    public Page<RelatorioMissaoDTO> gerarRelatorioMetricas(OffsetDateTime inicio, OffsetDateTime fim, Pageable pageable) {
        return missaoRepository.gerarRelatorioMetricas(inicio, fim, pageable);
    }
}