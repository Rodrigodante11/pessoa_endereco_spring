package com.rodrigo.pessoa_spring.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name="endereco" , schema = "pessoa_endereco")
public class Endereco {

    @Id
    @Column(name="id", unique = true)
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String Logradouro;
    @Column(length = 10, nullable = false)
    private String cep;
    private int numero;
    @Column(length = 100, nullable = false)
    private String cidade;

}
