package com.testesII.backend.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Classificacao {

    NOVO("Novo"),
    DESTAQUE("Destaque"),
    NORMAL("Normal");

    private final String descricao;

    Classificacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    private static final Map<String, Classificacao> MAPA_CLASSIFICACOES =
            Arrays.stream(values())
                    .collect(Collectors.toMap(
                            c -> c.getDescricao().toUpperCase(),
                            Function.identity()
                    ));

    public static Classificacao fromDescricao(String descricao) {

        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException(
                    "A classificação é obrigatória"
            );
        }

        Classificacao classificacao =
                MAPA_CLASSIFICACOES.get(
                        descricao.toUpperCase()
                );

        if (classificacao == null) {
            throw new IllegalArgumentException(
                    "Classificação inválida: " + descricao
            );
        }

        return classificacao;
    }
}