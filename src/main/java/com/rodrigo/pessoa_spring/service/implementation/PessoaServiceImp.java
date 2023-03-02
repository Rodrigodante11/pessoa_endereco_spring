package com.rodrigo.pessoa_spring.service.implementation;

import com.rodrigo.pessoa_spring.entity.Pessoa;
import com.rodrigo.pessoa_spring.exceptions.EnderecoErroException;
import com.rodrigo.pessoa_spring.repository.PessoaRepository;
import com.rodrigo.pessoa_spring.service.PessoaService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PessoaServiceImp implements PessoaService {

    private PessoaRepository pessoaRepository;

    public PessoaServiceImp(PessoaRepository pessoaRepository){
        this.pessoaRepository= pessoaRepository;
    }

    @Override
    @Transactional
    public Pessoa salvarPessoa(Pessoa pessoa) {
        validarPessoa(pessoa);
        return pessoaRepository.save(pessoa);
    }

    @Override
    @Transactional
    public Pessoa atualizarPessoa(Pessoa pessoa) {
        Objects.requireNonNull(pessoa.getId());
        validarPessoa(pessoa);
        return pessoaRepository.save(pessoa);
    }

    @Override
    @Transactional
    public void deletarPessoa(Pessoa pessoa) {
        Objects.requireNonNull(pessoa.getId());
        pessoaRepository.delete(pessoa);
    }

    @Override
    public List<Pessoa> buscarPessoa(Pessoa pessoaFiltro) {
        Example<Pessoa> example = Example.of(pessoaFiltro,
                ExampleMatcher.matching()
                        .withIgnoreCase()   // ignorar maisculo e minusculo
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return pessoaRepository.findAll(example);
    }

    @Override
    public void validarPessoa(Pessoa pessoa) {
        if(pessoa.getNome() == null || pessoa.getNome()==""){
            throw new EnderecoErroException("Informe o Nome");
        }
    }

    @Override
    public Optional<Pessoa> obterPessoaPorId(Long id) {
        Optional<Pessoa> pessoa =  pessoaRepository.findById(id);
        return pessoa;
    }

    @Override
    public List<Pessoa> obterTodasPessoas() {
        return pessoaRepository.findAll();
    }
}
