package egresso.demo.service.adm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import egresso.demo.entity.Usuario;
import egresso.demo.entity.repository.UsuarioRepo;
import egresso.demo.service.exceptions.RegraNegocioRunTime;

@Service
public class UsuarioService  implements UserDetailsService{

    @Autowired
    UsuarioRepo repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean efetuarLogin(String email, String senha) {
        Optional<Usuario> usr = repo.findByEmail(email);
        if (!usr.isPresent())
            throw new RegraNegocioRunTime("Erro de autenticação. Email informado não encontrado");    
        if (!usr.get().getSenha().equals(senha))
            throw new RegraNegocioRunTime("Erro de autenticação. Senha inválida");    
        return true;
    }

    public List<Usuario> buscar_adm(){
        List<Usuario> usuarios = repo.obterUsuariosAdm();
        if(usuarios.isEmpty()) throw new RegraNegocioRunTime("Erro ao buscar os usuarios administradores"); 
        return usuarios;
    }

    public Usuario salvar(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        verificarDadosUsuario(usuario);
        return repo.save(usuario);
    }

    public Usuario editar(Usuario usuario) {
        verificarId(usuario);
        return salvar(usuario);
    }

    public void remover(Long id) {
        if(id == null)  throw new RegraNegocioRunTime("curso não encontrado");
        repo.deleteById(id);
    }

    // public void remover(Usuario usuario) {        
    //     verificarId(usuario);
    //     repo.delete(usuario);
    // }

    private void verificarId(Usuario usuario) {
        if ((usuario == null) || (usuario.getId() == null))
            throw new RegraNegocioRunTime("Usuario sem id");
    }

    private void verificarDadosUsuario(Usuario usuario) {
        if (usuario == null)
            throw new RegraNegocioRunTime("Um usuário válido deve ser informado");                
        if ((usuario.getNome() == null) || (usuario.getNome().equals("")))
            throw new RegraNegocioRunTime("Nome do usuário deve ser informado");    
        if ((usuario.getEmail() == null) || (usuario.getEmail().equals("")))
            throw new RegraNegocioRunTime("Email deve ser informado");          
        boolean email_existente = repo.existsByEmail(usuario.getEmail());
        if (email_existente)
            throw new RegraNegocioRunTime("Email já foi cadastrado");               
        if ((usuario.getSenha() == null) || (usuario.getSenha().equals("")))
            throw new RegraNegocioRunTime("Usuário deve possuir senha");
    }

    // public List<InvestimentoSaldo> obterSaldos(Usuario usuario) {}
    // private void verficarId(Usuario usuario) {}

    @Override
    public UserDetails loadUserByUsername(String email)
        throws UsernameNotFoundException {
            Optional<Usuario> user = repo.findByEmail(email);

            if(!user.isPresent()){
                throw new UsernameNotFoundException(email);
            }

            Usuario usr = user.get();
            return new User(usr.getEmail(), usr.getSenha(), Collections.emptyList());
        }
    
    
    
}
