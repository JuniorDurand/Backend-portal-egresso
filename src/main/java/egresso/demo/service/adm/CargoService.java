package egresso.demo.service.adm;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egresso.demo.entity.Cargo;
import egresso.demo.entity.ProfEgresso;
import egresso.demo.entity.repository.CargoRepo;
import egresso.demo.entity.repository.ProfEgressoRepo;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@Service
public class CargoService {

    @Autowired
    CargoRepo repo;

    @Autowired
    ProfEgressoRepo repoProfEgresso;

    public List<Cargo> listar(){
        return repo.obterCargos();
    }

    public Cargo salvar(Cargo cargo) {
        verificarDadosCargo(cargo);
        return repo.save(cargo);
    }

    public Cargo editar(Cargo cargo) {
        verificarId(cargo);
        verificarProfEgresso(cargo);
        return salvar(cargo);
    }

    public void remover(Long id) {
        if(id == null)  throw new RegraNegocioRunTime("cargo não encontrado");
        Optional<Cargo> cargo = repo.findById(id);
        verificarProfEgresso(cargo.get());
        repo.deleteById(id);
    }

    // public void remover(Cargo cargo) {        
    //     verificarId(cargo);
    //     verificarProfEgresso(cargo);
    //     repo.delete(cargo);
    // }

    private void verificarId(Cargo cargo) {
        if ((cargo == null) || (cargo.getId() == null))
            throw new RegraNegocioRunTime("cargo sem id");
    }

    private void verificarDadosCargo(Cargo cargo) {
        if (cargo == null)
            throw new RegraNegocioRunTime("cargo não inserido");                
        if ((cargo.getNome() == null) || (cargo.getNome().equals("")))
            throw new RegraNegocioRunTime("Nome do cargo obrigatório");   
        if ((cargo.getDescricao() == null) || (cargo.getDescricao().equals("")))
            throw new RegraNegocioRunTime("Descrição do cargo obrigatório");   
    }

    private void verificarProfEgresso(Cargo cargo) {
        List<ProfEgresso> res = repoProfEgresso.findByCargo(cargo);
        if (!res.isEmpty())
            throw new RegraNegocioRunTime("cargo informado está sendo utilizado");
    }
    
}
