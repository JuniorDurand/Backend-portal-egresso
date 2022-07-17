package egresso.demo.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import egresso.demo.entity.Curso;
import egresso.demo.entity.repository.CursoRepo;
import egresso.demo.service.adm.CursoService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CursoServiceTest {

    @Autowired
    CursoRepo repo;

    @Autowired
    CursoService service;

    private Curso createCenarioCurso(){
        return Curso.builder()
                        .nome("administração")
                        .nivel("nivel adm")
                        .build();
    }
    
    @Test
    public void deveVerificarSalvar() {
        // cenario
        Curso curso = createCenarioCurso();

        // ação
        Curso salvo = service.salvar(curso);

        // verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(curso.getNome(), salvo.getNome());
        Assertions.assertEquals(curso.getNivel(), salvo.getNivel());
    }
   

   
    
}
