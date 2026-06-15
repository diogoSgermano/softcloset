package com.testesII.backend.repository;

import com.testesII.backend.entity.Produto;
import com.testesII.backend.enums.Classificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
    List<Produto> findByNomeContainingIgnoreCase(String nome);

    List<Produto> findByClassificacao(Classificacao classificacao);

}
