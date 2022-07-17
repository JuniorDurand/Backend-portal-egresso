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

import egresso.demo.entity.Contato;
import egresso.demo.service.adm.ContatoService;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@RestController
@RequestMapping("/api/contatos")
public class ContatoController {

    @Autowired
    ContatoService service;

    @GetMapping("/listar")
    public ResponseEntity listar() {
        try {
            List<Contato> contatos = service.listar();
            return ResponseEntity.ok(contatos);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar(@RequestParam("nome") String nome,
                                    @RequestParam("url_logo") String url_logo) {
        Contato contato = Contato.builder()
                                .nome(nome)
                                .url_logo(url_logo).build();
        try {
            Contato contato_salvo = service.salvar(contato);
            return ResponseEntity.ok(contato_salvo);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/editar")
    public ResponseEntity editar(@RequestParam("id") Long id,
                                 @RequestParam("nome") String nome,
                                 @RequestParam("url_logo") String url_logo) {
            
        try {
            Contato contato = Contato.builder()
                                .id(id)
                                .nome(nome)
                                .url_logo(url_logo).build();

            Contato salvo = service.editar(contato);
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
