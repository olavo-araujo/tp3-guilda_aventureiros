package infnet.tp2_springboot;

import infnet.tp2_springboot.domain.audit.Organizacao;
import infnet.tp2_springboot.domain.audit.Usuario;
import infnet.tp2_springboot.domain.aventura.Aventureiro;
import infnet.tp2_springboot.dto.AventureiroResumoDTO;
import infnet.tp2_springboot.repository.AventureiroRepository;
import infnet.tp2_springboot.repository.OrganizacaoRepository;
import infnet.tp2_springboot.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class AventureiroBuscaTest {
    @Autowired
    private AventureiroRepository aventureiroRepository;

    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
        aventureiroRepository.save(a1);

        Aventureiro a2 = new Aventureiro();
        a2.setNome("Guilherme");
        a2.setClasse("Arqueiro");
        a2.setNivel(8);
        a2.setAtivo(true);
        a2.setOrganizacao(org);
        a2.setUsuarioResponsavel(user);
        aventureiroRepository.save(a2);

        Aventureiro a3 = new Aventureiro();
        a3.setNome("Victor");
        a3.setClasse("Guerreiro");
        a3.setNivel(12);
        a3.setAtivo(false);
        a3.setOrganizacao(org);
        a3.setUsuarioResponsavel(user);
        aventureiroRepository.save(a3);
    }

    @Test
    public void testListagemComFiltros() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("nome").ascending());

        Page<AventureiroResumoDTO> resultado1 = aventureiroRepository.buscarComFiltros(true, "Guerreiro", 5, pageRequest);
        assertEquals(1, resultado1.getTotalElements());
        assertEquals("Olavo", resultado1.getContent().get(0).getNome());

        Page<AventureiroResumoDTO> resultado2 = aventureiroRepository.buscarComFiltros(null, null, 12, pageRequest);
        assertEquals(2, resultado2.getTotalElements());
    }

    @Test
    public void testBuscaPorNomeParcial() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("nivel").descending());

        Page<Aventureiro> resultado = aventureiroRepository.findByNomeContainingIgnoreCase("v", pageRequest);

        assertEquals(2, resultado.getTotalElements());
        assertEquals("Olavo", resultado.getContent().get(0).getNome());
        assertEquals("Victor", resultado.getContent().get(1).getNome());
    }
}