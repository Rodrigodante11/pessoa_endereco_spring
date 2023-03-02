package com.rodrigo.pessoa_spring.service.implementation;

import com.rodrigo.pessoa_spring.entity.Pessoa;

import java.util.List;
import java.util.Optional;

public interface PessoaService {

    Pessoa salvarPessoa(Pessoa pessoa);
    Pessoa atualizarPessoa(Pessoa pessoa);
    void deletarPessoa(Pessoa pessoa);
    List<Pessoa> buscarPessoa(Pessoa pessoa);
    void validarPessoa(Pessoa pessoa);
    Optional<Pessoa> obterPessoaPorId(Long id);

}
