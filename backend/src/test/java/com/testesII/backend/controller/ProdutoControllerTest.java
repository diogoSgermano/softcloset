package com.testesII.backend.controller;

import com.testesII.backend.dto.ProdutoResponseDTO;
import com.testesII.backend.entity.Produto;
import com.testesII.backend.entity.ProdutoVariante;
import com.testesII.backend.enums.Categoria;
import com.testesII.backend.enums.Classificacao;
import com.testesII.backend.enums.ProdutoTamanho;
import com.testesII.backend.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.testesII.backend.dto.ProdutoRequestDTO;
import com.testesII.backend.dto.ProdutoVarianteRequestDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes da classe ProdutoController")
class ProdutoControllerTest {

	@Mock
	private ProdutoService produtoService;

	@InjectMocks
	private ProdutoController produtoController;

	private Produto produtoTeste;
	private ProdutoVariante varianteTeste;

	@BeforeEach
	void setUp() {

		produtoTeste = new Produto();
		produtoTeste.setIdProduto(1L);
		produtoTeste.setNome("Camiseta Azul");
		produtoTeste.setMarca("Nike");
		produtoTeste.setClassificacao(Classificacao.NOVO);
		produtoTeste.setCategoria(Categoria.CAMISAS);
		produtoTeste.setDescricao("Camiseta azul de algodão");
		produtoTeste.setPreco(new BigDecimal("89.90"));
		produtoTeste.setImagemUrl("https://exemplo.com/camiseta-azul.jpg");

		varianteTeste = new ProdutoVariante();
		varianteTeste.setId(1L);
		varianteTeste.setProduto(produtoTeste);
		varianteTeste.setTamanho(ProdutoTamanho.M);
		varianteTeste.setQuantidade(50);

		produtoTeste.setTamanhos(List.of(varianteTeste));
	}

	@Test
	@DisplayName("Deve listar todos os produtos com status 200 OK")
	void testGetAllProdutos() {

		List<Produto> produtos = List.of(produtoTeste);
		when(produtoService.listarTodos()).thenReturn(produtos);

		ResponseEntity<List<ProdutoResponseDTO>> resposta = produtoController.getAllProdutos();

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertNotNull(resposta.getBody());
		assertEquals(1, resposta.getBody().size());
		verify(produtoService).listarTodos();
	}

	@Test
	@DisplayName("Deve retornar lista vazia com status 200 OK")
	void testGetAllProdutosVazio() {

		when(produtoService.listarTodos()).thenReturn(List.of());

		ResponseEntity<List<ProdutoResponseDTO>> resposta = produtoController.getAllProdutos();

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertTrue(resposta.getBody().isEmpty());
	}

	@Test
	@DisplayName("Deve converter Produto para ProdutoResponseDTO corretamente")
	void testConversaoProdutoParaDTO() {

		List<Produto> produtos = List.of(produtoTeste);
		when(produtoService.listarTodos()).thenReturn(produtos);

		ResponseEntity<List<ProdutoResponseDTO>> resposta = produtoController.getAllProdutos();

		ProdutoResponseDTO dto = resposta.getBody().get(0);
		assertEquals("Camiseta Azul", dto.nome());
		assertEquals("Nike", dto.marca());
		assertEquals(Categoria.CAMISAS, dto.categoria());
		assertEquals(new BigDecimal("89.90"), dto.preco());
	}

	@Test
	@DisplayName("Deve retornar produto por ID com status 200 OK")
	void testGetProdutoByIdSucesso() {

		Long idProduto = 1L;
		when(produtoService.buscarPorId(idProduto))
			.thenReturn(Optional.of(produtoTeste));

		ResponseEntity<ProdutoResponseDTO> resposta = produtoController.getProdutoById(idProduto);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertNotNull(resposta.getBody());
		assertEquals("Camiseta Azul", resposta.getBody().nome());
		verify(produtoService).buscarPorId(idProduto);
	}

