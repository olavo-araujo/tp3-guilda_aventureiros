package infnet.tp2_springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AventureiroResumoDTO {
    private Long id;
    private String nome;
    private String classe;
    private Integer nivel;
    private Boolean ativo;
}