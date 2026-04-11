package infnet.tp3_springboot.dto;

import infnet.tp3_springboot.enums.NivelPerigo;
import infnet.tp3_springboot.enums.StatusMissao;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RelatorioMissaoDTO {
    private String titulo;
    private StatusMissao status;
    private NivelPerigo nivelPerigo;
    private Long qtdParticipantes;
    private Long totalRecompensas;
}