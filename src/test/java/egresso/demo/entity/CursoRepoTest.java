package egresso.demo.entity;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import egresso.demo.entity.Curso;
import egresso.demo.entity.repository.CursoRepo;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CursoRepoTest {

    @Autowired
    CursoRepo repo;

    @Test
    public void deveVerificarSalvarCurso() {
        //cenário
        Curso novo = Curso.builder().nome("Ciencia da computação")
                                    .nivel("graduacao").build();

        //ação
        Curso salvo = repo.save(novo);

        //verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(novo.getNome(), salvo.getNome());
        Assertions.assertEquals(novo.getNivel(), salvo.getNivel());
    }

    @Test
    public void deveVerificarRemoverCurso() {
        //cenário
        Curso novo = Curso.builder().nome("deletar")
                                    .nivel("graduacao").build();

        //ação
        Curso salvo = repo.save(novo);
        Long id = salvo.getId();
        repo.deleteById(salvo.getId());

        //verificação
        Optional<Curso> temp = repo.findById(id);        
        Assertions.assertFalse(temp.isPresent());
    }

    @Test
    public void deveVerificarFindByNivel() {
        //cenário
        String nivel  = "graduacao";

        //ação
        List<Curso> lista = repo.obterCursosPorNivel(nivel);
        Curso curso = lista.get(0);

        //verificação
        Assertions.assertNotNull(curso);
        Assertions.assertEquals(curso.getNivel(), nivel);
    }
    
}
