package infnet.tp2_springboot;

import infnet.tp2_springboot.domain.audit.Usuario;
import infnet.tp2_springboot.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class MapeamentoLegadoTest {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testCarregamentoRelacionamentosLegados() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        assertFalse(usuarios.isEmpty());

        Usuario usuario = usuarios.get(0);

        assertNotNull(usuario.getOrganizacao());

        assertNotNull(usuario.getRoles());
        assertFalse(usuario.getRoles().isEmpty());

        assertNotNull(usuario.getRoles().get(0).getPermissions());
        assertFalse(usuario.getRoles().get(0).getPermissions().isEmpty());
    }
}