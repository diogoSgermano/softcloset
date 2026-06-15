package com.testesII.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.testesII.backend.dto.ProdutoRequestDTO;
import com.testesII.backend.dto.ProdutoResponseDTO;
import com.testesII.backend.dto.ProdutoVarianteResponseDTO;
import com.testesII.backend.entity.Produto;
import com.testesII.backend.entity.ProdutoVariante;
import com.testesII.backend.enums.Classificacao;
import com.testesII.backend.service.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> getAllProdutos() {

        List<ProdutoResponseDTO> produtos = produtoService.listarTodos()
                .stream()
                .map(this::convertToResponseDTOLight)
                .toList();

        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> getProdutoById(
            @PathVariable Long id) {

        return produtoService.buscarPorId(id)
                .map(this::convertToResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> createProduto(
            @Valid @RequestBody ProdutoRequestDTO dto) {

        Produto produto = convertToEntity(dto);

        Produto savedProduto = produtoService.salvar(produto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertToResponseDTO(savedProduto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> updateProduto(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoRequestDTO dto) {

        try {

            Produto produto = convertToEntity(dto);

            produto.setIdProduto(id);

            Produto updatedProduto =
                    produtoService.atualizar(id, produto);

            return ResponseEntity.ok(
                    convertToResponseDTO(updatedProduto)
            );

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(
            @PathVariable Long id) {

        try {

            produtoService.deletar(id);

            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProdutoResponseDTO>> searchProdutos(
            @RequestParam String q) {

        List<ProdutoResponseDTO> produtos =
                produtoService.buscarPorNome(q)
                        .stream()
                        .map(this::convertToResponseDTO)
                        .toList();

        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/classificacao")
    public ResponseEntity<List<ProdutoResponseDTO>> getProdutosByClassificacao(
            @RequestParam Classificacao classificacao) {

        List<ProdutoResponseDTO> produtos =
                produtoService.filtrarPorClassificacao(classificacao)
                        .stream()
                        .map(this::convertToResponseDTO)
                        .toList();

        return ResponseEntity.ok(produtos);
    }

    private ProdutoResponseDTO convertToResponseDTO(
            Produto produto) {

        List<ProdutoVarianteResponseDTO> variantesDTO =
                produto.getTamanhos()
                        .stream()
                        .map(variante ->
                                new ProdutoVarianteResponseDTO(
                                        variante.getId(),
                                        variante.getTamanho(),
                                        variante.getQuantidade()
                                )
                        )
                        .toList();

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

        private ProdutoResponseDTO convertToResponseDTOLight(Produto produto) {
                return new ProdutoResponseDTO(
                                produto.getIdProduto(),
                                produto.getNome(),
                                produto.getMarca(),
                                produto.getClassificacao(),
                                produto.getCategoria(),
                                produto.getDescricao(),
                                produto.getPreco(),
                                produto.getImagemUrl(),
                                List.of() // retornar lista vazia de variantes para reduzir payload na listagem
                );
        }

    private Produto convertToEntity(
            ProdutoRequestDTO dto) {

        Produto produto = new Produto();

        produto.setNome(dto.nome());
        produto.setMarca(dto.marca());
        produto.setClassificacao(dto.classificacao());
        produto.setCategoria(dto.categoria());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setImagemUrl(dto.imagemUrl());

        if (dto.tamanhos() != null) {

            List<ProdutoVariante> variantes =
                    dto.tamanhos()
                            .stream()
                            .map(varianteDTO -> {

                                ProdutoVariante variante =
                                        new ProdutoVariante();

                                variante.setTamanho(
                                        varianteDTO.tamanho()
                                );

                                variante.setQuantidade(
                                        varianteDTO.quantidade()
                                );

                                variante.setProduto(produto);

                                return variante;
                            })
                            .toList();

            produto.setTamanhos(variantes);
        }

        return produto;
    }
}