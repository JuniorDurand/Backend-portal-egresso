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

import egresso.demo.controller.dto.FaixaSalarioDTO;
import egresso.demo.entity.FaixaSalario;
import egresso.demo.service.adm.FaixaSalarioService;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@RestController
@RequestMapping("/api/salarios")
public class FaixaSalarioController {

    @Autowired
    FaixaSalarioService service;

    @GetMapping("/listar")
    public ResponseEntity listar() {
        try {
            List<FaixaSalario> salarios = service.listar();
            return ResponseEntity.ok(salarios);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar(@RequestParam("descricao") String descricao) {
        FaixaSalario faixa_salario = FaixaSalario.builder()
                                .descricao(descricao).build();
        try {
            FaixaSalario faixa_salario_salvo = service.salvar(faixa_salario);
            return ResponseEntity.ok(faixa_salario_salvo);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/editar")
    public ResponseEntity editar(@RequestParam("id") Long id,
                                    @RequestParam("descricao") String descricao) {
        try {
            FaixaSalario faixa_salario = FaixaSalario.builder()
                                .id(id)
                                .descricao(descricao).build();

            FaixaSalario salvo = service.editar(faixa_salario);
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
