package egresso.demo.entity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import egresso.demo.entity.Usuario;

public interface UsuarioRepo extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByEmailAndNome(String email, String nome);
    Optional<Usuario> findByNomeContaining(String nome);  
    boolean existsByEmail(String email);
    void deleteByNome(String nome);
    long countByNome(String nome);

    @Query("select u from Usuario u where u.nome=:nomeUsuario")
        Usuario obterUsuarioPorNome(
            @Param("nomeUsuario") String nomeUsuario);
    
    @Query("select u from Usuario u where u.adm=1")
        List<Usuario> obterUsuariosAdm();
    
}
