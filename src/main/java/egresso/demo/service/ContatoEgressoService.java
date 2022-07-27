package egresso.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egresso.demo.entity.ContatoEgresso;
import egresso.demo.entity.Egresso;
import egresso.demo.entity.repository.ContatoEgressoRepo;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@Service
public class ContatoEgressoService {

    @Autowired
    ContatoEgressoRepo repo;

    public ContatoEgresso salvar(ContatoEgresso contatoEgresso){
        verificarDadosContatoEgresso(contatoEgresso);
        return repo.save(contatoEgresso);
    }

    public void editarLista(List<ContatoEgresso> contatoEgressoList){
        for (ContatoEgresso contatoEgresso : contatoEgressoList) {
            verificarId(contatoEgresso);
            salvar(contatoEgresso);
        }
    }

    public ContatoEgresso editar(ContatoEgresso contatoEgresso) {
        verificarId(contatoEgresso);
        return salvar(contatoEgresso);
    }

    public void remover(ContatoEgresso contatoEgresso) {        
        verificarId(contatoEgresso);
        repo.delete(contatoEgresso);
    }

    public List<ContatoEgresso>buscar_por_Egresso(Egresso egresso) { 
        List<ContatoEgresso> contatos = repo.findByEgresso(egresso); 
        return contatos;
    }

    private void verificarId(ContatoEgresso contatoEgresso) {
        if ((contatoEgresso == null) || (contatoEgresso.getId() == null))
            throw new RegraNegocioRunTime("Contato egresso sem id");
    }

    private void verificarDadosContatoEgresso(ContatoEgresso contatoEgresso) {
        if (contatoEgresso == null)
            throw new RegraNegocioRunTime("Um contato válido deve ser informado");                
        if((contatoEgresso.getContato() == null) || (contatoEgresso.getContato().equals("")))
            throw new RegraNegocioRunTime("Um contato válido deve ser informado");  
        if((contatoEgresso.getEgresso() == null) || (contatoEgresso.getEgresso().equals("")))
            throw new RegraNegocioRunTime("id do Egresso inválido no contato");
    }
    
}
