package egresso.demo.entity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import egresso.demo.entity.Egresso;

public interface EgressoRepo extends JpaRepository<Egresso, Long> {

    Egresso findByEmail(String email);
    List<Egresso> findByNomeContaining(String nome);  
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    void deleteByCpf(String cpf);
    long countByNome(String nome);

    @Query("select u from Egresso u where u.nome=:nomeEgresso")
        List<Egresso> getAllEgressosByNome(
            @Param("nomeEgresso") String nomeEgresso);

    @Query("select u from Egresso u")
        List<Egresso> getAllEgressos();

    // @Query("SELECT COUNT(*) FROM Egresso e"
    //     + " JOIN e.cursoEgresso ce"
    //     + " JOIN ce.curso c"
    //     + " WHERE c = :curso")
    //     Long qntEgressoByCurso( @Param("curso") Curso curso);

    
}
