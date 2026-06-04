package com.testesII.backend.dto;

import com.testesII.backend.enums.ProdutoTamanho;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProdutoVarianteRequestDTO {

    @NotNull(message = "O tamanho é obrigatório")
    private ProdutoTamanho tamanho;

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 0, message = "A quantidade não pode ser negativa")
    private Integer quantidade;

    // Getters and Setters
    public ProdutoTamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(ProdutoTamanho tamanho) {
        this.tamanho = tamanho;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
