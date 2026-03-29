package infnet.tp2_springboot.dto;

import infnet.tp2_springboot.enums.NivelPerigo;
import infnet.tp2_springboot.enums.StatusMissao;
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