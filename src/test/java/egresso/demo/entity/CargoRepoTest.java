package egresso.demo.entity;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import egresso.demo.entity.Cargo;
import egresso.demo.entity.repository.CargoRepo;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CargoRepoTest {

    @Autowired
    CargoRepo repo;

    @Test
    public void deveVerificarSalvarCargo() {
        //cenário
        Cargo novo = Cargo.builder().nome("Cargo um")
                                    .descricao("descricao do cargo").build();

        //ação
        Cargo salvo = repo.save(novo);

        //verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(novo.getNome(), salvo.getNome());
        Assertions.assertEquals(novo.getDescricao(), salvo.getDescricao());
    }

    @Test
    public void deveVerificarRemoverCurso() {
        //cenário
        Cargo novo = Cargo.builder().nome("remover um")
                                    .descricao("descricao do cargo").build();

        //ação
        Cargo salvo = repo.save(novo);
        repo.deleteById(salvo.getId());

        //verificação
        Optional<Cargo> temp = repo.findById(salvo.getId());        
        Assertions.assertFalse(temp.isPresent());
    }

    @Test
    public void deveVerificarObterCargoPorNome() {
        //cenário
        String nome  = "Cargo um";

        //ação
        List<Cargo> lista = repo.obterCargoPorNome(nome);
        Cargo cargo = lista.get(0);

        //verificação
        Assertions.assertNotNull(cargo);
        Assertions.assertEquals(cargo.getNome(), nome);
    }
    
}
