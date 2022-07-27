package egresso.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egresso.demo.entity.CursoEgresso;
import egresso.demo.entity.CursoEgressoId;
import egresso.demo.entity.Egresso;
import egresso.demo.entity.repository.CursoEgressoRepo;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@Service
public class CursoEgressoService {

    @Autowired
    CursoEgressoRepo repo;

    public CursoEgresso salvar(CursoEgresso cursoEgresso) {
        verificarDadosCursoEgresso(cursoEgresso);
        return repo.save(cursoEgresso);
    }

    public CursoEgresso editar(CursoEgresso cursoEgresso) {
        verificarId(cursoEgresso);
        return salvar(cursoEgresso);
    }

    public void remover(CursoEgresso cursoEgresso) {        
        verificarId(cursoEgresso);
        repo.delete(cursoEgresso);
    }

    public List<CursoEgresso>buscar_por_Egresso(Egresso egresso) { 
        List<CursoEgresso> cursos = repo.findByEgresso(egresso); 
        return cursos;
    }

    public CursoEgresso buscar_por_id(CursoEgressoId id) { 
        Optional<CursoEgresso> curso_egresso = repo.findById(id); 
        return curso_egresso.get();
    }


    private void verificarId(CursoEgresso cursoEgresso) {
        if ((cursoEgresso == null) || (cursoEgresso.getId() == null))
            throw new RegraNegocioRunTime("curso egresso sem id");
    }

    private void verificarDadosCursoEgresso(CursoEgresso cursoEgresso) {
        if (cursoEgresso == null)
            throw new RegraNegocioRunTime("Um curso válido deve ser informado");                
        if((cursoEgresso.getEgresso() == null) || (cursoEgresso.getEgresso().equals("")))
            throw new RegraNegocioRunTime("Egresso não registrado");  
        if((cursoEgresso.getDataInicio() == null) || (cursoEgresso.getDataInicio().equals("")))
            throw new RegraNegocioRunTime("Data de início do curso deve ser informado");
        if((cursoEgresso.getDataConclusao() == null) || (cursoEgresso.getDataConclusao().equals("")))
            throw new RegraNegocioRunTime("Data de conclusão do curso deve ser informado");
    }
    
}
