package egresso.demo.entity;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import egresso.demo.entity.Curso;
import egresso.demo.entity.CursoEgresso;
import egresso.demo.entity.CursoEgressoId;
import egresso.demo.entity.Egresso;
import egresso.demo.entity.repository.CursoEgressoRepo;
import egresso.demo.entity.repository.CursoRepo;
import egresso.demo.entity.repository.EgressoRepo;



@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CursoEgressoRepoTest {

    @Autowired
    CursoEgressoRepo repo;

    @Autowired
    CursoRepo repoCurso;

    @Autowired
    EgressoRepo repoEgresso;

    private Curso createCenarioCurso(){
        return Curso.builder()
                        .nome("CursoEgressoRepoTest")
                        .nivel("CursoEgresso")
                        .build();
    }

    
    private Egresso createCenarioEgresso(){
        return Egresso.builder()
                        .nome("CursoEgressoRepoTest")
                        .cpf("cpf")
                        .email("CursoEgresso@mail.com")
                        .resumo("CursoEgresso")
                        .build();
    }

    private CursoEgresso createCenarioCursoEgresso(Curso c, Egresso e){
        return CursoEgresso.builder()
                        .id(new CursoEgressoId(e.getId(), c.getId()))
                        .curso(c)
                        .egresso(e)
                        .dataConclusao(LocalDate.of(2005, 6, 12))
                        .dataInicio(LocalDate.of(2002, 6, 12))
                        .build();
    }

    @Test
    public void deveSalvarCurso() throws Exception {
        //cenario
        Curso curso = this.createCenarioCurso();
        Egresso egresso = this.createCenarioEgresso();
        curso = repoCurso.save(curso);
        egresso = repoEgresso.save(egresso);

        CursoEgresso cursoEgresso = this.createCenarioCursoEgresso(curso, egresso);

        //ação
        CursoEgresso salvo = repo.save(cursoEgresso);
      
        //verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(cursoEgresso.getDataConclusao(), salvo.getDataConclusao());
        Assertions.assertEquals(cursoEgresso.getDataInicio(), salvo.getDataInicio());
        Assertions.assertEquals(cursoEgresso.getEgresso().getId(), salvo.getEgresso().getId());
        Assertions.assertEquals(cursoEgresso.getCurso().getId(), salvo.getCurso().getId());

    }

    
}
