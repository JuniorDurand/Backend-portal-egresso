package egresso.demo.entity;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import egresso.demo.entity.Contato;
import egresso.demo.entity.repository.ContatoRepo;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ContatoRepoTest {

    @Autowired
    ContatoRepo repo;

    @Test
    public void deveVerificarSalvarContato() {
        //cenário
        Contato novo = Contato.builder().nome("instagram")
                                    .url_logo("http://").build();

        //ação
        Contato salvo = repo.save(novo);

        //verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(novo.getNome(), salvo.getNome());
        Assertions.assertEquals(novo.getUrl_logo(), salvo.getUrl_logo());
    }

    @Test
    public void deveVerificarRemoverContato() {
        //cenário
        Contato novo = Contato.builder().nome("remover")
                                    .url_logo("http://").build();

        //ação
        Contato salvo = repo.save(novo);
        repo.deleteById(salvo.getId());

        //verificação
        Optional<Contato> temp = repo.findById(salvo.getId());        
        Assertions.assertFalse(temp.isPresent());
    }

    @Test
    public void deveVerificarObterContatos() {
        //cenário
        String nome  = "instagram";

        //ação
        List<Contato> lista = repo.obterContatos();
        Contato contato = lista.get(0);

        //verificação
        Assertions.assertNotNull(contato);
        Assertions.assertEquals(contato.getNome(), nome);
    }
    
}