	@Test
	@DisplayName("Deve retornar 404 NOT_FOUND quando produto não existe")
	void testGetProdutoByIdNaoEncontrado() {

		Long idInexistente = 999L;
		when(produtoService.buscarPorId(idInexistente))
			.thenReturn(Optional.empty());

		ResponseEntity<ProdutoResponseDTO> resposta = produtoController.getProdutoById(idInexistente);

		assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
		assertNull(resposta.getBody());
	}

	@Test
	@DisplayName("Deve incluir variantes no DTO retornado")
	void testGetProdutoByIdComVariantes() {

		when(produtoService.buscarPorId(1L))
			.thenReturn(Optional.of(produtoTeste));

		ResponseEntity<ProdutoResponseDTO> resposta = produtoController.getProdutoById(1L);

		assertNotNull(resposta.getBody().tamanhos());
		assertEquals(1, resposta.getBody().tamanhos().size());
	}

	@Test
	@DisplayName("Deve criar um novo produto com status 201 CREATED")
	void testCreateProdutoSucesso() {

		when(produtoService.salvar(any(Produto.class)))
			.thenReturn(produtoTeste);

		ProdutoRequestDTO requestDTO = new ProdutoRequestDTO(
			"Camiseta Azul",
			"Nike",
			Classificacao.NOVO,
			Categoria.CAMISAS,
			"Camiseta azul de algodão",
			new BigDecimal("89.90"),
			"https://exemplo.com/camiseta-azul.jpg",
			List.of(new ProdutoVarianteRequestDTO(ProdutoTamanho.M, 50))
		);

		ResponseEntity<ProdutoResponseDTO> resposta = produtoController.createProduto(requestDTO);

		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertNotNull(resposta.getBody());
		assertEquals("Camiseta Azul", resposta.getBody().nome());
	}

	@Test
	@DisplayName("Deve atualizar um produto com status 200 OK")
	void testUpdateProdutoSucesso() {

		Long idProduto = 1L;
		Produto produtoAtualizado = new Produto();
		produtoAtualizado.setIdProduto(idProduto);
		produtoAtualizado.setNome("Camiseta Vermelha");
		produtoAtualizado.setPreco(new BigDecimal("99.90"));

		when(produtoService.atualizar(eq(idProduto), any(Produto.class)))
			.thenReturn(produtoAtualizado);

		ProdutoRequestDTO requestDTO = new ProdutoRequestDTO(
			"Camiseta Vermelha",
			"Nike",
			Classificacao.NOVO,
			Categoria.CAMISAS,
			"Camiseta vermelha de algodão",
			new BigDecimal("99.90"),
			"https://exemplo.com/camiseta-vermelha.jpg",
			List.of(new ProdutoVarianteRequestDTO(ProdutoTamanho.M, 50))
		);

		ResponseEntity<ProdutoResponseDTO> resposta = produtoController.updateProduto(idProduto, requestDTO);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertNotNull(resposta.getBody());
	}

	@Test
	@DisplayName("Deve retornar 404 ao atualizar produto inexistente")
	void testUpdateProdutoNaoEncontrado() {

		Long idInexistente = 999L;
		when(produtoService.atualizar(eq(idInexistente), any(Produto.class)))
			.thenThrow(new RuntimeException("Produto não encontrado"));

		ProdutoRequestDTO requestDTO = new ProdutoRequestDTO(
			"Camiseta Inexistente",
			"Nike",
			Classificacao.NOVO,
			Categoria.CAMISAS,
			"Produto inexistente",
			new BigDecimal("99.90"),
			"https://exemplo.com/imagem.jpg",
			List.of(new ProdutoVarianteRequestDTO(ProdutoTamanho.M, 50))
		);

		ResponseEntity<ProdutoResponseDTO> resposta = produtoController.updateProduto(idInexistente, requestDTO);

		assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
	}

	@Test
	@DisplayName("Deve deletar um produto com status 204 NO_CONTENT")
	void testDeleteProdutoSucesso() {

		Long idProduto = 1L;
		doNothing().when(produtoService).deletar(idProduto);

		ResponseEntity<Void> resposta = produtoController.deleteProduto(idProduto);

		assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
		verify(produtoService).deletar(idProduto);
	}

