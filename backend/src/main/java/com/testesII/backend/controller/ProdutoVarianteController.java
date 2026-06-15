package com.testesII.backend.controller;

import com.testesII.backend.dto.ProdutoVarianteRequestDTO;
import com.testesII.backend.dto.ProdutoVarianteResponseDTO;
import com.testesII.backend.entity.ProdutoVariante;
import com.testesII.backend.service.ProdutoService;
import com.testesII.backend.service.ProdutoVarianteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produto-variantes")
public class ProdutoVarianteController {

    private final ProdutoVarianteService produtoVarianteService;
    private final ProdutoService produtoService;

    public ProdutoVarianteController(
            ProdutoVarianteService produtoVarianteService,
            ProdutoService produtoService
    ) {
        this.produtoVarianteService = produtoVarianteService;
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoVarianteResponseDTO>> getAllProdutoVariantes() {

        List<ProdutoVarianteResponseDTO> responseDTOS =
                produtoVarianteService.listarTodas()
                        .stream()
                        .map(this::convertToResponseDTO)
                        .toList();

        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoVarianteResponseDTO> getProdutoVarianteById(
            @PathVariable Long id
    ) {

        return produtoVarianteService.buscarPorId(id)
                .map(this::convertToResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/produto/{produtoId}")
    public ResponseEntity<ProdutoVarianteResponseDTO> createProdutoVariante(
            @PathVariable Long produtoId,
            @Valid @RequestBody ProdutoVarianteRequestDTO dto
    ) {

        return produtoService.buscarPorId(produtoId)
                .map(produto -> {

                    ProdutoVariante produtoVariante =
                            convertToEntity(dto);

                    produtoVariante.setProduto(produto);

                    ProdutoVariante savedVariante =
                            produtoVarianteService.salvar(produtoVariante);

                    return ResponseEntity
                            .status(HttpStatus.CREATED)
                            .body(convertToResponseDTO(savedVariante));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoVarianteResponseDTO> updateProdutoVariante(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoVarianteRequestDTO dto
    ) {

        try {

            ProdutoVariante produtoVariante =
                    convertToEntity(dto);

            ProdutoVariante updatedVariante =
                    produtoVarianteService.atualizar(
                            id,
                            produtoVariante
                    );

            return ResponseEntity.ok(
                    convertToResponseDTO(updatedVariante)
            );

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProdutoVariante(
            @PathVariable Long id
    ) {

        try {

            produtoVarianteService.deletar(id);

            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }

    private ProdutoVarianteResponseDTO convertToResponseDTO(
            ProdutoVariante produtoVariante
    ) {

        return new ProdutoVarianteResponseDTO(
                produtoVariante.getId(),
                produtoVariante.getTamanho(),
                produtoVariante.getQuantidade()
        );
    }

    private ProdutoVariante convertToEntity(
            ProdutoVarianteRequestDTO dto
    ) {

        ProdutoVariante produtoVariante =
                new ProdutoVariante();

        produtoVariante.setTamanho(
                dto.tamanho()
        );

        produtoVariante.setQuantidade(
                dto.quantidade()
        );

        return produtoVariante;
    }
}