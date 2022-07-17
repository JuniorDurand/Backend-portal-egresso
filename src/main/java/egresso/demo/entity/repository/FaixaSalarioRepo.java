package egresso.demo.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import egresso.demo.entity.FaixaSalario;

public interface FaixaSalarioRepo extends JpaRepository<FaixaSalario, Long> {

    List<FaixaSalario> findByDescricao(String descricao);

    @Query("select c from FaixaSalario c ")
        List<FaixaSalario> obterSalarios();
    
}
