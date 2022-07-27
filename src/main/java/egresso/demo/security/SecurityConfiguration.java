package egresso.demo.security;

import javax.xml.ws.http.HTTPBinding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import egresso.demo.service.adm.UsuarioService;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UsuarioService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
          .authorizeRequests()
          //   autenticação e cadastro
          .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll() 
          .antMatchers(HttpMethod.POST, "/api/login").permitAll()
          //  URL pública
          // Listas
          .antMatchers(HttpMethod.GET, "/api/cursos/listar").permitAll()
          .antMatchers(HttpMethod.GET, "/api/egressos/listar").permitAll()
          .antMatchers(HttpMethod.GET, "/api/depoimentos/recentes").permitAll()
          .antMatchers(HttpMethod.GET, "/api/cargos/listar").permitAll()
          .antMatchers(HttpMethod.GET, "/api/salarios/listar").permitAll()
          // Pagina egresso
          .antMatchers(HttpMethod.GET, "/api/egressos/dados_egresso").permitAll()
          .antMatchers(HttpMethod.GET, "/api/cursos_egresso/listar").permitAll()
          .antMatchers(HttpMethod.GET, "/api/contatos_egresso/listar").permitAll()
          .antMatchers(HttpMethod.GET, "/api/depoimentos/listar").permitAll()
          .antMatchers(HttpMethod.GET, "/api/contatos/listar").permitAll()
          .anyRequest().authenticated()
          .and()
        //   .addFilter(new AuthenticationFilter(authenticationManager()))
        //   .addFilter(new AuthorizationFilter(authenticationManager()))                
        .addFilter(new JWTAuthenticationFilter(authenticationManager()))                
        .addFilter(new JWTAuthorizationFilter(authenticationManager()))                
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) 
        throws Exception {        
        // configura o método de autenticação
        auth.userDetailsService(service)
            .passwordEncoder(passwordEncoder);    
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
    
    
}
