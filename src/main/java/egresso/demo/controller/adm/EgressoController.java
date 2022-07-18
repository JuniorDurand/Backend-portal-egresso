package egresso.demo.controller.adm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import egresso.demo.controller.dto.CadastroDTO;
import egresso.demo.controller.dto.ContatoEgressoDTO;
import egresso.demo.controller.dto.CursoDTO;
import egresso.demo.controller.dto.CursoEgressoDTO;
import egresso.demo.controller.dto.EgressoDTO;
import egresso.demo.entity.Cargo;
import egresso.demo.entity.Contato;
import egresso.demo.entity.ContatoEgresso;
import egresso.demo.entity.ContatoEgressoId;
import egresso.demo.entity.Curso;
import egresso.demo.entity.CursoEgresso;
import egresso.demo.entity.CursoEgressoId;
import egresso.demo.entity.Egresso;
import egresso.demo.entity.FaixaSalario;
import egresso.demo.entity.ProfEgresso;
import egresso.demo.entity.Usuario;
import egresso.demo.service.EgressoService;
import egresso.demo.service.adm.CargoService;
import egresso.demo.service.adm.ContatoService;
import egresso.demo.service.adm.CursoService;
import egresso.demo.service.adm.FaixaSalarioService;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@RestController
@RequestMapping("/api/egressos")
public class EgressoController {

    @Autowired
    EgressoService service;

    @Autowired
    CursoService serviceCurso;
    @Autowired
    ContatoService serviceContato;
    @Autowired
    CargoService serviceCargo;
    @Autowired
    FaixaSalarioService serviceFaixaSalario;

    @GetMapping("/listar")
    public ResponseEntity listar() {
        try {
            List<Egresso> egressos = service.listar();
            return ResponseEntity.ok(egressos);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping(path = "/cadastrarEgresso")
    public ResponseEntity cadastrarEgresso(@RequestBody CadastroDTO dados) {
        try {
            
            Curso curso = serviceCurso.buscarPorId(dados.getCursoEgresso().getId_curso());
            Cargo cargo = serviceCargo.buscarPorId(dados.getProfissao().getId_cargo());
            FaixaSalario faixaSalario = serviceFaixaSalario.buscarPorId(dados.getProfissao().getId_faixa_salario());
           
            Egresso egr = Egresso.builder()
                                .nome(dados.getEgresso().getNome())
                                .email(dados.getEgresso().getEmail())
                                .cpf(dados.getEgresso().getCpf())
                                .resumo(dados.getEgresso().getResumo())
                                .url_foto(dados.getEgresso().getUrl_foto())
                                .build();
            CursoEgresso curso_egr = CursoEgresso.builder()
                                .id(new CursoEgressoId(egr.getId(), curso.getId()))
                                .curso(curso)
                                .egresso(egr)
                                .dataInicio(dados.getCursoEgresso().getData_inicio())
                                .dataConclusao(dados.getCursoEgresso().getData_conclusao())
                                .build();
            ProfEgresso prof_egr =  ProfEgresso.builder()
                                .empresa(dados.getProfissao().getEmpresa())
                                .descricao(dados.getProfissao().getDescricao())
                                .dataRegistro(LocalDate.now())
                                .egresso(egr)
                                .cargo(cargo)
                                .faixaSalario(faixaSalario)
                                .build();

            List<ContatoEgresso> cursoEgressoList = new ArrayList();
            for (ContatoEgressoDTO contatoEgresso : dados.getContatoEgressoList()) {
                Contato contato = serviceContato.buscarPorId(contatoEgresso.getContato_id());
                ContatoEgresso contato_egr = ContatoEgresso.builder()
                                .id(new ContatoEgressoId(egr.getId(), contato.getId()))
                                .egresso(egr)
                                .contato(contato)
                                .descricao(contatoEgresso.getDescricao())
                                .build();
                cursoEgressoList.add(contato_egr);
            }

            Usuario usr =  Usuario.builder()
                                .nome(dados.getUsuario().getNome())
                                .email(dados.getUsuario().getEmail())
                                .senha(dados.getUsuario().getSenha())
                                .adm(0)
                                .build();
            
            Egresso egress = service.cadastrarEgresso(egr,curso_egr,prof_egr,cursoEgressoList,usr);
            return ResponseEntity.ok(egress);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/dados_egresso")
    public ResponseEntity busca_dados_pagina_egresso(@RequestParam("id") Long id) {
        try {
            Egresso egresso = service.busca_dados_pagina_egresso(id);
            return ResponseEntity.ok(egresso);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
