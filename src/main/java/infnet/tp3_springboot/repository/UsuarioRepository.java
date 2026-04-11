package infnet.tp3_springboot.repository;

import infnet.tp3_springboot.domain.audit.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}