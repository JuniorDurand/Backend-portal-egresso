package egresso.demo.controller.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfEgressoDTO {
    private Long id;
    private String empresa;
    private String descricao;
    private LocalDate dataRegistro;
    private Long id_cargo;
    private Long id_faixa_salario;
}
