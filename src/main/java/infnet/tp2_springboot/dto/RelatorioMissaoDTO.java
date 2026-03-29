package infnet.tp2_springboot.dto;

import infnet.tp2_springboot.enums.NivelPerigo;
import infnet.tp2_springboot.enums.StatusMissao;
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