package egresso.demo.service;

import java.util.List;

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
public class ContatoEgressoServiceTest {

    @Autowired
    ContatoEgressoService service;

    @Autowired
    ContatoEgressoRepo repo;

    @Autowired
    EgressoRepo repoEgresso;
    @Autowired
    ContatoRepo repoContato;

    private Contato createCenarioContato(){
        return Contato.builder()
                        .nome("nnnnn")
                        .url_logo("nnnnn")
                        .build();
    }
    private Egresso createCenarioEgresso(){
        return Egresso.builder()
                        .nome("nnnnn")
                        .cpf("cpf")
                        .email("nnnnn@mail.com")
                        .resumo("nnnnn")
                        .build();
    }


    @Test
    public void deveVerificarSalvar() {
        // cenario
        List<Contato> contatos = repoContato.findByNomeContaining("nnnnn");
        Contato contato = contatos.get(0);
        List<Egresso> egressos = repoEgresso.findByNomeContaining("nnnnn");
        Egresso egresso = egressos.get(0);

        ContatoEgresso contatoEgresso = ContatoEgresso.builder()
                    .id(new ContatoEgressoId(egresso.getId(), contato.getId()))
                    .contato(contato)
                    .egresso(egresso)
                    .descricao("nnnnn")
                    .build();

        // ação
        ContatoEgresso salvo = service.salvar(contatoEgresso);

        // verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(contatoEgresso.getDescricao(), salvo.getDescricao());
        Assertions.assertEquals(contatoEgresso.getContato(), salvo.getContato());
        Assertions.assertEquals(contatoEgresso.getEgresso(), salvo.getEgresso());
    }
    
    @Test
    public void deveVerificarRemover() {
        // cenario
        List<ContatoEgresso> contatoEgressos = repo.findByDescricao("nnnnn");
        ContatoEgresso contatoEgresso = contatoEgressos.get(0);
        Egresso e = contatoEgresso.getEgresso();

        // ação
        service.remover(contatoEgresso);

        // verificação

    }

   
    
}
