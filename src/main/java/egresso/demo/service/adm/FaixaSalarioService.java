package egresso.demo.service.adm;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egresso.demo.entity.FaixaSalario;
import egresso.demo.entity.ProfEgresso;
import egresso.demo.entity.repository.FaixaSalarioRepo;
import egresso.demo.entity.repository.ProfEgressoRepo;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@Service
public class FaixaSalarioService {

    @Autowired
    FaixaSalarioRepo repo;

    @Autowired
    ProfEgressoRepo repoProfEgresso;

    public List<FaixaSalario> listar(){
        return repo.obterSalarios();
    }

    public FaixaSalario salvar(FaixaSalario faixaSalario) {
        verificarDadosFaixaSalario(faixaSalario);
        return repo.save(faixaSalario);
    }

    public FaixaSalario editar(FaixaSalario faixaSalario) {
        verificarId(faixaSalario);
        verificarProfEgresso(faixaSalario);
        return salvar(faixaSalario);
    }

    public void remover(Long id) {
        if(id == null)  throw new RegraNegocioRunTime("faixa salarioal não encontrado");
        Optional<FaixaSalario> faixa_salario = repo.findById(id);
        verificarProfEgresso(faixa_salario.get());
        repo.deleteById(id);
    }

    // public void remover(FaixaSalario faixaSalario) {        
    //     verificarId(faixaSalario);
    //     verificarProfEgresso(faixaSalario);
    //     repo.delete(faixaSalario);
    // }

    private void verificarId(FaixaSalario faixaSalario) {
        if ((faixaSalario == null) || (faixaSalario.getId() == null))
            throw new RegraNegocioRunTime("faixa Sslario sem id");
    }

    private void verificarDadosFaixaSalario(FaixaSalario faixaSalario) {
        if (faixaSalario == null)
            throw new RegraNegocioRunTime("Faixa salário não selecionado");                
        if ((faixaSalario.getDescricao() == null) || (faixaSalario.getDescricao().equals("")))
            throw new RegraNegocioRunTime("Faixa Salario deve ser informado");    
    }

    private void verificarProfEgresso(FaixaSalario faixaSalario) {
        List<ProfEgresso> res = repoProfEgresso.findByFaixaSalario(faixaSalario);
        if (!res.isEmpty())
            throw new RegraNegocioRunTime("Faixa salario informado está sendo utilizado");
    }
    
}
