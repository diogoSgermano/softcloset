package com.testesII.backend.service;

import java.util.List;
import java.util.Optional; // Importar Classificacao

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.testesII.backend.entity.Produto;
import com.testesII.backend.enums.Classificacao;
import com.testesII.backend.repository.ProdutoRepository;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository){
        this.produtoRepository=produtoRepository;
    }

    @Cacheable("produtos")
    public List<Produto> listarTodos(){
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    @Transactional
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Transactional
    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado com o ID: " + id);
        }
        produtoRepository.deleteById(id);
    }

    @Transactional
    public Produto atualizar(Long id, Produto produtoAtualizado) {
        return produtoRepository.findById(id)
                .map(produtoExistente -> {
                    produtoExistente.setNome(produtoAtualizado.getNome());
                    produtoExistente.setMarca(produtoAtualizado.getMarca());
                    produtoExistente.setClassificacao(produtoAtualizado.getClassificacao());
                    produtoExistente.setCategoria(produtoAtualizado.getCategoria());
                    produtoExistente.setDescricao(produtoAtualizado.getDescricao());
                    produtoExistente.setPreco(produtoAtualizado.getPreco());
                    produtoExistente.setImagemUrl(produtoAtualizado.getImagemUrl());
                    // Assuming ProdutoVariante (tamanhos) are handled separately or in a more complex update logic
                    // For simplicity, I'm not directly updating the list of ProdutoVariante here.
                    // If you need to update variants, a more sophisticated approach is required.
                    return produtoRepository.save(produtoExistente);

                })
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));
    }

    // Método ajustado para receber o enum Classificacao diretamente
    public List<Produto> filtrarPorClassificacao(Classificacao classificacao){
        return produtoRepository.findByClassificacao(classificacao);
    }

    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }
}
