package egresso.demo.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import egresso.demo.entity.FaixaSalario;
import egresso.demo.entity.repository.FaixaSalarioRepo;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FaixaSalarioRepoTest {

    @Autowired
    FaixaSalarioRepo repo;

    @Test
    public void deveVerificarSalvarFaixaSalario() {
        //cenário
        FaixaSalario novo = FaixaSalario.builder().descricao("entre 1 e 10").build();

        //ação
        FaixaSalario salvo = repo.save(novo);

        //verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(novo.getDescricao(), salvo.getDescricao());
    }
    
}
