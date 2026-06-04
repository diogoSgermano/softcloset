package com.testesII.backend.controller;

import com.testesII.backend.dto.ProdutoVarianteRequestDTO;
import com.testesII.backend.dto.ProdutoVarianteResponseDTO;
import com.testesII.backend.entity.ProdutoVariante;
import com.testesII.backend.service.ProdutoVarianteService;
import com.testesII.backend.service.ProdutoService; // Needed to associate variant with a product
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produto-variantes")
public class ProdutoVarianteController {

    private final ProdutoVarianteService produtoVarianteService;
    private final ProdutoService produtoService; // Inject ProdutoService to handle product association

    public ProdutoVarianteController(ProdutoVarianteService produtoVarianteService, ProdutoService produtoService) {
        this.produtoVarianteService = produtoVarianteService;
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoVarianteResponseDTO>> getAllProdutoVariantes() {
        List<ProdutoVariante> variantes = produtoVarianteService.listarTodas();
        List<ProdutoVarianteResponseDTO> responseDTOS = variantes.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoVarianteResponseDTO> getProdutoVarianteById(@PathVariable Long id) {
        return produtoVarianteService.buscarPorId(id)
                .map(this::convertToResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // This POST method assumes you will provide the parent productId in the request body or as a path variable
    // For simplicity, I'm assuming it will be part of the request body for now, or you can add a path variable.
    // A more robust solution might involve nesting this under /api/produtos/{productId}/variantes
    @PostMapping("/produto/{produtoId}")
    public ResponseEntity<ProdutoVarianteResponseDTO> createProdutoVariante(
            @PathVariable Long produtoId,
            @Valid @RequestBody ProdutoVarianteRequestDTO produtoVarianteRequestDTO) {

        return produtoService.buscarPorId(produtoId)
                .map(produto -> {
                    ProdutoVariante produtoVariante = convertToEntity(produtoVarianteRequestDTO);
                    produtoVariante.setProduto(produto); // Associate with the parent product
                    ProdutoVariante savedVariante = produtoVarianteService.salvar(produtoVariante);
                    return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponseDTO(savedVariante));
                })
                .orElse(ResponseEntity.notFound().build()); // Parent product not found
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProdutoVarianteResponseDTO> updateProdutoVariante(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoVarianteRequestDTO produtoVarianteRequestDTO) {
        try {
            ProdutoVariante produtoVarianteToUpdate = convertToEntity(produtoVarianteRequestDTO);
            ProdutoVariante updatedVariante = produtoVarianteService.atualizar(id, produtoVarianteToUpdate);
            return ResponseEntity.ok(convertToResponseDTO(updatedVariante));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProdutoVariante(@PathVariable Long id) {
        try {
            produtoVarianteService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private ProdutoVarianteResponseDTO convertToResponseDTO(ProdutoVariante produtoVariante) {
        return new ProdutoVarianteResponseDTO(
                produtoVariante.getId(),
                produtoVariante.getTamanho(),
                produtoVariante.getQuantidade()
        );
    }

    private ProdutoVariante convertToEntity(ProdutoVarianteRequestDTO produtoVarianteRequestDTO) {
        ProdutoVariante produtoVariante = new ProdutoVariante();
        produtoVariante.setTamanho(produtoVarianteRequestDTO.getTamanho());
        produtoVariante.setQuantidade(produtoVarianteRequestDTO.getQuantidade());
        // The 'produto' field is not set here as it's handled in the controller methods (e.g., POST)
        return produtoVariante;
    }
}
