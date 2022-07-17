package egresso.demo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import egresso.demo.entity.Usuario;
import egresso.demo.entity.repository.UsuarioRepo;
import egresso.demo.service.adm.UsuarioService;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    UsuarioService service;

    @Autowired
    UsuarioRepo repo;

    @Test
    public void deveVerificarLogin() {
        //cenario
        String email_existente = "teste@teste.com";
        String senha_existente = "123";

        // ação
        boolean resposta = service.efetuarLogin(email_existente, senha_existente);

        //verificação
        Assertions.assertTrue(resposta); 
    }

    @Test
    public void deveVerificarLoginSenhaErrada() {
        //cenario
        String email_existente = "teste@teste.com";
        String senha_errada = "errada";

        // ação
        Assertions.assertThrows(
            RegraNegocioRunTime.class, 
                            () -> service.efetuarLogin(email_existente, senha_errada), 
                            "erro");
    }

    @Test
    public void deveVerificarLoginEmailErrado() {
        //cenario
        String email_errado = "nao_existe";
        String senha = "senha";

        // ação
        Assertions.assertThrows(
            RegraNegocioRunTime.class, 
                            () -> service.efetuarLogin(email_errado, senha), 
                            "erro");
    }


    @Test
    public void deveVerificarCadastrarUsuario() {
        //cenario
        Usuario usuario = Usuario.builder().nome("deletar")
                                        .email("ok@teste.com")
                                        .senha("123").build();

        // ação
        Usuario salvo = service.salvar(usuario);

        //verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(usuario.getNome(), salvo.getNome());
        Assertions.assertEquals(usuario.getEmail(), salvo.getEmail());
        Assertions.assertEquals(usuario.getSenha(), salvo.getSenha());
        Assertions.assertEquals(usuario.getAdm(), salvo.getAdm());

    repo.delete(salvo);
    }

    @Test
    public void deveVerificarCadastroComEmailExistente() {
        //cenario
        Usuario usuario = Usuario.builder().nome("Teste")
                                        .email("novo@teste.com")
                                        .senha("123").build();

        // ação
        Assertions.assertThrows(
            RegraNegocioRunTime.class, 
                            () -> service.salvar(usuario), 
                            "email existente");

    }


    
}
