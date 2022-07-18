package egresso.demo.controller.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CadastroDTO {
    private EgressoDTO egresso;
    private CursoEgressoDTO cursoEgresso;
    private ProfEgressoDTO profissao;
    private List<ContatoEgressoDTO> contatoEgressoList;
    private UsuarioDTO usuario;
}
