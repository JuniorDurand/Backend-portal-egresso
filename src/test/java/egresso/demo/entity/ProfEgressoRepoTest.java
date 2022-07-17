package egresso.demo.entity;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import egresso.demo.entity.Cargo;
import egresso.demo.entity.Egresso;
import egresso.demo.entity.FaixaSalario;
import egresso.demo.entity.ProfEgresso;
import egresso.demo.entity.repository.CargoRepo;
import egresso.demo.entity.repository.EgressoRepo;
import egresso.demo.entity.repository.FaixaSalarioRepo;
import egresso.demo.entity.repository.ProfEgressoRepo;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProfEgressoRepoTest {

    @Autowired
    ProfEgressoRepo repo;

    @Autowired
    EgressoRepo repoEgresso;

    @Autowired
    CargoRepo repoCargo;

    @Autowired
    FaixaSalarioRepo repoFaixaSalario;

    private ProfEgresso createCenario(){
        Egresso egresso = Egresso.builder()
                            .nome("prof egresso")
                            .email("profEgresso@teste.com")
                            .cpf("333")
                            .resumo("profEgresso")
                            .build();
        
        Cargo cargo = Cargo.builder()
                            .nome("prof egresso")
                            .descricao("Descricao profEgresso")
                            .build();
    
        FaixaSalario faixaSalario = FaixaSalario.builder()
                                      .descricao("prof egresso")
                                      .build();
    
        ProfEgresso profEgresso = ProfEgresso.builder()
                                  .empresa("prof egresso")
                                  .descricao("descricao_um")
                                  .egresso(egresso)
                                  .cargo(cargo)
                                  .faixaSalario(faixaSalario)
                                  .dataRegistro(LocalDate.of(2020, 1, 8))
                                  .build();

        return profEgresso;
    }

    @Test
    public void deveVerificarSalvarProfEgresso() {
        //cenário
        ProfEgresso profEgresso = createCenario();

        Egresso egresso = repoEgresso.save(profEgresso.getEgresso());
        profEgresso.setEgresso(egresso);
        FaixaSalario faixaSalario = repoFaixaSalario.save(profEgresso.getFaixaSalario());
        profEgresso.setFaixaSalario(faixaSalario);
        Cargo cargo = repoCargo.save(profEgresso.getCargo());
        profEgresso.setCargo(cargo);

        //ação
        ProfEgresso salvo = repo.save(profEgresso);

        //verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(profEgresso.getEgresso(), salvo.getEgresso());
        Assertions.assertEquals(profEgresso.getEmpresa(), salvo.getEmpresa());
        Assertions.assertEquals(profEgresso.getDataRegistro(), salvo.getDataRegistro());
    }

    @Test
    public void deveVerificarFindByEgresso() {
        //cenário
        List<Egresso> aux = repoEgresso.findByNomeContaining("prof egresso");
        Egresso egresso = aux.get(0);

        //ação
        List<ProfEgresso> lista = repo.findByEgresso(egresso);
        ProfEgresso profEgresso = lista.get(0);

        //verificação
        Assertions.assertNotNull(profEgresso);
        Assertions.assertEquals(profEgresso.getEgresso(), egresso);
    }

    
}
