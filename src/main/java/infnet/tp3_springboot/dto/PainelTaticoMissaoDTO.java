package infnet.tp3_springboot.dto;

import infnet.tp3_springboot.domain.operacoes.PainelTaticoMissao;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PainelTaticoMissaoDTO {
    private Long id;
    private String titulo;
    private String status;
    private String nivelPerigo;
    private Integer totalParticipantes;
    private Double nivelMedioEquipe;
    private Double totalRecompensa;
    private Integer totalMvps;
    private Integer participantesComCompanheiro;
    private LocalDateTime ultimaAtualizacao;
    private Double indiceProntidao;

    public PainelTaticoMissaoDTO(PainelTaticoMissao missao) {
        this.id = missao.getId();
        this.titulo = missao.getTitulo();
        this.status = missao.getStatus();
        this.nivelPerigo = missao.getNivelPerigo();
        this.totalParticipantes = missao.getTotalParticipantes();
        this.nivelMedioEquipe = missao.getNivelMedioEquipe();
        this.totalRecompensa = missao.getTotalRecompensa();
        this.totalMvps = missao.getTotalMvps();
        this.participantesComCompanheiro = missao.getParticipantesComCompanheiro();
        this.ultimaAtualizacao = missao.getUltimaAtualizacao();
        this.indiceProntidao = missao.getIndiceProntidao();
    }
}