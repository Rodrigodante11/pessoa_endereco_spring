package com.rodrigo.pessoa_spring.utility;

import com.rodrigo.pessoa_spring.entity.Endereco;
import com.rodrigo.pessoa_spring.entity.Pessoa;
import lombok.experimental.UtilityClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@UtilityClass
public class Criar {

    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    private static String CEP = "37548000";
    private static String CIDADE = "Rio de Janeiro";
    private static String LOGRADOURO= "Praca";
    private static int NUMERO = 111;

    private static boolean ISENDERECOPRINCIPAL = true;

    private static String NOME = "Rodrigo Augusto de Oliveira";
    private static Date DATA_NASCIMENTO;

    static {
        try {
            DATA_NASCIMENTO = formato.parse("01/03/2023");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Endereco endereco() { // usado para testes
        return Endereco.builder()
                .cep(CEP)
                .cidade(CIDADE)
                .Logradouro(LOGRADOURO)
                .numero(NUMERO)
                .isEnderecoprincipal(ISENDERECOPRINCIPAL).build();
    }

    public static Pessoa pessoa(){ // usado para testes
        return Pessoa.builder()
                .nome(NOME)
                .data_nascimento(DATA_NASCIMENTO)
                .endereco(Criar.endereco())
                .build();
    }


}
