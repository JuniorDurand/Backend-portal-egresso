package egresso.demo.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import egresso.demo.entity.Contato;
import egresso.demo.entity.ContatoEgresso;
import egresso.demo.entity.Egresso;

public interface ContatoEgressoRepo extends JpaRepository<ContatoEgresso, Long> {

    List<ContatoEgresso> findByEgresso(Egresso egresso);
    List<ContatoEgresso> findByContato(Contato contato);
    List<ContatoEgresso> findByDescricao(String descricao);
    void deleteByEgresso(Egresso egresso);
    
}
