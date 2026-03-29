package infnet.tp2_springboot;

import infnet.tp2_springboot.domain.audit.Organizacao;
import infnet.tp2_springboot.domain.audit.Usuario;
import infnet.tp2_springboot.domain.aventura.Aventureiro;
import infnet.tp2_springboot.domain.aventura.Companheiro;
import infnet.tp2_springboot.domain.aventura.Missao;
import infnet.tp2_springboot.domain.aventura.ParticipacaoMissao;
import infnet.tp2_springboot.dto.AventureiroDetalheDTO;
import infnet.tp2_springboot.enums.Especie;
import infnet.tp2_springboot.enums.NivelPerigo;
import infnet.tp2_springboot.enums.PapelMissao;
import infnet.tp2_springboot.enums.StatusMissao;
import infnet.tp2_springboot.repository.AventureiroRepository;
import infnet.tp2_springboot.repository.MissaoRepository;
import infnet.tp2_springboot.repository.OrganizacaoRepository;
import infnet.tp2_springboot.repository.ParticipacaoMissaoRepository;
import infnet.tp2_springboot.repository.UsuarioRepository;
import infnet.tp2_springboot.service.AventureiroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Transactional
public class AventureiroPerfilTest {
    @Autowired
    private AventureiroService aventureiroService;

    @Autowired
    private AventureiroRepository aventureiroRepository;

    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MissaoRepository missaoRepository;

    @Autowired
    private ParticipacaoMissaoRepository participacaoMissaoRepository;

    private Long idAventureiroCompleto;
    private Long idAventureiroNovato;

    @BeforeEach
    public void setUp() {
        Organizacao org = organizacaoRepository.findAll().get(0);
        Usuario user = usuarioRepository.findAll().get(0);

        Aventureiro a1 = new Aventureiro();
        a1.setNome("Olavo");
        a1.setClasse("Guerreiro");
        a1.setNivel(14);
        a1.setAtivo(true);
        a1.setOrganizacao(org);
        a1.setUsuarioResponsavel(user);

        Companheiro c1 = new Companheiro();
        c1.setNome("Passaro");
        c1.setEspecie(Especie.BALANCA_RABO_LEITOSO);
        c1.setIndiceLealdade(100);
        c1.setAventureiro(a1);
        a1.setCompanheiro(c1);

        aventureiroRepository.save(a1);
        idAventureiroCompleto = a1.getId();

        Missao m1 = new Missao();
        m1.setTitulo("Aniquilar o AT");
        m1.setNivelPerigo(NivelPerigo.INSANO);
        m1.setStatus(StatusMissao.EM_ANDAMENTO);
        m1.setOrganizacao(org);
        missaoRepository.save(m1);

        ParticipacaoMissao p1 = new ParticipacaoMissao();
        p1.setMissao(m1);
        p1.setAventureiro(a1);
        p1.setPapel(PapelMissao.LIDER);
        p1.setDestaqueMvp(true);
        participacaoMissaoRepository.save(p1);

        Aventureiro a2 = new Aventureiro();
        a2.setNome("Kowalski");
        a2.setClasse("Mago");
        a2.setNivel(1);
        a2.setAtivo(true);
        a2.setOrganizacao(org);
        a2.setUsuarioResponsavel(user);
        aventureiroRepository.save(a2);
        idAventureiroNovato = a2.getId();
    }

    @Test
    public void testPerfilCompletoComTudo() {
        AventureiroDetalheDTO dto = aventureiroService.obterPerfilCompleto(idAventureiroCompleto);

        assertEquals("Olavo", dto.getNome());
        assertEquals("Passaro", dto.getNomeCompanheiro());
        assertEquals(Especie.BALANCA_RABO_LEITOSO, dto.getEspecieCompanheiro());
        assertEquals(1, dto.getTotalParticipacoes());
        assertEquals("Aniquilar o AT", dto.getTituloUltimaMissao());
        assertNotNull(dto.getDataUltimaMissao());
    }

    @Test
    public void testPerfilConsistenteSemMissoesECompanheiro() {
        AventureiroDetalheDTO dto = aventureiroService.obterPerfilCompleto(idAventureiroNovato);

        assertEquals("Kowalski", dto.getNome());
        assertNull(dto.getNomeCompanheiro());
        assertNull(dto.getEspecieCompanheiro());
        assertEquals(0, dto.getTotalParticipacoes());
        assertNull(dto.getTituloUltimaMissao());
        assertNull(dto.getDataUltimaMissao());
    }
}