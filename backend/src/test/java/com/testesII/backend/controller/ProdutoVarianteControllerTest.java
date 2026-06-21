package com.testesII.backend.controller;

import com.testesII.backend.dto.ProdutoVarianteResponseDTO;
import com.testesII.backend.dto.ProdutoVarianteRequestDTO;
import com.testesII.backend.entity.Produto;
import com.testesII.backend.entity.ProdutoVariante;
import com.testesII.backend.enums.Categoria;
import com.testesII.backend.enums.Classificacao;
import com.testesII.backend.enums.ProdutoTamanho;
import com.testesII.backend.service.ProdutoService;
import com.testesII.backend.service.ProdutoVarianteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes da classe ProdutoVarianteController")
class ProdutoVarianteControllerTest {

	@Mock
	private ProdutoVarianteService produtoVarianteService;

	@Mock
	private ProdutoService produtoService;

	@InjectMocks
	private ProdutoVarianteController produtoVarianteController;

	private Produto produtoTeste;
	private ProdutoVariante varianteTeste;

	@BeforeEach
	void setUp() {

		produtoTeste = new Produto();
		produtoTeste.setIdProduto(1L);
		produtoTeste.setNome("Camiseta");
		produtoTeste.setMarca("Nike");
		produtoTeste.setClassificacao(Classificacao.NOVO);
		produtoTeste.setCategoria(Categoria.CAMISAS);
		produtoTeste.setDescricao("Camiseta básica");
		produtoTeste.setPreco(new BigDecimal("79.90"));
		produtoTeste.setImagemUrl("https://exemplo.com/img.jpg");

		varianteTeste = new ProdutoVariante();
		varianteTeste.setId(1L);
		varianteTeste.setProduto(produtoTeste);
		varianteTeste.setTamanho(ProdutoTamanho.M);
		varianteTeste.setQuantidade(50);
	}

	@Test
	@DisplayName("Deve listar todas as variantes com status 200 OK")
	void testGetAllVariantes() {

		List<ProdutoVariante> variantes = List.of(varianteTeste);
		when(produtoVarianteService.listarTodas()).thenReturn(variantes);

		ResponseEntity<List<ProdutoVarianteResponseDTO>> resposta =
			produtoVarianteController.getAllProdutoVariantes();

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertNotNull(resposta.getBody());
		assertEquals(1, resposta.getBody().size());
		verify(produtoVarianteService).listarTodas();
	}

	@Test
	@DisplayName("Deve retornar lista vazia com status 200 OK")
	void testGetAllVariantesVazio() {

		when(produtoVarianteService.listarTodas()).thenReturn(List.of());

		ResponseEntity<List<ProdutoVarianteResponseDTO>> resposta =
			produtoVarianteController.getAllProdutoVariantes();

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertTrue(resposta.getBody().isEmpty());
	}

	@Test
	@DisplayName("Deve retornar variante por ID com status 200 OK")
	void testGetVarianteByIdSucesso() {

		Long idVariante = 1L;
		when(produtoVarianteService.buscarPorId(idVariante))
			.thenReturn(Optional.of(varianteTeste));

		ResponseEntity<ProdutoVarianteResponseDTO> resposta =
			produtoVarianteController.getProdutoVarianteById(idVariante);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertNotNull(resposta.getBody());
		assertEquals(ProdutoTamanho.M, resposta.getBody().tamanho());
		assertEquals(50, resposta.getBody().quantidade());
		verify(produtoVarianteService).buscarPorId(idVariante);
	}

	@Test
	@DisplayName("Deve retornar 404 NOT_FOUND quando variante não existe")
	void testGetVarianteByIdNaoEncontrado() {

		Long idInexistente = 999L;
		when(produtoVarianteService.buscarPorId(idInexistente))
			.thenReturn(Optional.empty());

		ResponseEntity<ProdutoVarianteResponseDTO> resposta =
			produtoVarianteController.getProdutoVarianteById(idInexistente);

		assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
		assertNull(resposta.getBody());
	}

	@Test
	@DisplayName("Deve criar variante para produto existente com status 201 CREATED")
	void testCreateVarianteParaProdutoExistente() {

		Long idProduto = 1L;

		when(produtoService.buscarPorId(idProduto))
			.thenReturn(Optional.of(produtoTeste));

		when(produtoVarianteService.salvar(any(ProdutoVariante.class)))
			.thenReturn(varianteTeste);

		ProdutoVarianteRequestDTO requestDTO = new ProdutoVarianteRequestDTO(ProdutoTamanho.M, 50);

		ResponseEntity<ProdutoVarianteResponseDTO> resposta =
			produtoVarianteController.createProdutoVariante(idProduto, requestDTO);

		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertNotNull(resposta.getBody());
		verify(produtoService).buscarPorId(idProduto);
		verify(produtoVarianteService).salvar(any(ProdutoVariante.class));
	}

