package egresso.demo.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import egresso.demo.controller.adm.UsuarioController;
import egresso.demo.controller.dto.UsuarioDTO;
import egresso.demo.service.adm.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest (controllers =  UsuarioController.class)
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    static final String API = "/api/usuarios";
    @Autowired
    MockMvc mvc;
    @MockBean
    UsuarioService service;

    @Test
    public void deveAutenticarUsuario() throws Exception{
        //cenário
        UsuarioDTO dto = UsuarioDTO.builder()
            .email("teste@teste.com")
            .senha("122").build();
        Mockito.when(
            service.efetuarLogin(
                Mockito.anyString(), Mockito.anyString()))
                .thenReturn(true);
        String json = new ObjectMapper().writeValueAsString(dto);

        //ação
        MockHttpServletRequestBuilder request = 
                MockMvcRequestBuilders.post(API.concat("/autenticar"))
                .accept (MediaType.APPLICATION_JSON)
                .contentType (MediaType.APPLICATION_JSON)
                .content (json);

        //ação e verificação
        mvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isOk());
        
    }
    
}
