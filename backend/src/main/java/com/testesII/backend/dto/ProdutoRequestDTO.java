package com.testesII.backend.dto;

import com.testesII.backend.enums.Categoria;
import com.testesII.backend.enums.Classificacao;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record ProdutoRequestDTO(

        @NotBlank(message = "O nome é obrigatório")
        String nome,

        String marca,

        @NotNull(message = "Classificação é obrigatória")
        Classificacao classificacao,

        @NotNull(message = "Categoria é obrigatória")
        Categoria categoria,

        @NotBlank(message = "A descrição é obrigatória")
        String descricao,

        @NotNull(message = "O preço é obrigatório")
        @DecimalMin(
                value = "0.00",
                message = "O preço deve ser maior ou igual a 0.00"
        )
        BigDecimal preco,

        @NotBlank(message = "A URL da imagem é obrigatória")
        String imagemUrl,

        @NotEmpty(message = "O produto deve possuir pelo menos um tamanho")
        List<ProdutoVarianteRequestDTO> tamanhos

) {}