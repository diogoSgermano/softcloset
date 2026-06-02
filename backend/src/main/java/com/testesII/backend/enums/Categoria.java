package com.testesII.backend.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Categoria {

    CALCADOS("Calçados"),
    CAMISAS("Camisas"),
    CALCAS("Calças"),
    VESTIDOS("Vestidos");

    private final String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    private static final Map<String, Categoria> MAPA_CATEGORIAS =
            Arrays.stream(values())
                    .collect(Collectors.toMap(
                            c -> c.getDescricao().toUpperCase(),
                            Function.identity()
                    ));

    public static Categoria fromDescricao(String descricao) {

        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException(
                    "A categoria é obrigatória"
            );
        }

        Categoria categoria =
                MAPA_CATEGORIAS.get(
                        descricao.toUpperCase()
                );

        if (categoria == null) {
            throw new IllegalArgumentException(
                    "Categoria inválida: " + descricao
            );
        }

        return categoria;
    }
}