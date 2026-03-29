package infnet.tp2_springboot.dto;

import infnet.tp2_springboot.enums.NivelPerigo;
import infnet.tp2_springboot.enums.StatusMissao;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
public class MissaoDetalheDTO {
    private Long id;
    private String titulo;
    private NivelPerigo nivelPerigo;
    private StatusMissao status;
    private List<ParticipanteDTO> participantes;

    @Data
    @AllArgsConstructor
    public static class ParticipanteDTO {
        private String nomeAventureiro;
        private String papel;
    }
}