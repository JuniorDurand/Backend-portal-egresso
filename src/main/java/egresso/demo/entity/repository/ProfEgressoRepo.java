package egresso.demo.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import egresso.demo.entity.Cargo;
import egresso.demo.entity.Egresso;
import egresso.demo.entity.FaixaSalario;
import egresso.demo.entity.ProfEgresso;

public interface ProfEgressoRepo extends JpaRepository<ProfEgresso, Long> {

    List<ProfEgresso> findByEmpresaContaining(String empresa);  

    List<ProfEgresso> findByEgresso(Egresso egresso);
    List<ProfEgresso> findByCargo(Cargo cargo);
    List<ProfEgresso> findByFaixaSalario(FaixaSalario faixa_salario);
    
}
