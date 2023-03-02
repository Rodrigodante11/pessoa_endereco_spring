package com.rodrigo.pessoa_spring.repository;

import com.rodrigo.pessoa_spring.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnderecoRepository  extends JpaRepository<Endereco, Long> {

    Optional<Endereco> findByCep(Long aLong);
}
