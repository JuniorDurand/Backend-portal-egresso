package egresso.demo.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import egresso.demo.entity.Contato;
import egresso.demo.entity.ContatoEgresso;
import egresso.demo.entity.ContatoEgressoId;
import egresso.demo.entity.Egresso;
import egresso.demo.entity.repository.ContatoEgressoRepo;
import egresso.demo.entity.repository.ContatoRepo;
import egresso.demo.entity.repository.EgressoRepo;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ContatoEgressoRepoTest {

    @Autowired
    ContatoEgressoRepo repo;

    @Autowired
    ContatoRepo repoContato;

    @Autowired
    EgressoRepo repoEgresso;

    private Contato createCenarioContato(){
        return Contato.builder()
                        .nome("ContatoEgressoRepoTest")
                        .url_logo("ContatoEgresso")
                        .build();
    }

    
    private Egresso createCenarioEgresso(){
        return Egresso.builder()
                        .nome("ContatoEgressoRepoTest")
                        .cpf("cpf")
                        .email("ContatoEgresso@mail.com")
                        .resumo("ContatoEgresso")
                        .build();
    }

    private ContatoEgresso createCenarioContatoEgresso(Contato c, Egresso e){
        return ContatoEgresso.builder()
                        .id(new ContatoEgressoId(e.getId(), c.getId()))
                        .contato(c)
                        .egresso(e)
                        .descricao("ContatoEgressoRepoTest")
                        .build();
    }

    @Test
    public void deveSalvarContatoEgresso() throws Exception {
        //cenario
        Contato contato = this.createCenarioContato();
        Egresso egresso = this.createCenarioEgresso();
        Contato contato_salvo = repoContato.save(contato);
        Egresso egresso_salvo = repoEgresso.save(egresso);

        ContatoEgresso contatoEgresso = this.createCenarioContatoEgresso(contato_salvo, egresso_salvo);

        //ação
        ContatoEgresso salvo = repo.save(contatoEgresso);
      
        //verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(contatoEgresso.getDescricao(), salvo.getDescricao());
        Assertions.assertEquals(contatoEgresso.getEgresso().getId(), salvo.getEgresso().getId());
        Assertions.assertEquals(contatoEgresso.getContato().getId(), salvo.getContato().getId());

    }

    
}
