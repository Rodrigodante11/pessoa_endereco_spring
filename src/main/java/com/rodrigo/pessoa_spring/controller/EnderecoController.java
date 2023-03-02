package com.rodrigo.pessoa_spring.controller;

import com.rodrigo.pessoa_spring.dto.EnderecoDTO;
import com.rodrigo.pessoa_spring.entity.Endereco;
import com.rodrigo.pessoa_spring.exceptions.EnderecoErroException;
import com.rodrigo.pessoa_spring.service.EnderecoService;
import com.rodrigo.pessoa_spring.utility.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enderecos")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService enderecoService;

    @PostMapping()
    public ResponseEntity<?> salvar(@RequestBody EnderecoDTO enderecoDTO){

        try {
            Endereco enderecoEntidade = Converter.converter(enderecoDTO);
            enderecoEntidade = enderecoService.salvarEndereco(enderecoEntidade);
            return new ResponseEntity<>(enderecoEntidade,  HttpStatus.CREATED);

        }catch(EnderecoErroException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("{id}")
    public ResponseEntity<?> obterEnderecoPorId(@PathVariable("id") Long id){
        return enderecoService.obterEnderecoPorId(id)
                .map( endereco -> new ResponseEntity<>(
                            Converter.converter(endereco), HttpStatus.OK
                        )
                ).orElseGet( () -> new ResponseEntity<>((HttpStatus.NOT_FOUND)));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar( @PathVariable("id") Long id, @RequestBody EnderecoDTO dto ) {
        return enderecoService.obterEnderecoPorId(id).map( entity -> {
            try {

                Endereco endereco = Converter.converter(dto);
                endereco.setId(entity.getId());
                enderecoService.atualizarEndereco(endereco);
                return ResponseEntity.ok(endereco);

            }catch (EnderecoErroException e) {

                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet( () ->
                new ResponseEntity<>("Endereco não encontrado na base de Dados.", HttpStatus.BAD_REQUEST) );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletar(@PathVariable("id") Long id ){

        return enderecoService.obterEnderecoPorId(id).map( entidade -> {
            enderecoService.deletarEndereco(entidade);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }).orElseGet( () ->
                new ResponseEntity<>("Endereco nao encontrado na base de dados", HttpStatus.BAD_REQUEST)
        );
    }
}