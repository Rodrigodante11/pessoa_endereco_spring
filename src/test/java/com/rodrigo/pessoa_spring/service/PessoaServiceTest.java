package com.rodrigo.pessoa_spring.service;

import com.rodrigo.pessoa_spring.entity.Endereco;
import com.rodrigo.pessoa_spring.entity.Pessoa;
import com.rodrigo.pessoa_spring.exceptions.PessoaErroException;
import com.rodrigo.pessoa_spring.repository.PessoaRepository;
import com.rodrigo.pessoa_spring.service.implementation.PessoaServiceImp;
import com.rodrigo.pessoa_spring.utility.Criar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith( SpringExtension.class)
@ActiveProfiles("test")
public class PessoaServiceTest {

    @SpyBean
    PessoaServiceImp pessoaServiceImp;

    @MockBean
    PessoaRepository pessoaRepository;

    @Test
    public void deveSalvarUmEndereco(){
        Pessoa pessoaASalvar = Criar.pessoa();

        Assertions.assertDoesNotThrow(() -> {

            Mockito.doNothing().when(pessoaServiceImp).validarPessoa(pessoaASalvar);

            Pessoa pessoasalva = Criar.pessoa();
            pessoasalva.setId(1L);

            Mockito.when(pessoaRepository.save(pessoaASalvar)).thenReturn(pessoasalva); // classe que nao quero testar

            Pessoa pessoaSalvaImp = pessoaServiceImp.salvarPessoa(pessoaASalvar);

            Assertions.assertNotNull(pessoaSalvaImp);
            Assertions.assertEquals(pessoaSalvaImp.getId(), pessoasalva.getId());
            Assertions.assertEquals(pessoaSalvaImp.getEndereco(), pessoasalva.getEndereco());
            Assertions.assertEquals(pessoaSalvaImp.getNome(), pessoasalva.getNome());
            Assertions.assertEquals(pessoaSalvaImp.getEndereco().getCep(), pessoasalva.getEndereco().getCep());
            Assertions.assertEquals(pessoaSalvaImp.getEndereco().getCidade(), pessoasalva.getEndereco().getCidade());
            Assertions.assertEquals(pessoaSalvaImp.getEndereco().getLogradouro(), pessoasalva.getEndereco().getLogradouro());
            Assertions.assertEquals(pessoaSalvaImp.getEndereco().isEnderecoprincipal(), pessoasalva.getEndereco().isEnderecoprincipal() );

        });

    }
    @Test
    public void deveAtualizarUmaPessoa() {

        Pessoa pessoaSalva = Criar.pessoa();
        pessoaSalva.setId(1L);

        Mockito.doNothing().when(pessoaServiceImp).validarPessoa(pessoaSalva); // mesma classe que estou tentando

        pessoaServiceImp.atualizarPessoa(pessoaSalva);

        Mockito.verify(pessoaRepository, Mockito.times(1)).save(pessoaSalva);
    }
    @Test
    public void deveLancarUmErroAoTentarAtualizarUmaPessoaQueNaoFoiSaldo() {

        Assertions.assertThrows(NullPointerException.class, () -> {

            Pessoa pessoaSalva = Criar.pessoa();

            Mockito.doNothing().when(pessoaServiceImp).validarPessoa(pessoaSalva);

            pessoaServiceImp.atualizarPessoa(pessoaSalva);

            Mockito.verify(pessoaRepository, Mockito.times(1)).save(pessoaSalva);

        });
    }

    @Test
    public void deveDelearUmaPessoa(){

        Pessoa pessoa = Criar.pessoa();
        pessoa.setId(1L);

        pessoaServiceImp.deletarPessoa(pessoa);

        Mockito.verify(pessoaRepository).delete(pessoa);
    }

    @Test
    public void deveLancarUmErroAoTentarDeletarUmaPessoa(){

        Assertions.assertThrows(NullPointerException.class, () -> {

            Pessoa pessoa = Criar.pessoa();

            pessoaServiceImp.deletarPessoa(pessoa);

            Mockito.verify(pessoaRepository, Mockito.never()).delete(pessoa);
        });

    }

    @Test
    public void deveBuscarPessoa(){

        Pessoa pessoa = Criar.pessoa();
        pessoa.setId(1L);

        List<Pessoa> lista = List.of(pessoa); // cast para list

        Mockito.when(pessoaRepository.findAll(Mockito.any(Example.class))).thenReturn(lista);

        List<Pessoa> resultado = pessoaServiceImp.buscarPessoa(pessoa);

        Assertions.assertNotNull(resultado);
        Assertions.assertArrayEquals(resultado.toArray(), lista.toArray());
        Assertions.assertEquals(resultado.size(), 1);

    }

    @Test
    public void deveObterUmaPessoaPorId(){

        Pessoa pessoa = Criar.pessoa();
        Long id = 1L;
        pessoa.setId(id);

        Mockito.when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoa));

        Optional<Pessoa> resultado = pessoaServiceImp.obterPessoaPorId(id);

        Assertions.assertTrue(resultado.isPresent());

        Mockito.verify(pessoaRepository).findById(id);
    }

    @Test
    public void deveRetornarVazioQuandoUmaPessoaNaoExiste(){
        Pessoa pessoa = Criar.pessoa();
        Long id = 1L;
        pessoa.setId(id);

        Mockito.when(pessoaRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Pessoa> resultado = pessoaServiceImp.obterPessoaPorId(id);

        Assertions.assertFalse(resultado.isPresent());

        Mockito.verify(pessoaRepository).findById(id);
    }

    @Test
    public void naoDeveSalvarUmLancamentoQuandoHouverErroDeValidao(){

        Assertions.assertThrows(PessoaErroException.class, () -> {

            Pessoa pessoa = Criar.pessoa();
            Mockito.doThrow(PessoaErroException.class).when(pessoaServiceImp).validarPessoa(pessoa);

            pessoaServiceImp.salvarPessoa(pessoa);

            Mockito.verify(pessoaRepository, Mockito.never()).save(pessoa);
        });
    }
}
