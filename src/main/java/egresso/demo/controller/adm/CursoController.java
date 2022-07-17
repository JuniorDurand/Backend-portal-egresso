package egresso.demo.controller.adm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import egresso.demo.controller.dto.CursoDTO;
import egresso.demo.entity.Curso;
import egresso.demo.service.adm.CursoService;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    CursoService service;

    @GetMapping("/listar")
    public ResponseEntity listar() {
        try {
            List<Curso> cursos = service.listar();
            return ResponseEntity.ok(cursos);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar(@RequestParam("nome") String nome,
                                    @RequestParam("nivel") String nivel) {
        Curso curso = Curso.builder()
                                .nome(nome)
                                .nivel(nivel).build();
        try {
            Curso curso_salvo = service.salvar(curso);
            return ResponseEntity.ok(curso_salvo);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/editar")
    public ResponseEntity editar(@RequestParam("id") Long id,
                                 @RequestParam("nome") String nome,
                                 @RequestParam("nivel") String nivel) {
            
        try {
            Curso curso = Curso.builder()
                                .id(id)
                                .nome(nome)
                                .nivel(nivel).build();

            Curso salvo = service.editar(curso);
            return ResponseEntity.ok(salvo);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/remover")
    public ResponseEntity remover(@RequestParam("id") Long id) {
        
        try {
            service.remover(id);
            return ResponseEntity.ok(true);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    
}
