package egresso.demo.entity;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import egresso.demo.entity.Usuario;
import egresso.demo.entity.repository.UsuarioRepo;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UsuarioRepoTest {

    @Autowired
    UsuarioRepo repo;

    @Test
    public void deveVerificarSalvarUsuario() {
        //cenário
        Usuario user = Usuario.builder().nome("UsuarioRepoTest: salvar")
        .email("teste@teste.com")
        .senha("123").build();

        //ação
        Usuario salvo = repo.save(user);

        //verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(user.getNome(), salvo.getNome());
        Assertions.assertEquals(user.getEmail(), salvo.getEmail());
        Assertions.assertEquals(user.getSenha(), salvo.getSenha());
        Assertions.assertEquals(user.getNome(), salvo.getNome());
    }

    @Test
    public void deveVerificarRemoverUsuario() {
        //cenário
        Usuario user = Usuario.builder().nome("UsuarioRepoTest: remover")
                    .email("teste@teste.com")
                    .senha("123").build();
        //ação
        Usuario salvo = repo.save(user);  //salva
        Long id = salvo.getId();
        repo.deleteById(salvo.getId());

        //verificação
        Optional<Usuario> temp = repo.findById(id);        
        Assertions.assertFalse(temp.isPresent());
    }

    @Test
    public void deveVerificarExistsByEmail() {
        //cenário
        String email  = "teste@teste.com";

        //ação
        boolean salvo = repo.existsByEmail(email);

        //verificação
        Assertions.assertNotNull(salvo);
    }
    
}
