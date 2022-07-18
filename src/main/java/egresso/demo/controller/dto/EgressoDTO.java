package egresso.demo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EgressoDTO {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String resumo;
    private String url_foto;
}
