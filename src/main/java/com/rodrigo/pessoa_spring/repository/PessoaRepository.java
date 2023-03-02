package com.rodrigo.pessoa_spring.repository;

import com.rodrigo.pessoa_spring.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository  extends JpaRepository<Pessoa, Long> {



}
