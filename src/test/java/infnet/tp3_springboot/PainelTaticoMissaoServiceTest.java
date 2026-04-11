package infnet.tp3_springboot;

import infnet.tp3_springboot.domain.operacoes.PainelTaticoMissao;
import infnet.tp3_springboot.service.PainelTaticoMissaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PainelTaticoMissaoServiceTest {

    @Autowired
    private PainelTaticoMissaoService service;

    @Test
    public void testObterTop10MissoesRecentes() {
        List<PainelTaticoMissao> resultado = service.obterTop10MissoesRecentes();

        assertNotNull(resultado, "A lista de missões não poder ser nula");
        assertTrue(resultado.size() <= 10, "A consulta deve retornar no max 10 missões.");

        System.out.println("Teste executado com sucesso! Missões retornadas: " + resultado.size());
    }
}