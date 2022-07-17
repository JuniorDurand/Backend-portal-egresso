package egresso.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name="egresso")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Egresso {
    @Id
    @Column(name="id_egresso")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nome") // not null
    private String nome;

    @Column(name="email") // not null
    private String email;

    @Column(name="cpf") // not null
    private String cpf;
    
    @Column(name="resumo") 
    private String resumo;

    @Column(name="url_foto") 
    private String url_foto;
}