	@Test
	@DisplayName("Deve retornar 404 ao deletar produto inexistente")
	void testDeleteProdutoNaoEncontrado() {

		Long idInexistente = 999L;
		doThrow(new RuntimeException("Produto não encontrado"))
			.when(produtoService).deletar(idInexistente);

		ResponseEntity<Void> resposta = produtoController.deleteProduto(idInexistente);

		assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
	}

	@Test
	@DisplayName("Deve buscar produtos por nome com sucesso")
	void testSearchProdutosPorNomeSucesso() {

		String termo = "Camiseta";
		List<Produto> produtos = List.of(produtoTeste);
		when(produtoService.buscarPorNome(termo))
			.thenReturn(produtos);

		ResponseEntity<List<ProdutoResponseDTO>> resposta = produtoController.searchProdutos(termo);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(1, resposta.getBody().size());
		verify(produtoService).buscarPorNome(termo);
	}

	@Test
	@DisplayName("Deve retornar lista vazia ao buscar nome inexistente")
	void testSearchProdutosPorNomeVazio() {

		String termoInexistente = "XYZ_INEXISTENTE";
		when(produtoService.buscarPorNome(termoInexistente))
			.thenReturn(List.of());

		ResponseEntity<List<ProdutoResponseDTO>> resposta = produtoController.searchProdutos(termoInexistente);

		assertTrue(resposta.getBody().isEmpty());
	}

	@Test
	@DisplayName("Deve filtrar produtos por classificação NOVO")
	void testGetProdutosByClassificacaoNovo() {

		Classificacao classificacao = Classificacao.NOVO;
		List<Produto> produtos = List.of(produtoTeste);
		when(produtoService.filtrarPorClassificacao(classificacao))
			.thenReturn(produtos);

		ResponseEntity<List<ProdutoResponseDTO>> resposta =
			produtoController.getProdutosByClassificacao(classificacao);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(1, resposta.getBody().size());
		verify(produtoService).filtrarPorClassificacao(classificacao);
	}

	@Test
	@DisplayName("Deve retornar lista vazia ao filtrar com nenhum resultado")
	void testGetProdutosByClassificacaoVazio() {

		when(produtoService.filtrarPorClassificacao(any(Classificacao.class)))
			.thenReturn(List.of());

		ResponseEntity<List<ProdutoResponseDTO>> resposta =
			produtoController.getProdutosByClassificacao(Classificacao.DESTAQUE);

		assertTrue(resposta.getBody().isEmpty());
	}

	@Test
	@DisplayName("Validar status codes HTTP corretos")
	void testStatusCodesHTTP() {

		when(produtoService.listarTodos()).thenReturn(List.of(produtoTeste));

		ResponseEntity<List<ProdutoResponseDTO>> resposta = produtoController.getAllProdutos();

		assertNotNull(resposta.getStatusCode());
		assertTrue(resposta.getStatusCode().is2xxSuccessful());
		assertEquals(200, resposta.getStatusCode().value());
	}

	@Test
	@DisplayName("Deve chamar serviço com argumentos corretos")
	void testChamadasServicoArgumentosCorretos() {

		String nomeBuscado = "Camiseta";
		when(produtoService.buscarPorNome(nomeBuscado))
			.thenReturn(List.of(produtoTeste));

		produtoController.searchProdutos(nomeBuscado);

		verify(produtoService).buscarPorNome(eq(nomeBuscado));
		verify(produtoService, times(1)).buscarPorNome(nomeBuscado);
	}

	@Test
	@DisplayName("Não deve fazer chamadas extras ao serviço")
	void testNaoChamadasExtras() {

		when(produtoService.listarTodos()).thenReturn(List.of());

		produtoController.getAllProdutos();

		verify(produtoService, times(1)).listarTodos();

		verify(produtoService, never()).buscarPorId(any());
		verify(produtoService, never()).salvar(any());
	}
}

