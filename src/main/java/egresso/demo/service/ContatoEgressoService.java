package egresso.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egresso.demo.entity.ContatoEgresso;
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

    public ContatoEgresso editar(ContatoEgresso contatoEgresso) {
        verificarId(contatoEgresso);
        return salvar(contatoEgresso);
    }

    public void remover(ContatoEgresso contatoEgresso) {        
        verificarId(contatoEgresso);
        repo.delete(contatoEgresso);
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
        if((contatoEgresso.getDescricao() == null) || (contatoEgresso.getDescricao().equals("")))
            throw new RegraNegocioRunTime("Descrição do contato deve ser informado");
    }
    
}
