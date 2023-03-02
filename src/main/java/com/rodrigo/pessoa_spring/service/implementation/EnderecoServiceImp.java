package com.rodrigo.pessoa_spring.service.implementation;

import com.rodrigo.pessoa_spring.entity.Endereco;
import com.rodrigo.pessoa_spring.exceptions.EnderecoErroException;
import com.rodrigo.pessoa_spring.repository.EnderecoRepository;
import com.rodrigo.pessoa_spring.service.EnderecoService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EnderecoServiceImp implements EnderecoService {

    private EnderecoRepository enderecoRepository;

    public EnderecoServiceImp(EnderecoRepository enderecoRepository){
        this.enderecoRepository = enderecoRepository;
    }

    @Override
    @Transactional
    public Endereco salvarEndereco(Endereco endereco) {
        validarEndereco(endereco);
        return enderecoRepository.save(endereco);
    }

    @Override
    @Transactional
    public Endereco atualizarEndereco(Endereco endereco) {
        Objects.requireNonNull(endereco.getId());
        validarEndereco(endereco);
        return enderecoRepository.save(endereco);
    }

    @Override
    @Transactional
    public void deletarEndereco(Endereco endereco) {
        Objects.requireNonNull(endereco.getId());
        enderecoRepository.delete(endereco);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Endereco> buscarEndereco(Endereco enderecoFiltro) {

            Example<Endereco> example = Example.of(enderecoFiltro,
                    ExampleMatcher.matching()
                            .withIgnoreCase()   // ignorar maisculo e minusculo
                            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

            return enderecoRepository.findAll(example);
    }

    @Override
    public Optional<Endereco> obterEnderecoPorId(Long id) {
        Optional<Endereco> endereco =  enderecoRepository.findById(id);
        return endereco;
    }

    @Override
    public Optional<Endereco> obterEnderecoPorCep(String cep) {
        Optional<Endereco> endereco =  enderecoRepository.findByCep(cep);
        return endereco;
    }

    @Override
    public void validarEndereco(Endereco endereco) {
        if(endereco.getCidade() == null || endereco.getCidade()==""){
            throw new EnderecoErroException("Informe uma Cidade");
        }
        if(endereco.getCep() == null || endereco.getCep()=="" ){
            throw new EnderecoErroException("Informe uma Cep");
        }
        if(endereco.getCep().length() !=8 || endereco.getCep().contains("-")){
            throw new EnderecoErroException("Informe uma Cep Valido");
        }
    }
}
