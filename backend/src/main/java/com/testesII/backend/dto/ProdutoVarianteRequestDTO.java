package com.testesII.backend.dto;

import com.testesII.backend.enums.ProdutoTamanho;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProdutoVarianteRequestDTO(

        @NotNull(message = "O tamanho é obrigatório")
        ProdutoTamanho tamanho,

        @NotNull(message = "A quantidade é obrigatória")
        @Min(
                value = 0,
                message = "A quantidade não pode ser negativa"
        )
        Integer quantidade

) {
}