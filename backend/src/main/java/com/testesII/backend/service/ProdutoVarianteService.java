package com.testesII.backend.service;

import com.testesII.backend.entity.ProdutoVariante;
import com.testesII.backend.repository.ProdutoVarianteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoVarianteService {

    private final ProdutoVarianteRepository produtoVarianteRepository;

    public ProdutoVarianteService(ProdutoVarianteRepository produtoVarianteRepository) {
        this.produtoVarianteRepository = produtoVarianteRepository;
    }

    public List<ProdutoVariante> listarTodas() {
        return produtoVarianteRepository.findAll();
    }

    public Optional<ProdutoVariante> buscarPorId(Long id) {
        return produtoVarianteRepository.findById(id);
    }

    @Transactional
    public ProdutoVariante salvar(ProdutoVariante produtoVariante) {
        return produtoVarianteRepository.save(produtoVariante);
    }

    @Transactional
    public void deletar(Long id) {
        if (!produtoVarianteRepository.existsById(id)) {
            throw new RuntimeException("Produto Variante não encontrado com o ID: " + id);
        }
        produtoVarianteRepository.deleteById(id);
    }

    @Transactional
    public ProdutoVariante atualizar(Long id, ProdutoVariante produtoVarianteAtualizada) {
        return produtoVarianteRepository.findById(id)
                .map(varianteExistente -> {
                    varianteExistente.setTamanho(produtoVarianteAtualizada.getTamanho());
                    varianteExistente.setQuantidade(produtoVarianteAtualizada.getQuantidade());
                    // Note: The 'produto' (parent) should ideally not be changed directly via variant update
                    // If it needs to be changed, it implies a different business logic or a new variant.
                    return produtoVarianteRepository.save(varianteExistente);
                })
                .orElseThrow(() -> new RuntimeException("Produto Variante não encontrado com o ID: " + id));
    }
}
