package infnet.tp2_springboot.dto;

import infnet.tp2_springboot.enums.Especie;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class AventureiroDetalheDTO {
    private Long id;
    private String nome;
    private String classe;
    private Integer nivel;
    private Boolean ativo;
    private String nomeCompanheiro;
    private Especie especieCompanheiro;
    private Long totalParticipacoes;
    private String tituloUltimaMissao;
    private OffsetDateTime dataUltimaMissao;
}