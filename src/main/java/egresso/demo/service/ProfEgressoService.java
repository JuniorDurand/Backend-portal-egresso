package egresso.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egresso.demo.entity.Egresso;
import egresso.demo.entity.ProfEgresso;
import egresso.demo.entity.repository.ProfEgressoRepo;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@Service
public class ProfEgressoService {

    @Autowired
    ProfEgressoRepo repo;


    public ProfEgresso salvar(ProfEgresso profEgresso) {
        verificarDadosProfEgresso(profEgresso);
        return repo.save(profEgresso);
    }

    public ProfEgresso editar(ProfEgresso profEgresso) {
        verificarId(profEgresso);
        return salvar(profEgresso);
    }

    public void remover(Long id) {
        if(id == null)  throw new RegraNegocioRunTime("Profissão não encontrada");
        Boolean prof_egr = repo.existsById(id);
        if(!prof_egr)
            throw new RegraNegocioRunTime("Erro ao buscar");
        repo.deleteById(id);
    }

    public void remover(ProfEgresso profEgresso) {        
        verificarId(profEgresso);
        repo.delete(profEgresso);
    }

    public List<ProfEgresso>buscar_por_Egresso(Egresso egresso) { 
        List<ProfEgresso> profissoes = repo.findByEgresso(egresso); 
        return profissoes;
    }

    private void verificarId(ProfEgresso profEgresso) {
        if ((profEgresso == null) || (profEgresso.getId() == null))
            throw new RegraNegocioRunTime("Profissão sem id");
    }

    private void verificarDadosProfEgresso(ProfEgresso profEgresso) {
        if (profEgresso == null)
            throw new RegraNegocioRunTime("Uma profissão válida deve ser informada");                
        if ((profEgresso.getEgresso() == null) || (profEgresso.getEgresso().equals("")))
            throw new RegraNegocioRunTime("Egresso deve existir para cadastrar profissão");    
        if ((profEgresso.getCargo() == null) || (profEgresso.getCargo().equals("")))
            throw new RegraNegocioRunTime("Um Cargo deve ser selecionado"); 
        if ((profEgresso.getFaixaSalario() == null) || (profEgresso.getFaixaSalario().equals("")))
            throw new RegraNegocioRunTime("Uma Faixa Salario deve ser selecionada"); 
        if ((profEgresso.getEmpresa() == null) || (profEgresso.getEmpresa().equals("")))
            throw new RegraNegocioRunTime("Uma Empresa deve ser selecionada");
    }
    
}
