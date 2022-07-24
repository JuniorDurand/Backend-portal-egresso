package egresso.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import egresso.demo.entity.Depoimento;
import egresso.demo.entity.Egresso;
import egresso.demo.service.DepoimentoService;
import egresso.demo.service.EgressoService;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@RestController
@RequestMapping("/api/depoimentos")
public class DepoimentoController {

    @Autowired
    DepoimentoService service;

    @Autowired
    EgressoService serviceEgresso;

    @GetMapping("/listar")
    public ResponseEntity listar(@RequestParam("id_egresso") Long id_egresso) {
        try {
            Egresso egresso = serviceEgresso.buscar_por_id(id_egresso);
            List<Depoimento> depoimentos = service.buscar_por_egresso(egresso);
            
            return ResponseEntity.ok(depoimentos);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/recentes")
    public ResponseEntity<Object> obterOrdenadosPorMaisRecente() {
        try {
            List<Depoimento> deps = service.getDepoimentosOrderByMostRecent();
            return ResponseEntity.ok(deps);
                                
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar(@RequestParam("texto") String texto,
                                    @RequestParam("egresso_id") Long egresso_id) {
        
        Egresso egresso = serviceEgresso.buscar_por_id(egresso_id);
        Depoimento depoimento = Depoimento.builder()
                                .egresso(egresso)
                                .texto(texto)
                                .data(LocalDate.now()).build();
        try {
            Depoimento depoimento_salvo = service.salvar(depoimento);
            return ResponseEntity.ok(depoimento_salvo);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/editar")
    public ResponseEntity editar(@RequestParam("id") Long id,
                                    @RequestParam("texto") String texto) {
        try {
            Depoimento dep = service.buscarPorId(id);
            Depoimento depoimento = Depoimento.builder()
                                .id(id)
                                .egresso(dep.getEgresso())
                                .texto(texto)
                                .data(LocalDate.now()).build();

            Depoimento salvo = service.editar(depoimento);
            return ResponseEntity.ok(salvo);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/remover")
    public ResponseEntity remover(@RequestParam("id") Long id) {

        try {
            service.removerPorId(id);
            return ResponseEntity.ok(true);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    

    

    
}
