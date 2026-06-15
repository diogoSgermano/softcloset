package com.testesII.backend.dto;

import com.testesII.backend.enums.ProdutoTamanho;

public record ProdutoVarianteResponseDTO(
        Long id,
        ProdutoTamanho tamanho,
        Integer quantidade
) {
}