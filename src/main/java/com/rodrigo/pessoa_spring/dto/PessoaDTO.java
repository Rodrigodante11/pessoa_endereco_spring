package com.rodrigo.pessoa_spring.dto;

import com.rodrigo.pessoa_spring.entity.Endereco;
import lombok.*;

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
    private Endereco endereco;

    private Long pessoa;
}
