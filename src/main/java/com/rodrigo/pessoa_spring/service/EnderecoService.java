package com.rodrigo.pessoa_spring.service;

import com.rodrigo.pessoa_spring.entity.Endereco;

import java.util.List;
import java.util.Optional;

public interface EnderecoService {

    Endereco salvarEndereco(Endereco endereco);
    Endereco atualizarEndereco(Endereco endereco);
    void deletarEndereco(Endereco endereco);
    List<Endereco> buscarEndereco(Endereco endereco);
    void validarEndereco(Endereco endereco);
    Optional<Endereco> obterEnderecoPorId(Long id);
    Optional<Endereco> obterEnderecoPorCep(String cep);

}
