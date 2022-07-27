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

import egresso.demo.controller.dto.ContatoEgressoDTO;
import egresso.demo.controller.dto.EgressoDTO;
import egresso.demo.entity.Contato;
import egresso.demo.entity.ContatoEgresso;
import egresso.demo.entity.ContatoEgressoId;
import egresso.demo.entity.Egresso;
import egresso.demo.service.ContatoEgressoService;
import egresso.demo.service.EgressoService;
import egresso.demo.service.adm.ContatoService;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@RestController
@RequestMapping("/api/contatos_egresso")
public class ContatoEgressoController {

    @Autowired
    ContatoEgressoService service;

    @Autowired
    EgressoService serviceEgresso;

    @Autowired
    ContatoService serviceContato;

    @GetMapping("/listar")
    public ResponseEntity listar(@RequestParam("id_egresso") Long id_egresso) {
        try {
            Egresso egresso = serviceEgresso.buscar_por_id(id_egresso);
            List<ContatoEgresso> contatos = service.buscar_por_Egresso(egresso);
            
            return ResponseEntity.ok(contatos);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar(@RequestBody List<ContatoEgresso> contatoEgressoList) {
        
        try {

            service.editarLista(contatoEgressoList);
            return ResponseEntity.ok(contatoEgressoList);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    

    

    
}