	@Test
	@DisplayName("Deve retornar 404 ao criar variante para produto inexistente")
	void testCreateVarianteParaProdutoInexistente() {

		Long idProdutoInexistente = 999L;

		when(produtoService.buscarPorId(idProdutoInexistente))
			.thenReturn(Optional.empty());

		ResponseEntity<ProdutoVarianteResponseDTO> resposta =
			produtoVarianteController.createProdutoVariante(idProdutoInexistente, null);

		assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
		verify(produtoVarianteService, never()).salvar(any());
	}

	@Test
	@DisplayName("Deve criar múltiplas variantes para o mesmo produto")
	void testCreateMultiplasVariantesParaMesmoProduto() {

		Long idProduto = 1L;

		ProdutoVariante varianteG = new ProdutoVariante();
		varianteG.setId(2L);
		varianteG.setTamanho(ProdutoTamanho.G);
		varianteG.setQuantidade(30);
		varianteG.setProduto(produtoTeste);

		when(produtoService.buscarPorId(idProduto))
			.thenReturn(Optional.of(produtoTeste));

		when(produtoVarianteService.salvar(any(ProdutoVariante.class)))
			.thenReturn(varianteTeste)
			.thenReturn(varianteG);

		ProdutoVarianteRequestDTO requestDTO1 = new ProdutoVarianteRequestDTO(ProdutoTamanho.M, 50);
		ProdutoVarianteRequestDTO requestDTO2 = new ProdutoVarianteRequestDTO(ProdutoTamanho.G, 30);

		ResponseEntity<ProdutoVarianteResponseDTO> resposta1 =
			produtoVarianteController.createProdutoVariante(idProduto, requestDTO1);
		ResponseEntity<ProdutoVarianteResponseDTO> resposta2 =
			produtoVarianteController.createProdutoVariante(idProduto, requestDTO2);

		assertEquals(HttpStatus.CREATED, resposta1.getStatusCode());
		assertEquals(HttpStatus.CREATED, resposta2.getStatusCode());
		verify(produtoVarianteService, times(2)).salvar(any(ProdutoVariante.class));
	}

	@Test
	@DisplayName("Deve atualizar variante com status 200 OK")
	void testUpdateVarianteSucesso() {

		Long idVariante = 1L;
		ProdutoVariante varianteAtualizada = new ProdutoVariante();
		varianteAtualizada.setTamanho(ProdutoTamanho.G);
		varianteAtualizada.setQuantidade(100);

		when(produtoVarianteService.atualizar(eq(idVariante), any(ProdutoVariante.class)))
			.thenReturn(varianteAtualizada);

		ProdutoVarianteRequestDTO requestDTO = new ProdutoVarianteRequestDTO(ProdutoTamanho.G, 100);

		ResponseEntity<ProdutoVarianteResponseDTO> resposta =
			produtoVarianteController.updateProdutoVariante(idVariante, requestDTO);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertNotNull(resposta.getBody());
		assertEquals(ProdutoTamanho.G, resposta.getBody().tamanho());
		assertEquals(100, resposta.getBody().quantidade());
	}

	@Test
	@DisplayName("Deve retornar 404 ao atualizar variante inexistente")
	void testUpdateVarianteNaoEncontrada() {

		Long idInexistente = 999L;

		when(produtoVarianteService.atualizar(eq(idInexistente), any(ProdutoVariante.class)))
			.thenThrow(new RuntimeException("Variante não encontrada"));

		ProdutoVarianteRequestDTO requestDTO = new ProdutoVarianteRequestDTO(ProdutoTamanho.G, 100);

		ResponseEntity<ProdutoVarianteResponseDTO> resposta =
			produtoVarianteController.updateProdutoVariante(idInexistente, requestDTO);

		assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
	}

	@Test
	@DisplayName("Deve atualizar quantidade da variante")
	void testUpdateQuantidadeVariante() {

		Long idVariante = 1L;
		ProdutoVariante varianteAtualizada = new ProdutoVariante();
		varianteAtualizada.setTamanho(ProdutoTamanho.M);
		varianteAtualizada.setQuantidade(200);

		when(produtoVarianteService.atualizar(eq(idVariante), any(ProdutoVariante.class)))
			.thenReturn(varianteAtualizada);

		ProdutoVarianteRequestDTO requestDTO = new ProdutoVarianteRequestDTO(ProdutoTamanho.M, 200);

		ResponseEntity<ProdutoVarianteResponseDTO> resposta =
			produtoVarianteController.updateProdutoVariante(idVariante, requestDTO);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(200, resposta.getBody().quantidade());
	}

