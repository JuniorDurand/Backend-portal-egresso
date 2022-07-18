package egresso.demo.service.adm;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egresso.demo.entity.Contato;
import egresso.demo.entity.ContatoEgresso;
import egresso.demo.entity.repository.ContatoEgressoRepo;
import egresso.demo.entity.repository.ContatoRepo;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@Service
public class ContatoService {

    @Autowired
    ContatoRepo repo;

    @Autowired
    ContatoEgressoRepo repoContatoEgresso;

    public List<Contato> listar(){
        return repo.obterContatos();
    }

    public Contato salvar(Contato contato) {
        verificarDadosContato(contato);
        return repo.save(contato);
    }

    public Contato editar(Contato contato) {
        verificarId(contato);
        verificarContatoEgresso(contato);
        return salvar(contato);
    }

    public void remover(Long id) {
        if(id == null)  throw new RegraNegocioRunTime("contato não encontrado");
        Optional<Contato> contato = repo.findById(id);
        verificarContatoEgresso(contato.get());
        repo.deleteById(id);
    }

    public Contato buscarPorId(Long id) {      
        if(id == null)  throw new RegraNegocioRunTime("contato não selecionado "+id);
        Optional<Contato> contato = repo.findById(id);
        if(contato.isEmpty()) throw new RegraNegocioRunTime("contato não encontrado");
        return contato.get();
    }

    private void verificarId(Contato contato) {
        if ((contato == null) || (contato.getId() == null))
            throw new RegraNegocioRunTime("contato não encontrado");
    }

    private void verificarDadosContato(Contato contato){
        if(contato == null)
            throw new RegraNegocioRunTime("contato não inserido");
        if((contato.getNome() == null) || (contato.getNome().equals("")))
            throw new RegraNegocioRunTime("nome do contato deve ser informado");
        if((contato.getUrl_logo() == null) || (contato.getUrl_logo().equals("")))
            throw new RegraNegocioRunTime("nivel do contato deve ser informado");
    }

    private void verificarContatoEgresso(Contato contato) {
        List<ContatoEgresso> res = repoContatoEgresso.findByContato(contato);
        if (!res.isEmpty())
            throw new RegraNegocioRunTime("contato informado está sendo utilizado");
    }
    
}
