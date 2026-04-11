package infnet.tp3_springboot;

import infnet.tp3_springboot.domain.audit.Organizacao;
import infnet.tp3_springboot.domain.aventura.Aventureiro;
import infnet.tp3_springboot.domain.aventura.Missao;
import infnet.tp3_springboot.domain.aventura.ParticipacaoMissao;
import infnet.tp3_springboot.dto.MissaoDetalheDTO;
import infnet.tp3_springboot.dto.MissaoResumoDTO;
import infnet.tp3_springboot.dto.RelatorioMissaoDTO;
import infnet.tp3_springboot.dto.RankingAventureiroDTO;
import infnet.tp3_springboot.enums.NivelPerigo;
import infnet.tp3_springboot.enums.PapelMissao;
import infnet.tp3_springboot.enums.StatusMissao;
import infnet.tp3_springboot.repository.*;
import infnet.tp3_springboot.service.MissaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
public class MissaoBuscaTest {
    @Autowired
    private MissaoService missaoService;
    @Autowired
    private MissaoRepository missaoRepository;
    @Autowired
    private AventureiroRepository aventureiroRepository;
    @Autowired
    private ParticipacaoMissaoRepository participacaoRepository;
    @Autowired
    private OrganizacaoRepository orgRepo;
    @Autowired
    private UsuarioRepository userRepo;

    @Test
    public void testBuscaMissaoERelatorios() {
        Organizacao org = orgRepo.findAll().get(0);
        OffsetDateTime agora = OffsetDateTime.now();

        Missao m = new Missao();
        m.setTitulo("Passar direto");
        m.setNivelPerigo(NivelPerigo.ALTO);
        m.setStatus(StatusMissao.PLANEJADA);
        m.setOrganizacao(org);
        missaoRepository.save(m);

        Page<MissaoResumoDTO> resumo = missaoRepository.buscarComFiltros(
                StatusMissao.PLANEJADA,
                NivelPerigo.ALTO,
                agora.minusDays(1),
                agora.plusDays(1),
                PageRequest.of(0, 10)
        );
        assertFalse(resumo.isEmpty());

        Aventureiro a = new Aventureiro();
        a.setNome("Olavo");
        a.setClasse("Arquiteto");
        a.setNivel(33);
        a.setAtivo(true);
        a.setOrganizacao(org);
        a.setUsuarioResponsavel(userRepo.findAll().get(0));
        aventureiroRepository.save(a);

        ParticipacaoMissao p = new ParticipacaoMissao();
        p.setMissao(m);
        p.setAventureiro(a);
        p.setPapel(PapelMissao.LIDER);
        p.setRecompensaOuro(500);
        p.setDestaqueMvp(true);
        participacaoRepository.save(p);

        MissaoDetalheDTO detalhe = missaoService.obterDetalhes(m.getId());
        assertEquals("Passar direto", detalhe.getTitulo());
        assertEquals(1, detalhe.getParticipantes().size());

        Page<RelatorioMissaoDTO> relatorio = missaoRepository.gerarRelatorioMetricas(
                agora.minusDays(7), agora.plusDays(7), PageRequest.of(0, 10));
        assertFalse(relatorio.isEmpty());
        assertEquals(1L, relatorio.getContent().get(0).getQtdParticipantes());

        Page<RankingAventureiroDTO> ranking = participacaoRepository.obterRanking(
                agora.minusDays(7), agora.plusDays(7), PageRequest.of(0, 10));
        assertFalse(ranking.isEmpty());
        assertEquals("Olavo", ranking.getContent().get(0).getNome());
        assertEquals(1L, ranking.getContent().get(0).getTotalMvps());
    }
}