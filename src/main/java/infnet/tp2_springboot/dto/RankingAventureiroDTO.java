package infnet.tp2_springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RankingAventureiroDTO {
    private String nome;
    private Long totalParticipacoes;
    private Long somaRecompensas;
    private Long totalMvps;
}