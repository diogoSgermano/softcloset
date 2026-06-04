package com.testesII.backend.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ProdutoTamanho {

    PP("PP"),
    P("P"),
    M("M"),
    G("G"),
    GG("GG"),
    GGG("GGG"),
    N34("34"),
    N35("35"),
    N36("36"),
    N37("37"),
    N38("38"),
    N39("39"),
    N40("40"),
    N41("41"),
    N42("42"),
    N43("43"),
    N44("44"),
    N45("45");

    private final String valor;

    ProdutoTamanho(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }


    private static final Map<String, ProdutoTamanho> MAPA_TAMANHOS =
            Arrays.stream(values())
                    .collect(Collectors.toMap(
                            t -> t.getValor().toUpperCase(),
                            Function.identity()
                    ));

    // Conversão String -> Enum
    public static ProdutoTamanho fromValor(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("O tamanho é obrigatório");
        }


        ProdutoTamanho tamanho =
                MAPA_TAMANHOS.get(valor.toUpperCase());

        if (tamanho == null) {
            throw new IllegalArgumentException(
                    "Tamanho inválido: " + valor
            );
        }

        return tamanho;
    }
}