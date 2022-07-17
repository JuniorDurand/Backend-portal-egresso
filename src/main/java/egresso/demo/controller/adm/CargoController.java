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

import egresso.demo.entity.Cargo;
import egresso.demo.service.adm.CargoService;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@RestController
@RequestMapping("/api/cargos")
public class CargoController {

    @Autowired
    CargoService service;

    @GetMapping("/listar")
    public ResponseEntity listar() {
        try {
            List<Cargo> cargos = service.listar();
            return ResponseEntity.ok(cargos);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar(@RequestParam("nome") String nome,
                                    @RequestParam("descricao") String descricao) {
        Cargo cargo = Cargo.builder()
                                .nome(nome)
                                .descricao(descricao).build();
        try {
            Cargo cargo_salvo = service.salvar(cargo);
            return ResponseEntity.ok(cargo_salvo);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/editar")
    public ResponseEntity editar(@RequestParam("id") Long id,
                                 @RequestParam("nome") String nome,
                                 @RequestParam("descricao") String descricao) {
            
        try {
            Cargo cargo = Cargo.builder()
                                .id(id)
                                .nome(nome)
                                .descricao(descricao).build();

            Cargo salvo = service.editar(cargo);
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
