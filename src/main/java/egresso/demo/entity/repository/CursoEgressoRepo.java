package egresso.demo.entity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import egresso.demo.entity.Curso;
import egresso.demo.entity.CursoEgresso;
import egresso.demo.entity.CursoEgressoId;
import egresso.demo.entity.Egresso;

public interface CursoEgressoRepo extends JpaRepository<CursoEgresso, Long> {

    List<CursoEgresso> findByEgresso(Egresso egresso);
    List<CursoEgresso> findByCurso(Curso curso);
    Optional<CursoEgresso> findById(CursoEgressoId id);

    boolean existsByEgresso(Egresso egresso);
    boolean existsByCurso(Curso curso);

    void deleteByEgresso(Egresso egresso);

    
}
