package egresso.demo.controller.adm;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import egresso.demo.controller.dto.UsuarioDTO;
import egresso.demo.entity.Usuario;
import egresso.demo.service.adm.UsuarioService;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService service;



    // @PostMapping("/autenticar")
    // public ResponseEntity autenticar(@RequestParam("email") String email,
    //                                 @RequestParam("senha") String senha) {
    //     try {
    //         service.efetuarLogin(email, senha);
    //         return ResponseEntity.ok(true);
    //     } catch(RegraNegocioRunTime e) {
    //         return ResponseEntity.badRequest().body(e.getMessage());
    //     }
    // }

    @GetMapping("/listar")
    public ResponseEntity listar_administradores() {
        try {
            List<Usuario> usuarios = service.buscar_adm();
            return ResponseEntity.ok(usuarios);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cadastrarAdm")
    public ResponseEntity cadastrarAdm(@RequestParam("nome") String nome,
                                    @RequestParam("email") String email,
                                    @RequestParam("senha") String senha) {
        Usuario usuario = Usuario.builder()
                                .nome(nome)
                                .email(email)
                                .senha(senha)
                                .adm(1).build();
        try {
            Usuario usuario_salvo = service.salvar(usuario);
            return ResponseEntity.ok(usuario_salvo);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity cadastrar(@RequestBody UsuarioDTO user) {
        Usuario usuario = Usuario.builder()
                                .nome(user.getNome())
                                .email(user.getEmail())
                                .senha(user.getSenha())
                                .adm(0).build();
        try {
            Usuario usuario_salvo = service.salvar(usuario);
            return ResponseEntity.ok(usuario_salvo);
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
