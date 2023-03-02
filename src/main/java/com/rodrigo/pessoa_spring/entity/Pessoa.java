package com.rodrigo.pessoa_spring.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name="pessoa" , schema = "pessoa_endereco")
public class Pessoa {

    @Id
    @Column(name="id", unique = true)
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Date data_nascimento;
    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

//    @OneToMany
//    @JoinColumn(name = "endereco_id")
//    private List<Endereco> endereco;

}
