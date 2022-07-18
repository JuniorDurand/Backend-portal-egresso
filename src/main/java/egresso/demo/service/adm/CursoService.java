package egresso.demo.service.adm;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egresso.demo.entity.Curso;
import egresso.demo.entity.CursoEgresso;
import egresso.demo.entity.repository.CursoEgressoRepo;
import egresso.demo.entity.repository.CursoRepo;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@Service
public class CursoService {

    @Autowired
    CursoRepo repo;

    @Autowired
    CursoEgressoRepo repoCursoEgresso;

    public List<Curso> listar(){
        return repo.obterCursos();
    }

    public Curso salvar(Curso curso) {
        verificarDadosCurso(curso);
        return repo.save(curso);
    }

    public Curso editar(Curso curso) {
        verificarId(curso);
        verificarCursoEgresso(curso);
        return salvar(curso);
    }

    public void remover(Long id) {      
        if(id == null)  throw new RegraNegocioRunTime("curso não encontrado");
        Optional<Curso> curso = repo.findById(id);
        verificarCursoEgresso(curso.get());
        repo.delete(curso.get());
    }

    public Curso buscarPorId(Long id) {      
        if(id == null)  throw new RegraNegocioRunTime("curso não selecionado");
        Optional<Curso> curso = repo.findById(id);
        if(curso.isEmpty()) throw new RegraNegocioRunTime("curso não encontrado");
        return curso.get();
    }

    private void verificarId(Curso curso) {
        if ((curso == null) || (curso.getId() == null))
            throw new RegraNegocioRunTime("curso não encontrado "+curso.getId());
    }

    private void verificarDadosCurso(Curso curso){
        if (curso == null)
            throw new RegraNegocioRunTime("erro ao verificar dados curso");                
        if ((curso.getNome() == null) || (curso.getNome().equals("")))
            throw new RegraNegocioRunTime("nome do curso deve ser informado");
        if ((curso.getNivel() == null) || (curso.getNivel().equals("")))
            throw new RegraNegocioRunTime("nivel do curso deve ser informado");
    }

    private void verificarCursoEgresso(Curso curso) {
        List<CursoEgresso> res = repoCursoEgresso.findByCurso(curso);
        if (!res.isEmpty())
            throw new RegraNegocioRunTime("Curso informado está sendo utilizado");
    }
    
}
