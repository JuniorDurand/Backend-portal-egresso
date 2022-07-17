package egresso.demo.entity;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import egresso.demo.entity.Egresso;
import egresso.demo.entity.repository.EgressoRepo;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EgressoRepoTest {

    @Autowired
    EgressoRepo repo;

    @Test
    public void deveVerificarSalvarEgresso() {
        //cenário
        Egresso novo = Egresso.builder().nome("teste")
            .email("teste@teste.com")
            .cpf("8888888").build();

        //ação
        Egresso salvo = repo.save(novo);

        //verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(novo.getNome(), salvo.getNome());
        Assertions.assertEquals(novo.getEmail(), salvo.getEmail());
        Assertions.assertEquals(novo.getCpf(), salvo.getCpf());
    }

    @Test
    public void deveVerificarRemoverEgresso() {
        //cenário
        Egresso user = Egresso.builder().nome("EgressoRepoTest: remover")
            .email("teste@teste.com")
            .cpf("8888888").build();

        //ação
        Egresso salvo = repo.save(user);  //salva
        Long id = salvo.getId();
        repo.deleteById(salvo.getId());

        //verificação
        Optional<Egresso> temp = repo.findById(id);        
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
