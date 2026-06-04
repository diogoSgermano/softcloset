package com.testesII.backend.dto;

import com.testesII.backend.enums.Categoria;
import com.testesII.backend.enums.Classificacao;

import java.math.BigDecimal;
import java.util.List;

public class ProdutoResponseDTO {
    private Long idProduto;
    private String nome;
    private String marca;
    private Classificacao classificacao;
    private Categoria categoria;
    private String descricao;
    private BigDecimal preco;
    private String imagemUrl;
    private List<ProdutoVarianteResponseDTO> tamanhos;

    // Constructors
    public ProdutoResponseDTO() {
    }

    public ProdutoResponseDTO(Long idProduto, String nome, String marca, Classificacao classificacao, Categoria categoria, String descricao, BigDecimal preco, String imagemUrl, List<ProdutoVarianteResponseDTO> tamanhos) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.marca = marca;
        this.classificacao = classificacao;
        this.categoria = categoria;
        this.descricao = descricao;
        this.preco = preco;
        this.imagemUrl = imagemUrl;
        this.tamanhos = tamanhos;
    }

    // Getters and Setters
    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

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

    public List<ProdutoVarianteResponseDTO> getTamanhos() {
        return tamanhos;
    }

    public void setTamanhos(List<ProdutoVarianteResponseDTO> tamanhos) {
        this.tamanhos = tamanhos;
    }
}
