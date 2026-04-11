package infnet.tp3_springboot.dto;

import infnet.tp3_springboot.enums.NivelPerigo;
import infnet.tp3_springboot.enums.StatusMissao;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MissaoResumoDTO {
    private Long id;
    private String titulo;
    private NivelPerigo nivelPerigo;
    private StatusMissao status;
}