package com.rodrigo.pessoa_spring.dto;

import lombok.*;
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO {
    private Long id;
    private String Logradouro;
    private String cep;
    private int numero;
    private boolean isEnderecoprincipal;
    private String cidade;
}
