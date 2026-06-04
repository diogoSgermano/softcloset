package com.testesII.backend.dto;

import com.testesII.backend.enums.Categoria;
import com.testesII.backend.enums.Classificacao;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public class ProdutoRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    private String marca; // Marca can be blank, handled by defaultValues in entity

    @NotNull(message = "Classificação é obrigatória")
    private Classificacao classificacao;

    @NotNull(message = "Categoria é obrigatória")
    private Categoria categoria;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.00", message = "O preço deve ser maior ou igual a 0.00")
    private BigDecimal preco;

    @NotBlank(message = "A URL da imagem é obrigatória")
    private String imagemUrl;

    @NotEmpty(message = "O produto deve possuir pelo menos um tamanho")
    private List<ProdutoVarianteRequestDTO> tamanhos; // Assuming you'll also need a DTO for variants

    // Getters and Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Classificacao getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(Classificacao classificacao) {
        this.classificacao = classificacao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public List<ProdutoVarianteRequestDTO> getTamanhos() {
        return tamanhos;
    }

    public void setTamanhos(List<ProdutoVarianteRequestDTO> tamanhos) {
        this.tamanhos = tamanhos;
    }
}