	@Test
	@DisplayName("Deve deletar variante com status 204 NO_CONTENT")
	void testDeleteVarianteSucesso() {

		Long idVariante = 1L;
		doNothing().when(produtoVarianteService).deletar(idVariante);

		ResponseEntity<Void> resposta = produtoVarianteController.deleteProdutoVariante(idVariante);

		assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
		verify(produtoVarianteService).deletar(idVariante);
	}

	@Test
	@DisplayName("Deve retornar 404 ao deletar variante inexistente")
	void testDeleteVarianteNaoEncontrada() {

		Long idInexistente = 999L;
		doThrow(new RuntimeException("Variante não encontrada"))
			.when(produtoVarianteService).deletar(idInexistente);

		ResponseEntity<Void> resposta = produtoVarianteController.deleteProdutoVariante(idInexistente);

		assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
	}

	@Test
	@DisplayName("Deve converter ProdutoVariante para ResponseDTO corretamente")
	void testConversaoVarianteParaDTO() {

		List<ProdutoVariante> variantes = List.of(varianteTeste);
		when(produtoVarianteService.listarTodas()).thenReturn(variantes);

		ResponseEntity<List<ProdutoVarianteResponseDTO>> resposta =
			produtoVarianteController.getAllProdutoVariantes();

		ProdutoVarianteResponseDTO dto = resposta.getBody().get(0);
		assertEquals(1L, dto.id());
		assertEquals(ProdutoTamanho.M, dto.tamanho());
		assertEquals(50, dto.quantidade());
	}

	@Test
	@DisplayName("Deve validar ID do produto antes de criar variante")
	void testValidarIdProdutoAntesDeCriar() {

		Long idProduto = 1L;

		when(produtoService.buscarPorId(idProduto))
			.thenReturn(Optional.of(produtoTeste));

		when(produtoVarianteService.salvar(any(ProdutoVariante.class)))
			.thenReturn(varianteTeste);

		ProdutoVarianteRequestDTO requestDTO = new ProdutoVarianteRequestDTO(ProdutoTamanho.M, 50);

		ResponseEntity<ProdutoVarianteResponseDTO> resposta =
			produtoVarianteController.createProdutoVariante(idProduto, requestDTO);

		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());

		verify(produtoService).buscarPorId(idProduto);
	}

	@Test
	@DisplayName("Deve chamar serviço com argumentos corretos")
	void testChamadasServicoArgumentosCorretos() {

		Long idVariante = 1L;
		when(produtoVarianteService.buscarPorId(idVariante))
			.thenReturn(Optional.of(varianteTeste));

		produtoVarianteController.getProdutoVarianteById(idVariante);

		verify(produtoVarianteService).buscarPorId(eq(idVariante));
		verify(produtoVarianteService, times(1)).buscarPorId(idVariante);
	}

	@Test
	@DisplayName("Não deve fazer chamadas extras ao serviço")
	void testNaoChamadasExtras() {

		when(produtoVarianteService.listarTodas()).thenReturn(List.of());

		produtoVarianteController.getAllProdutoVariantes();

		verify(produtoVarianteService, times(1)).listarTodas();
		verify(produtoVarianteService, never()).buscarPorId(any());
		verify(produtoVarianteService, never()).salvar(any());
	}

	@Test
	@DisplayName("Validar status codes HTTP corretos")
	void testStatusCodesHTTP() {

		when(produtoVarianteService.listarTodas()).thenReturn(List.of(varianteTeste));

		ResponseEntity<List<ProdutoVarianteResponseDTO>> resposta =
			produtoVarianteController.getAllProdutoVariantes();

		assertTrue(resposta.getStatusCode().is2xxSuccessful());
		assertEquals(200, resposta.getStatusCode().value());
	}

	@Test
	@DisplayName("Validar diferentes status codes")
	void testDiferentesStatusCodes() {

		Long idExistente = 1L;
		Long idInexistente = 999L;

		when(produtoVarianteService.buscarPorId(idExistente))
			.thenReturn(Optional.of(varianteTeste));

		when(produtoVarianteService.buscarPorId(idInexistente))
			.thenReturn(Optional.empty());

		ResponseEntity<ProdutoVarianteResponseDTO> resposta1 =
			produtoVarianteController.getProdutoVarianteById(idExistente);
		ResponseEntity<ProdutoVarianteResponseDTO> resposta2 =
			produtoVarianteController.getProdutoVarianteById(idInexistente);

		assertEquals(200, resposta1.getStatusCode().value());
		assertEquals(404, resposta2.getStatusCode().value());
	}
}

