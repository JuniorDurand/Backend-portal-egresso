package egresso.demo.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import egresso.demo.entity.Contato;

public interface ContatoRepo extends JpaRepository<Contato, Long> {

    List<Contato> findByNomeContaining(String nome); 
    void deleteByNome(String nome);

    @Query("select c from Contato c ")
        List<Contato> obterContatos();

}
