package infnet.tp2_springboot.service;

import infnet.tp2_springboot.domain.aventura.Missao;
import infnet.tp2_springboot.domain.aventura.ParticipacaoMissao;
import infnet.tp2_springboot.dto.MissaoDetalheDTO;
import infnet.tp2_springboot.repository.MissaoRepository;
import infnet.tp2_springboot.repository.ParticipacaoMissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}