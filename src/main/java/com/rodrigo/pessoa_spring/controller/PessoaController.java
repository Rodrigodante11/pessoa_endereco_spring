package com.rodrigo.pessoa_spring.controller;

import com.rodrigo.pessoa_spring.dto.EnderecoDTO;
import com.rodrigo.pessoa_spring.dto.PessoaDTO;
import com.rodrigo.pessoa_spring.entity.Endereco;
import com.rodrigo.pessoa_spring.entity.Pessoa;
import com.rodrigo.pessoa_spring.exceptions.EnderecoErroException;
import com.rodrigo.pessoa_spring.exceptions.PessoaErroException;
import com.rodrigo.pessoa_spring.service.EnderecoService;
import com.rodrigo.pessoa_spring.service.PessoaService;
import com.rodrigo.pessoa_spring.utility.Converter;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pessoas")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;

    private final EnderecoService enderecoService;

    @ApiOperation(value = "Salva Pessoa")
    @PostMapping
    public ResponseEntity salvar(@RequestBody PessoaDTO pessoaDTO){

        Endereco endereco = enderecoService
                .obterEnderecoPorId(pessoaDTO.getEndereco())
                .orElseThrow( () -> new EnderecoErroException("Endereco nao encontrado para o ID informado."));

        Pessoa pessoa = Converter.converter(pessoaDTO, endereco);

        try{
            Pessoa pessoaSalva = pessoaService.salvarPessoa(pessoa);

            return new ResponseEntity(pessoaSalva, HttpStatus.CREATED);
        }catch (PessoaErroException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Atualiza Pessoas cadastratadas")
    @PutMapping("{id}")
    public ResponseEntity<?> atualizar( @PathVariable("id") Long id, @RequestBody PessoaDTO pessoaDTO ) {

        Endereco endereco = enderecoService
                .obterEnderecoPorId(pessoaDTO.getEndereco())
                .orElseThrow( () -> new EnderecoErroException("Endereco nao encontrado para o ID informado."));

        return pessoaService.obterPessoaPorId(id).map( entity -> {
            try {

                Pessoa pessoa = Converter.converter(pessoaDTO, endereco);
                pessoa.setId(entity.getId());
                pessoaService.atualizarPessoa(pessoa);
                return ResponseEntity.ok(pessoa);

            }catch (PessoaErroException e) {

                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet( () ->
                new ResponseEntity<>("Pessoa não encontrado na base de Dados.", HttpStatus.BAD_REQUEST) );
    }

    @ApiOperation(value = "Consulta  Pessoa por ID")
    @GetMapping("{id}")
    public ResponseEntity<?> obterPessoaPorId(@PathVariable("id") Long id){

        Optional<Pessoa> pessoa = pessoaService.obterPessoaPorId(id);

        if(pessoa.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(pessoa);
    }

    @ApiOperation(value = "Consulta todas Pessoas cadastratadas")
    @GetMapping("/todas")
    public ResponseEntity<?> obterTodasPessoas(){

        List<Pessoa> pessoas = pessoaService.obterTodasPessoas();

        if(pessoas.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(pessoas);
    }

}
