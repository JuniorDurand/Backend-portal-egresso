package egresso.demo.entity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import egresso.demo.entity.Depoimento;
import egresso.demo.entity.Egresso;


public interface DepoimentoRepo extends JpaRepository<Depoimento, Long> {

    List<Depoimento> findByEgresso(Egresso egresso);
    Optional<Depoimento> findById(Long idEgresso);
    long countByEgresso(Egresso egresso);
    boolean existsByEgresso(Egresso egress);

    
}
