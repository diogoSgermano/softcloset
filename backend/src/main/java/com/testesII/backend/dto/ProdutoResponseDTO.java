package com.testesII.backend.dto;

import com.testesII.backend.enums.Categoria;
import com.testesII.backend.enums.Classificacao;

import java.math.BigDecimal;
import java.util.List;

public record ProdutoResponseDTO(
        Long idProduto,
        String nome,
        String marca,
        Classificacao classificacao,
        Categoria categoria,
        String descricao,
        BigDecimal preco,
        String imagemUrl,
        List<ProdutoVarianteResponseDTO> tamanhos
) {
}