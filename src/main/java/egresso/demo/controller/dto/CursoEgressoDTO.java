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
public class CursoEgressoDTO {
    private Long id_curso;
    private Long id_egresso;
    private LocalDate data_inicio;
    private LocalDate data_conclusao;
}
