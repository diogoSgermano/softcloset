package com.testesII.backend.controller;

import com.testesII.backend.dto.ProdutoRequestDTO;
import com.testesII.backend.dto.ProdutoResponseDTO;
import com.testesII.backend.dto.ProdutoVarianteRequestDTO;
import com.testesII.backend.dto.ProdutoVarianteResponseDTO;
import com.testesII.backend.entity.Produto;
import com.testesII.backend.entity.ProdutoVariante;
import com.testesII.backend.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> getAllProdutos() {
        List<Produto> produtos = produtoService.listarTodos();
        List<ProdutoResponseDTO> produtoResponseDTOS = produtos.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(produtoResponseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> getProdutoById(@PathVariable Long id) {
        return produtoService.buscarPorId(id)
                .map(this::convertToResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> createProduto(@Valid @RequestBody ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = convertToEntity(produtoRequestDTO);
        Produto savedProduto = produtoService.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponseDTO(savedProduto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> updateProduto(@PathVariable Long id, @Valid @RequestBody ProdutoRequestDTO produtoRequestDTO) {
        try {
            Produto produtoToUpdate = convertToEntity(produtoRequestDTO);
            // When updating, we need to ensure the ID is set for the entity
            // The service's update method will then find the existing entity by ID
            // and apply the changes from produtoToUpdate.
            produtoToUpdate.setIdProduto(id); // Ensure the ID is passed for update
            Produto updatedProduto = produtoService.atualizar(id, produtoToUpdate);
            return ResponseEntity.ok(convertToResponseDTO(updatedProduto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        try {
            produtoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private ProdutoResponseDTO convertToResponseDTO(Produto produto) {
        List<ProdutoVarianteResponseDTO> variantesDTO = produto.getTamanhos().stream()
                .map(variante -> new ProdutoVarianteResponseDTO(variante.getId(), variante.getTamanho(), variante.getQuantidade()))
                .collect(Collectors.toList());

        return new ProdutoResponseDTO(
                produto.getIdProduto(),
                produto.getNome(),
                produto.getMarca(),
                produto.getClassificacao(),
                produto.getCategoria(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getImagemUrl(),
                variantesDTO
        );
    }

    private Produto convertToEntity(ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = new Produto();
        produto.setNome(produtoRequestDTO.getNome());
        produto.setMarca(produtoRequestDTO.getMarca());
        produto.setClassificacao(produtoRequestDTO.getClassificacao());
        produto.setCategoria(produtoRequestDTO.getCategoria());
        produto.setDescricao(produtoRequestDTO.getDescricao());
        produto.setPreco(produtoRequestDTO.getPreco());
        produto.setImagemUrl(produtoRequestDTO.getImagemUrl());

        if (produtoRequestDTO.getTamanhos() != null) {
            List<ProdutoVariante> variantes = produtoRequestDTO.getTamanhos().stream()
                    .map(varianteDTO -> {
                        ProdutoVariante variante = new ProdutoVariante();
                        variante.setTamanho(varianteDTO.getTamanho()); // Correctly setting ProdutoTamanho enum
                        variante.setQuantidade(varianteDTO.getQuantidade());
                        variante.setProduto(produto); // Set the parent product
                        return variante;
                    })
                    .collect(Collectors.toList());
            produto.setTamanhos(variantes);
        }
        return produto;
    }
}
