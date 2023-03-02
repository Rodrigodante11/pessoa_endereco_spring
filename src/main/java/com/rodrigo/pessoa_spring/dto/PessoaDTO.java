package com.rodrigo.pessoa_spring.dto;

import com.rodrigo.pessoa_spring.entity.Endereco;
import com.rodrigo.pessoa_spring.entity.Pessoa;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDTO {

    private Long id;
    private String nome;
    private Date data_nascimento;
    private Long endereco;

//    @ManyToOne
//    @JoinColumn(name = "pessoa_id")
//    private Pessoa pessoa;
}
