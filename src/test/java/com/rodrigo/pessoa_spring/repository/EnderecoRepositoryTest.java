package com.rodrigo.pessoa_spring.repository;


import com.rodrigo.pessoa_spring.entity.Endereco;
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
public class EnderecoRepositoryTest {

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    TestEntityManager entityManager;

    public Endereco criarEPersistirUmEndereco(){
        Endereco endereco = Criar.endereco();
        entityManager.persist(endereco);
        return endereco;
    }

    @Test
    public void deveSalvarUmEndereco(){
        Endereco endereco = Criar.endereco();

        endereco = enderecoRepository.save(endereco);

        assertThat(endereco.getId()).isNotNull();
    }

    @Test
    public void deveDetelarUmEndereco(){
        Endereco endereco = criarEPersistirUmEndereco();

        endereco = entityManager.find(Endereco.class, endereco.getId()); // Achando o endereco criado na base de dados

        enderecoRepository.delete(endereco);

        //Procurando Novamente se existe o endereco apos deletado
        Endereco enderecoInexistente = entityManager.find(Endereco.class, endereco.getId());

        assertThat(enderecoInexistente).isNull();
    }

    @Test
    public void deveBuscarUmEnderecoPorId(){
        Endereco endereco = criarEPersistirUmEndereco();

        Endereco result = entityManager.find(Endereco.class, endereco.getId()); // Achando o endereco criado na base de dados

        assertThat(result.getId()).isNotNull();
    }

    @Test
    public void deveAtualizarUmEndereco(){
        Endereco endereco = criarEPersistirUmEndereco();

        endereco.setCep("11111111");
        endereco.setCidade("cidade editado");
        endereco.setLogradouro("praca editado");
        endereco.setEnderecoprincipal(false);

        enderecoRepository.save(endereco);

        Endereco enderecoAtualizado = entityManager.find(Endereco.class, endereco.getId());

        assertThat(enderecoAtualizado.getCep()).isEqualTo("11111111");
        assertThat(enderecoAtualizado.getCidade()).isEqualTo("cidade editado");
        assertThat(enderecoAtualizado.getLogradouro()).isEqualTo("praca editado");
        assertThat(enderecoAtualizado.isEnderecoprincipal()).isFalse();

    }

}
