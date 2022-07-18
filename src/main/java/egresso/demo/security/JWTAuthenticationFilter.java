package egresso.demo.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import egresso.demo.controller.dto.UserDTO;
import egresso.demo.controller.dto.UsuarioDTO;
import egresso.demo.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        setFilterProcessesUrl("/api/login"); 
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            UsuarioDTO creds = new ObjectMapper()
                    .readValue(req.getInputStream(), UsuarioDTO.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getSenha(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // @Override
    // protected void successfulAuthentication(HttpServletRequest req,
    //                                         HttpServletResponse res,
    //                                         FilterChain chain,
    //                                         Authentication auth) throws IOException {
    //     String token = JWT.create()
    //             .withSubject(((User) auth.getPrincipal()).getUsername())
    //             .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
    //             .sign(Algorithm.HMAC512(SECRET.getBytes()));

    //     String body = ((User) auth.getPrincipal()).getUsername() + " " + token;

    //     res.getWriter().write(body);
    //     res.getWriter().flush();
    // }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, 
                                            HttpServletResponse res, 
                                            FilterChain chain,
                                            Authentication auth) 
                        throws IOException, ServletException {
        String JWT = Jwts.builder()
                        .setSubject(auth.getName())
                        .setExpiration(new Date(System.currentTimeMillis() 
                                       + SecurityConstants.EXPIRATION_TIME))
                        .signWith(SignatureAlgorithm.HS512, SecurityConstants.KEY)
                        .compact();
        res.addHeader("token", JWT);

        String body = JWT;

        res.getWriter().write(body);
        res.getWriter().flush();
    }
}