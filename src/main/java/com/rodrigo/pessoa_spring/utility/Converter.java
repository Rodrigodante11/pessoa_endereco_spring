package com.rodrigo.pessoa_spring.utility;
import com.rodrigo.pessoa_spring.dto.EnderecoDTO;
import com.rodrigo.pessoa_spring.dto.PessoaDTO;
import com.rodrigo.pessoa_spring.entity.Endereco;
import com.rodrigo.pessoa_spring.entity.Pessoa;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Converter {

    public static EnderecoDTO converter(Endereco endereco){
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .cep(endereco.getCep())
                .Logradouro(endereco.getLogradouro())
                .cidade(endereco.getCidade())
                .numero(endereco.getNumero())
                .isEnderecoprincipal(endereco.isEnderecoprincipal())
                .build();
    }

    public static Endereco converter(EnderecoDTO enderecoDTO){
        return Endereco.builder()
                .id(enderecoDTO.getId())
                .cep(enderecoDTO.getCep())
                .Logradouro(enderecoDTO.getLogradouro())
                .cidade(enderecoDTO.getCidade())
                .numero(enderecoDTO.getNumero())
                .isEnderecoprincipal(enderecoDTO.isEnderecoprincipal())
                .build();
    }
    public static Pessoa converter(PessoaDTO pessoaDTO , Endereco endereco){

        return Pessoa.builder()
                .id(pessoaDTO.getId())
                .nome(pessoaDTO.getNome())
                .data_nascimento(pessoaDTO.getData_nascimento())
                .endereco(endereco)
                .build();
    }
}
