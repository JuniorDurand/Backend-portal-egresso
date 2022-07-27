package egresso.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import egresso.demo.controller.dto.CursoEgressoDTO;
import egresso.demo.entity.Curso;
import egresso.demo.entity.CursoEgresso;
import egresso.demo.entity.CursoEgressoId;
import egresso.demo.entity.Egresso;
import egresso.demo.service.CursoEgressoService;
import egresso.demo.service.EgressoService;
import egresso.demo.service.adm.CursoService;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@RestController
@RequestMapping("/api/cursos_egresso")
public class CursoEgressoController {

    @Autowired
    CursoEgressoService service;

    @Autowired
    EgressoService serviceEgresso;

    @Autowired
    CursoService serviceContato;

    @Autowired
    CursoService serviceCurso;

    @GetMapping("/listar")
    public ResponseEntity listar(@RequestParam("id_egresso") Long id_egresso) {
        try {
            Egresso egresso = serviceEgresso.buscar_por_id(id_egresso);
            List<CursoEgresso> cursos = service.buscar_por_Egresso(egresso);

            return ResponseEntity.ok(cursos);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar(@RequestBody CursoEgressoDTO dto) {
        
        
        try {
            Egresso egresso = serviceEgresso.buscar_por_id(dto.getId_egresso());
            Curso curso = serviceCurso.buscarPorId(dto.getId_curso());
            CursoEgresso curso_egr = CursoEgresso.builder()
                                    .id(new CursoEgressoId(egresso.getId(), curso.getId()))
                                    .curso(curso)
                                    .egresso(egresso)
                                    .dataInicio(dto.getData_inicio())
                                    .dataConclusao(dto.getData_conclusao())
                                    .build();
            CursoEgresso salvo = service.salvar(curso_egr);
            return ResponseEntity.ok(salvo);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/remover")
    public ResponseEntity remover(@RequestBody CursoEgressoId id) {

        try {
            CursoEgresso curso_egresso = service.buscar_por_id(id);
            service.remover(curso_egresso);
            return ResponseEntity.ok(true);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    

    

    

    
}
