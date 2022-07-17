package egresso.demo.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import egresso.demo.entity.Curso;

public interface CursoRepo extends JpaRepository<Curso, Long> {

    List<Curso> findByNomeContaining(String nome); 
    void deleteByNome(String nome);
    void deleteById(Long id);

    @Query("select c from Curso c ")
        List<Curso> obterCursos();

    @Query("select c from Curso c where c.nivel=:nivelCurso")
        List<Curso> obterCursosPorNivel(
            @Param("nivelCurso") String nivelCurso);
    
}
