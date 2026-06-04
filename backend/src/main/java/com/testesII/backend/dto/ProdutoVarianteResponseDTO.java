package com.testesII.backend.dto;

import com.testesII.backend.enums.ProdutoTamanho;

public class ProdutoVarianteResponseDTO {
    private Long id; // Assuming ProdutoVariante has an ID
    private ProdutoTamanho tamanho;
    private Integer quantidade;

    // Constructors
    public ProdutoVarianteResponseDTO() {
    }

    public ProdutoVarianteResponseDTO(Long id, ProdutoTamanho tamanho, Integer quantidade) {
        this.id = id;
        this.tamanho = tamanho;
        this.quantidade = quantidade;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
