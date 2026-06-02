package com.testesII.backend.entity;

import com.testesII.backend.enums.Categoria;
import com.testesII.backend.enums.Classificacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "produtos")
@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_produto;

    @NotBlank(message = "O nome é obrigatório")
    @Column(nullable = false,length = 50)
    private String nome;

    @Column(nullable=false, length=100)
    private  String marca;

    @PrePersist
    public void defaultValues() {
        if (this.marca == null || this.marca.isBlank()) {
            this.marca = "Sem marca";
        }
    }

    @NotNull(message = "Classificação é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,
            columnDefinition =
                    "VARCHAR(20) " +
                            "CHECK (classificacao " +
                            "IN ('NOVO','DESTAQUE','NORMAL' ))")
    private Classificacao classificacao ;

    @NotNull(message = "Categoria é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,
            columnDefinition =
                    "VARCHAR(20) " +
                            "CHECK (categoria " +
                            "IN ('CALCADOS','CAMISAS','CALCAS','VESTIDOS' ))")
    private Categoria categoria;

    @NotBlank(message = "A descrição é obrigatória")
    @Column(nullable = false,length = 255)
    private String descricao;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value="0.00")
    @Column(nullable = false,precision = 10,scale = 2)
    private BigDecimal preco;

    @NotBlank(message = "A URL da imagem é obrigatória")
    @Column(nullable = false)
    private String imagemUrl;

        @NotEmpty(message = "O tamanho é obrigatório")
        @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
        private List<ProdutoVariante> tamanhos= new ArrayList<>();

    public Produto() {
    }

    public Produto(Long id_produto, String nome, String marca, Classificacao classificacao, Categoria categoria, String descricao, BigDecimal preco, String imagemUrl, List<ProdutoVariante> tamanhos) {
        this.id_produto = id_produto;
        this.nome = nome;
        this.marca = marca;
        this.classificacao = classificacao;
        this.categoria = categoria;
        this.descricao = descricao;
        this.preco = preco;
        this.imagemUrl = imagemUrl;
        this.tamanhos = tamanhos;
    }

    public List<ProdutoVariante> getTamanhos() {
        return tamanhos;
    }

    public void setTamanhos(List<ProdutoVariante> tamanhos) {
        this.tamanhos = tamanhos;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Classificacao getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(Classificacao classificacao) {
        this.classificacao = classificacao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId_produto() {
        return id_produto;
    }

    public void setId_produto(Long id_produto) {
        this.id_produto = id_produto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto produto)) return false;

        return Objects.equals(id_produto, produto.id_produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_produto, nome, marca, classificacao, categoria, descricao, preco, imagemUrl, tamanhos);
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id_produto=" + id_produto +
                ", nome='" + nome + '\'' +
                ", marca='" + marca + '\'' +
                ", classificacao=" + classificacao +
                ", categoria=" + categoria +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", imagemUrl='" + imagemUrl + '\'' +
                ", tamanhos=" + tamanhos +
                '}';
    }
}
