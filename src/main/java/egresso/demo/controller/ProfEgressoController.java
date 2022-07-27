package egresso.demo.controller;

import java.time.LocalDate;
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

import egresso.demo.entity.Cargo;
import egresso.demo.entity.Egresso;
import egresso.demo.entity.FaixaSalario;
import egresso.demo.entity.ProfEgresso;
import egresso.demo.service.EgressoService;
import egresso.demo.service.ProfEgressoService;
import egresso.demo.service.adm.CargoService;
import egresso.demo.service.adm.FaixaSalarioService;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@RestController
@RequestMapping("/api/profissoes")
public class ProfEgressoController {

    @Autowired
    ProfEgressoService service;

    @Autowired
    EgressoService serviceEgresso;

    @Autowired
    CargoService serviceCargo;

    @Autowired
    FaixaSalarioService serviceFaixaSalario;


    @GetMapping("/listar")
    public ResponseEntity listar(@RequestParam("id_egresso") Long id_egresso) {
        try {
            Egresso egresso = serviceEgresso.buscar_por_id(id_egresso);
            List<ProfEgresso> profissoes = service.buscar_por_Egresso(egresso);
            
            return ResponseEntity.ok(profissoes);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar(@RequestParam("id_egresso") Long id_egresso,
                                        @RequestParam("empresa") String empresa,
                                        @RequestParam("descricao") String descricao,
                                        @RequestParam("cargoId") Long cargoId,
                                        @RequestParam("salarioId") Long salarioId) {
        
        try {
            Egresso egresso = serviceEgresso.buscar_por_id(id_egresso);
            Cargo cargo = serviceCargo.buscarPorId(cargoId);
            FaixaSalario faixaSalario = serviceFaixaSalario.buscarPorId(salarioId);

            ProfEgresso prof_egr =  ProfEgresso.builder()
                                .empresa(empresa)
                                .descricao(descricao)
                                .dataRegistro(LocalDate.now())
                                .egresso(egresso)
                                .cargo(cargo)
                                .faixaSalario(faixaSalario)
                                .build();

            ProfEgresso salvo = service.salvar(prof_egr);
            return ResponseEntity.ok(salvo);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/editar")
    public ResponseEntity editar(@RequestParam("id") Long id,
                                    @RequestParam("id_egresso") Long id_egresso,
                                    @RequestParam("empresa") String empresa,
                                    @RequestParam("descricao") String descricao,
                                    @RequestParam("cargoId") Long cargoId,
                                    @RequestParam("salarioId") Long salarioId) {
        try {
            Egresso egresso = serviceEgresso.buscar_por_id(id_egresso);
            Cargo cargo = serviceCargo.buscarPorId(cargoId);
            FaixaSalario faixaSalario = serviceFaixaSalario.buscarPorId(salarioId);

            ProfEgresso prof_egr =  ProfEgresso.builder()
                                .id(id)
                                .empresa(empresa)
                                .descricao(descricao)
                                .dataRegistro(LocalDate.now())
                                .egresso(egresso)
                                .cargo(cargo)
                                .faixaSalario(faixaSalario)
                                .build();

            ProfEgresso salvo = service.editar(prof_egr);
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
