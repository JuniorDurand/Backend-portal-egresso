package egresso.demo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EgressoDTO {
    private String nome;
    private String email;
    private String cpf;
    private String resumo;
    private String url_foto;
}
