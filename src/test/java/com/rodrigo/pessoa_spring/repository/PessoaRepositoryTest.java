package com.rodrigo.pessoa_spring.repository;

import com.rodrigo.pessoa_spring.entity.Endereco;
import com.rodrigo.pessoa_spring.entity.Pessoa;
import com.rodrigo.pessoa_spring.utility.Criar;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith( SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PessoaRepositoryTest {
    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    TestEntityManager entityManager;

    public Pessoa criarEPersistirUmaPessoa(){
        Pessoa pessoa = Criar.pessoa();
        entityManager.persist(pessoa);
        return pessoa;
    }

    @Test
    public void deveSalvarUmaPessoa(){
        Pessoa pessoa = Criar.pessoa();

        pessoa =  pessoaRepository.save(pessoa);

        assertThat(pessoa.getId()).isNotNull();
    }

    @Test
    public void deveDetelarUmEndereco(){
        Pessoa pessoa = criarEPersistirUmaPessoa();

        pessoa = entityManager.find(Pessoa.class, pessoa.getId()); // Achando o endereco criado na base de dados

        pessoaRepository.delete(pessoa);

        //Procurando Novamente se existe o endereco apos deletado
        Pessoa pessoaInexistente = entityManager.find(Pessoa.class, pessoa.getId());

        assertThat(pessoaInexistente).isNull();
    }

    @Test
    public void deveBuscarUmEnderecoPorId(){
        Pessoa pessoa = criarEPersistirUmaPessoa();

        Pessoa result = entityManager.find(Pessoa.class, pessoa.getId()); // Achando o endereco criado na base de dados

        assertThat(result.getId()).isNotNull();
    }

    @Test
    public void deveAtualizarUmEndereco(){
        Pessoa pessoa = criarEPersistirUmaPessoa();
        Endereco endereco = Criar.endereco();

        endereco.setCep("11111111");
        endereco.setCidade("cidade editado");
        endereco.setLogradouro("praca editado");
        pessoa.setNome("Rodrigo Editado");
        endereco.setEnderecoprincipal(false);
        pessoa.setEndereco(endereco);

        pessoaRepository.save(pessoa);

        Pessoa pesoaAtualizado = entityManager.find(Pessoa.class, pessoa.getId());

        assertThat(pesoaAtualizado.getNome()).isEqualTo("Rodrigo Editado");
        assertThat(pessoa.getEndereco().getCep()).isEqualTo("11111111");
        assertThat(pessoa.getEndereco().getCidade()).isEqualTo("cidade editado");
        assertThat(pessoa.getEndereco().getLogradouro()).isEqualTo("praca editado");
        assertThat(pessoa.getEndereco().isEnderecoprincipal()).isFalse();

    }

}
