package com.rodrigo.pessoa_spring.service;

import com.rodrigo.pessoa_spring.entity.Endereco;
import com.rodrigo.pessoa_spring.exceptions.EnderecoErroException;
import com.rodrigo.pessoa_spring.repository.EnderecoRepository;
import com.rodrigo.pessoa_spring.service.implementation.EnderecoServiceImp;
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
public class EnderecoServiceTest {

     @SpyBean
     EnderecoServiceImp enderecoServiceImp;

     @MockBean
     EnderecoRepository enderecoRepository;

    @Test
    public void deveSalvarUmEndereco(){
        Endereco enderecoASalvar = Criar.endereco();

        Assertions.assertDoesNotThrow(() -> {

            Mockito.doNothing().when(enderecoServiceImp).validarEndereco(enderecoASalvar); // mesma classe que estou tentando

            Endereco enderecosalvo = Criar.endereco();
            enderecosalvo.setId(1L);

            Mockito.when(enderecoRepository.save(enderecoASalvar)).thenReturn(enderecosalvo); // classe que nao quero testar

            Endereco EnderecoSalvoImp = enderecoServiceImp.salvarEndereco(enderecoASalvar);


            Assertions.assertNotNull(EnderecoSalvoImp);
            Assertions.assertEquals(EnderecoSalvoImp.getId(), enderecosalvo.getId());
            Assertions.assertEquals(EnderecoSalvoImp.getNumero(), enderecosalvo.getNumero());
            Assertions.assertEquals(EnderecoSalvoImp.getCep(), enderecosalvo.getCep());
            Assertions.assertEquals(EnderecoSalvoImp.getCidade(), enderecosalvo.getCidade());
            Assertions.assertEquals(EnderecoSalvoImp.getLogradouro(), enderecosalvo.getLogradouro());
            Assertions.assertEquals(EnderecoSalvoImp.isEnderecoprincipal(), enderecosalvo.isEnderecoprincipal() );

        });
    }

    @Test
    public void deveAtualizarUmEndereco() {

        Endereco enderecoSalvo =  Criar.endereco();
        enderecoSalvo.setId(1L);


        // nao quero testar o metodo (validar)
        Mockito.doNothing().when(enderecoServiceImp).validarEndereco(enderecoSalvo); // mesma classe que estou tentando

        //o que realmente quero testar
        enderecoServiceImp.atualizarEndereco(enderecoSalvo);


        Mockito.verify(enderecoRepository, Mockito.times(1)).save(enderecoSalvo);

    }

    @Test
    public void deveLancarUmErroAoTentarAtualizarumEnderecoQueNaoFoiSaldo() {


        Assertions.assertThrows(NullPointerException.class, () -> {

            Endereco enderecoSalvo = Criar.endereco();

            enderecoServiceImp.atualizarEndereco(enderecoSalvo);

            Mockito.verify(enderecoRepository, Mockito.never()).save(enderecoSalvo);

        });
    }

    @Test
    public void deveDelearUmEndereco(){

        Endereco endereco = Criar.endereco();
        endereco.setId(1L);

        enderecoServiceImp.deletarEndereco(endereco);

        Mockito.verify(enderecoRepository).delete(endereco);
    }

    @Test
    public void deveLancarUmErroAoTentarDeletarUmEndereco(){

        Assertions.assertThrows(NullPointerException.class, () -> {

            Endereco endereco = Criar.endereco();

            enderecoServiceImp.deletarEndereco(endereco);

            Mockito.verify(enderecoRepository, Mockito.never()).delete(endereco);
        });
    }
    @Test
    public void deveBuscarEndereco(){

        Endereco endereco = Criar.endereco();
        endereco.setId(1L);

        List<Endereco> lista = Arrays.asList(endereco); // cast para list

        Mockito.when(enderecoRepository.findAll(Mockito.any(Example.class))).thenReturn(lista);

        List<Endereco> resultado = enderecoServiceImp.buscarEndereco(endereco);

        Assertions.assertNotNull(resultado);
        Assertions.assertArrayEquals(resultado.toArray(), lista.toArray());
        Assertions.assertEquals(resultado.size(), 1);

    }

    @Test
    public void deveObterUmEnderecoPorId(){

        Endereco endereco = Criar.endereco();
        Long id = 1L;
        endereco.setId(id);

        Mockito.when(enderecoRepository.findById(id)).thenReturn(Optional.of(endereco));

        Optional<Endereco> resultado = enderecoServiceImp.obterEnderecoPorId(id);

        Assertions.assertTrue(resultado.isPresent());

        Mockito.verify(enderecoRepository).findById(id);
    }

    @Test
    public void deveRetornarVazioQuandoUmEnderecoNaoExiste(){
        Endereco endereco = Criar.endereco();
        Long id = 1L;
        endereco.setId(id);

        Mockito.when(enderecoRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Endereco> resultado = enderecoServiceImp.obterEnderecoPorId(id);

        Assertions.assertFalse(resultado.isPresent());

        Mockito.verify(enderecoRepository).findById(id);
    }

    @Test
    public void deveObterUmEnderecoPorCep(){

        Endereco endereco = Criar.endereco();
        String cep = "37548000";

        Mockito.when(enderecoRepository.findByCep(cep)).thenReturn(Optional.of(endereco));

        Optional<Endereco> resultado = enderecoServiceImp.obterEnderecoPorCep(cep);

        Assertions.assertTrue(resultado.isPresent());

        Mockito.verify(enderecoRepository).findByCep(cep);
    }

    @Test
    public void deveRetornarVazioQuandoUmEnderecoNaoExistePorCep(){
        String cep = "12345678";

        Mockito.when(enderecoRepository.findByCep(cep)).thenReturn(Optional.empty());

        Optional<Endereco> resultado = enderecoServiceImp.obterEnderecoPorCep(cep);

        Assertions.assertFalse(resultado.isPresent());

        Mockito.verify(enderecoRepository).findByCep(cep);
    }

    @Test
    public void naoDeveSalvarUmEnderecoQuandoHouverErroDeValidao(){

        Assertions.assertThrows(EnderecoErroException.class, () -> {

            Endereco enderecoASalvar = Criar.endereco();
            Mockito.doThrow(EnderecoErroException.class).when(enderecoServiceImp).validarEndereco(enderecoASalvar);

            enderecoServiceImp.salvarEndereco(enderecoASalvar);

            Mockito.verify(enderecoRepository, Mockito.never()).save(enderecoASalvar);
        });
    }

}
