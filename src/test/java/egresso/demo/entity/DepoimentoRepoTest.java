package egresso.demo.entity;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import egresso.demo.entity.Depoimento;
import egresso.demo.entity.Egresso;
import egresso.demo.entity.repository.DepoimentoRepo;
import egresso.demo.entity.repository.EgressoRepo;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DepoimentoRepoTest {

    @Autowired
    DepoimentoRepo repo;

    @Autowired
    EgressoRepo repoEgresso;

    private Depoimento createCenario(){
        Egresso novo = Egresso.builder().nome("DepoimentoRepoTest: salvar")
            .email("teste@teste.com")
            .cpf("8888888").build();

        Egresso egresso = repoEgresso.save(novo);

        Depoimento depoimento = Depoimento.builder()
            .texto("texto_um")
            .data(LocalDate.of(2020, 1, 8))
            .egresso(egresso)
            .build();
  
        return depoimento;
    }

    @Test
    public void deveVerificarSalvarDepoimento() {
        //cenário
        Depoimento novo = createCenario();

        //ação
        Depoimento salvo = repo.save(novo);

        //verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(novo.getData(), salvo.getData());
        Assertions.assertEquals(novo.getTexto(), salvo.getTexto());
    }

    @Test
    public void deveVerificarFindByEgresso() {
        //cenário
        List<Egresso> lista = repoEgresso.findByNomeContaining("DepoimentoRepoTest: salvar");
        Egresso egresso = lista.get(0);
        
        //ação
        List<Depoimento> salvos = repo.findByEgresso(egresso);
        Depoimento salvo = salvos.get(0);

        //verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals("texto_um", salvo.getTexto());
    }


    
}
