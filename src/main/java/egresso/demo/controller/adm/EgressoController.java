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

import egresso.demo.controller.dto.EgressoDTO;
import egresso.demo.entity.Egresso;
import egresso.demo.service.EgressoService;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@RestController
@RequestMapping("/api/egressos")
public class EgressoController {

    @Autowired
    EgressoService service;

    @GetMapping("/listar")
    public ResponseEntity listar() {
        try {
            List<Egresso> egressos = service.listar();
            return ResponseEntity.ok(egressos);
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
