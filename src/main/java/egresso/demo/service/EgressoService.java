package egresso.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egresso.demo.entity.ContatoEgresso;
import egresso.demo.entity.CursoEgresso;
import egresso.demo.entity.Depoimento;
import egresso.demo.entity.Egresso;
import egresso.demo.entity.ProfEgresso;
import egresso.demo.entity.Usuario;
import egresso.demo.entity.repository.ContatoEgressoRepo;
import egresso.demo.entity.repository.CursoEgressoRepo;
import egresso.demo.entity.repository.DepoimentoRepo;
import egresso.demo.entity.repository.EgressoRepo;
import egresso.demo.entity.repository.ProfEgressoRepo;
import egresso.demo.service.adm.UsuarioService;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@Service
public class EgressoService {

    @Autowired
    EgressoRepo repo;

    @Autowired
    DepoimentoRepo repoDepoimento; 

    @Autowired
    CursoEgressoRepo repoCursoEgresso;

    @Autowired
    ContatoEgressoRepo repoContatoEgresso; 

    @Autowired
    ProfEgressoRepo repoProfEgresso;

    @Autowired
    ProfEgressoService serviceProfEgresso;

    @Autowired
    CursoEgressoService serviceCursoEgresso;

    @Autowired
    ContatoEgressoService serviceContatoEgresso;

    @Autowired
    UsuarioService serviceUsuario;

    public Egresso cadastrarEgresso(Egresso egresso, CursoEgresso cursoEgresso, ProfEgresso profEgresso, List<ContatoEgresso> listContatoEgresso, Usuario usuario) {
        Egresso egresso_salvo = salvar(egresso);

        cursoEgresso.setEgresso(egresso_salvo);
        serviceCursoEgresso.salvar(cursoEgresso);

        profEgresso.setEgresso(egresso_salvo);
        serviceProfEgresso.salvar(profEgresso);

        for (ContatoEgresso contatoEgresso : listContatoEgresso) {
            contatoEgresso.setEgresso(egresso_salvo);
            serviceContatoEgresso.salvar(contatoEgresso);
        }

        serviceUsuario.salvar(usuario);
        
        return egresso_salvo;
    }

    public Egresso buscar_por_id(Long id){
        Optional<Egresso> egresso = repo.findById(id);
        if(egresso.isEmpty())
            throw new RegraNegocioRunTime("Erro ao buscar");

        return egresso.get();
    }
    
    public Egresso busca_dados_pagina_egresso(Long id){
        Optional<Egresso> egresso = repo.findById(id);
        if(egresso.isEmpty())
            throw new RegraNegocioRunTime("Erro ao buscar");

        return egresso.get();
    }

    public List<Egresso> listar(){
        List<Egresso> egressos = repo.getAllEgressos();
        if(egressos.isEmpty()) throw new RegraNegocioRunTime("Erro ao buscar os egressos");
        return egressos;
    }

    public Egresso salvar(Egresso egresso) {
        verificarDadosEgresso(egresso);
        verificarDadosEgressoNovo(egresso);
        return repo.save(egresso);
    }

    public Egresso editar(Egresso egresso) {
        verificarId(egresso);
        verificarDadosEgresso(egresso);
        return repo.save(egresso);
    }

    public void remover(Egresso egresso) {        
        verificarId(egresso);
        verificarDepoimento(egresso);
        verificarCursoEgresso(egresso);
        verificarContatoEgresso(egresso);
        verificarProfEgresso(egresso);
        repo.delete(egresso);
    }

    private void verificarId(Egresso egresso) {
        if ((egresso == null) || (egresso.getId() == null))
            throw new RegraNegocioRunTime("Egresso sem id");
    }

    private void verificarDadosEgresso(Egresso egresso) {
        if (egresso == null)
            throw new RegraNegocioRunTime("Um egresso válido deve ser informado");                
        if ((egresso.getNome() == null) || (egresso.getNome().equals("")))
            throw new RegraNegocioRunTime("Nome do egresso deve ser informado");    
        if ((egresso.getEmail() == null) || (egresso.getEmail().equals("")))
            throw new RegraNegocioRunTime("Email deve ser informado");                         
        if ((egresso.getCpf() == null) || (egresso.getCpf().equals("")))
            throw new RegraNegocioRunTime("Cpf deve ser informado");          
    }

    private void verificarDadosEgressoNovo(Egresso egresso) {
        boolean email_existente = repo.existsByEmail(egresso.getEmail());
        if (email_existente)
            throw new RegraNegocioRunTime("Email informado já existe na base");
        boolean cpf_existente = repo.existsByEmail(egresso.getCpf());
        if (cpf_existente)
                throw new RegraNegocioRunTime("Cpf informado já existe na base");
    }
    
    private void verificarDepoimento(Egresso egresso) {
        List<Depoimento> res = repoDepoimento.findByEgresso(egresso);
        if (!res.isEmpty())
            throw new RegraNegocioRunTime("Egresso informado possui depoimentos cadastrados");
    }
    private void verificarCursoEgresso(Egresso egresso) {
        List<CursoEgresso> res = repoCursoEgresso.findByEgresso(egresso);
        if (!res.isEmpty())
            throw new RegraNegocioRunTime("Egresso informado possui cursos cadastrados");
    }
    private void verificarContatoEgresso(Egresso egresso) {
        List<ContatoEgresso> res = repoContatoEgresso.findByEgresso(egresso);
        if (!res.isEmpty())
            throw new RegraNegocioRunTime("Egresso informado possui contatos cadastrados");
    }
    private void verificarProfEgresso(Egresso egresso) {
        List<ProfEgresso> res = repoProfEgresso.findByEgresso(egresso);
        if (!res.isEmpty())
            throw new RegraNegocioRunTime("Egresso informado possui profissões cadastradas");
    }
    
}
