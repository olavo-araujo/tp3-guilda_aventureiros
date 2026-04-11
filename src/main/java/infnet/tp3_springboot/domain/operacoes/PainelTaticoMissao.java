package infnet.tp3_springboot.domain.operacoes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Immutable
@Table(name = "vw_painel_tatico_missao", schema = "operacoes")
public class PainelTaticoMissao implements Serializable {
    @Id
    @Column(name = "missao_id")
    private Long id;

    private String titulo;

    private String status;

    @Column(name = "nivel_perigo")
    private String nivelPerigo;

    @Column(name = "organizacao_id")
    private Long organizacaoId;

    @Column(name = "total_participantes")
    private Integer totalParticipantes;

    @Column(name = "nivel_medio_equipe")
    private Double nivelMedioEquipe;

    @Column(name = "total_recompensa")
    private Double totalRecompensa;

    @Column(name = "total_mvps")
    private Integer totalMvps;

    @Column(name = "participantes_com_companheiro")
    private Integer participantesComCompanheiro;

    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;

    @Column(name = "indice_prontidao")
    private Double indiceProntidao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNivelPerigo() {
        return nivelPerigo;
    }

    public void setNivelPerigo(String nivelPerigo) {
        this.nivelPerigo = nivelPerigo;
    }

    public Long getOrganizacaoId() {
        return organizacaoId;
    }

    public void setOrganizacaoId(Long organizacaoId) {
        this.organizacaoId = organizacaoId;
    }

    public Integer getTotalParticipantes() {
        return totalParticipantes;
    }

    public void setTotalParticipantes(Integer totalParticipantes) {
        this.totalParticipantes = totalParticipantes;
    }

    public Double getNivelMedioEquipe() {
        return nivelMedioEquipe;
    }

    public void setNivelMedioEquipe(Double nivelMedioEquipe) {
        this.nivelMedioEquipe = nivelMedioEquipe;
    }

    public Double getTotalRecompensa() {
        return totalRecompensa;
    }

    public void setTotalRecompensa(Double totalRecompensa) {
        this.totalRecompensa = totalRecompensa;
    }

    public Integer getTotalMvps() {
        return totalMvps;
    }

    public void setTotalMvps(Integer totalMvps) {
        this.totalMvps = totalMvps;
    }

    public Integer getParticipantesComCompanheiro() {
        return participantesComCompanheiro;
    }

    public void setParticipantesComCompanheiro(Integer participantesComCompanheiro) {
        this.participantesComCompanheiro = participantesComCompanheiro;
    }

    public LocalDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public Double getIndiceProntidao() {
        return indiceProntidao;
    }

    public void setIndiceProntidao(Double indiceProntidao) {
        this.indiceProntidao = indiceProntidao;
    }
}