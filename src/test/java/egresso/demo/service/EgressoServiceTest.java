package egresso.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import egresso.demo.entity.Cargo;
import egresso.demo.entity.Contato;
import egresso.demo.entity.ContatoEgresso;
import egresso.demo.entity.ContatoEgressoId;
import egresso.demo.entity.Curso;
import egresso.demo.entity.CursoEgresso;
import egresso.demo.entity.CursoEgressoId;
import egresso.demo.entity.Egresso;
import egresso.demo.entity.FaixaSalario;
import egresso.demo.entity.ProfEgresso;
import egresso.demo.entity.repository.CargoRepo;
import egresso.demo.entity.repository.ContatoRepo;
import egresso.demo.entity.repository.CursoRepo;
import egresso.demo.entity.repository.EgressoRepo;
import egresso.demo.entity.repository.FaixaSalarioRepo;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EgressoServiceTest {

    @Autowired
    EgressoService service;

    @Autowired
    EgressoRepo repo;
    @Autowired
    FaixaSalarioRepo repoFaixaSalario;
    @Autowired
    CargoRepo repoCargo;
    @Autowired
    ContatoRepo repoContato;
    @Autowired
    CursoRepo repoCurso;

    private Egresso createCenario(){
        return Egresso.builder()
            .nome("uma vez")
            .email("uma vez@email.com") // email unico
            .cpf("uma vez") // cpf unico
            .resumo("uma vez")
            .url_foto("http://seuma vezrvice")
            .build();
    }
    private Curso createCenarioCurso(){
        return Curso.builder()
            .nome("uma vez")
            .nivel("uma vez")
            .build();
    }
    private Cargo createCenarioCargo(){
        return Cargo.builder()
            .nome("uma vez")
            .descricao("uma vez service")
            .build();
    }
    private FaixaSalario createCenarioFaixaSalario(){
        return FaixaSalario.builder()
            .descricao("uma vez service")
            .build();
    }
    private Contato createCenarioContato(){
        return Contato.builder()
            .nome("uma vez")
            .url_logo("uma vez")
            .build();
    }
    private CursoEgresso createCenarioCursoEgresso(Curso curso, Egresso egresso){
        return CursoEgresso.builder()
            .id(new CursoEgressoId(egresso.getId(), curso.getId()))
            .curso(curso)
            .egresso(egresso)
            .dataConclusao(LocalDate.of(2005, 6, 12))
            .dataInicio(LocalDate.of(2002, 6, 12))
            .build();
    }
    private ProfEgresso createCenarioProfEgresso(Egresso egresso, Cargo cargo, FaixaSalario faixaSalario){
        return ProfEgresso.builder()
            .empresa("uma vez service")
            .descricao("uma vez service")
            .dataRegistro(LocalDate.of(2020, 1, 8))
            .egresso(egresso)
            .cargo(cargo)
            .faixaSalario(faixaSalario)
            .build();
    }

    private ContatoEgresso createCenarioContatoEgresso(Egresso egresso, Contato contato){
        return ContatoEgresso.builder()
            .id(new ContatoEgressoId(egresso.getId(), contato.getId()))
            .egresso(egresso)
            .contato(contato)
            .descricao("uma vez")
            .build();
    }

    @Test
    public void deveVerificarCadastro() {
        // dados do banco
        Curso curso = this.createCenarioCurso();
        FaixaSalario faixaSalario = this.createCenarioFaixaSalario();
        Cargo cargo = this.createCenarioCargo();
        Contato contato = this.createCenarioContato();
        faixaSalario = repoFaixaSalario.save(faixaSalario);
        curso = repoCurso.save(curso);
        cargo = repoCargo.save(cargo);
        contato = repoContato.save(contato);

        // dados inseridos pelo usuário
        Egresso egresso = this.createCenario();
        ProfEgresso profEgresso = this.createCenarioProfEgresso(egresso, cargo, faixaSalario);
        List<ProfEgresso> profEgressoList = new ArrayList();
        profEgressoList.add(profEgresso);

        CursoEgresso cursoEgresso = this.createCenarioCursoEgresso(curso, egresso);
        List<CursoEgresso> cursoEgressoList = new ArrayList();
        cursoEgressoList.add(cursoEgresso);

        ContatoEgresso contatoEgresso = this.createCenarioContatoEgresso(egresso, contato);
        List<ContatoEgresso> contatoEgressoList = new ArrayList();
        contatoEgressoList.add(contatoEgresso);
        
        // ação
        Egresso salvo = service.cadastrarEgresso(egresso, profEgressoList, cursoEgressoList, contatoEgressoList);

        //verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(egresso.getNome(), salvo.getNome());
        Assertions.assertEquals(egresso.getEmail(), salvo.getEmail());
        Assertions.assertEquals(egresso.getCpf(), salvo.getCpf());
    }

    @Test
    public void deveVerificarRemover() {
        // cenario
        String email = "uma vez@email.com";
        Egresso egresso = repo.findByEmail(email);
        if(egresso == null)
            throw new RegraNegocioRunTime("Erro: Email não existe");

        // ação
        Assertions.assertThrows(
            RegraNegocioRunTime.class, 
                            () -> service.remover(egresso), 
                            "egresso possui depoimento cadastrado");
    }

   
    
}
