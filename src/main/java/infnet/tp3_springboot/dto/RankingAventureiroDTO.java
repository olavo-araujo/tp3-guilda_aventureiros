package infnet.tp3_springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankingAventureiroDTO {
    private String nome;
    private Long totalParticipacoes;
    private Long somaRecompensas;
    private Long totalMvps;
}