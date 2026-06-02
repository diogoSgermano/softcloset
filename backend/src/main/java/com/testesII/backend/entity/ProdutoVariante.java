package com.testesII.backend.entity;

import com.testesII.backend.enums.ProdutoTamanho;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"produto_id", "tamanho"}
        )
)
@Entity
public class ProdutoVariante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O produto é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @NotNull(message = "O tamanho é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProdutoTamanho tamanho;

    @NotNull(message = "A quantidade é obrigatória")
    @Column(nullable = false)
    private Integer quantidade;

    public ProdutoVariante() {
    }

    public ProdutoVariante(Long id, Produto produto, ProdutoTamanho tamanho, Integer quantidade) {
        this.id = id;
        this.produto = produto;
        this.tamanho = tamanho;
        this.quantidade = quantidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProdutoVariante that)) return false;

        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}