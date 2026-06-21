package com.testesII.backend.service;

import com.testesII.backend.entity.Produto;
import com.testesII.backend.entity.ProdutoVariante;
import com.testesII.backend.enums.Categoria;
import com.testesII.backend.enums.Classificacao;
import com.testesII.backend.enums.ProdutoTamanho;
import com.testesII.backend.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes da classe ProdutoService")
class ProdutoServiceTest {

	@Mock
	private ProdutoRepository produtoRepository;

	@InjectMocks
	private ProdutoService produtoService;

	private Produto produtoTeste;
	private ProdutoVariante varianteTeste;

	@BeforeEach
	void setUp() {

		produtoTeste = new Produto();
		produtoTeste.setIdProduto(1L);
		produtoTeste.setNome("Camiseta Básica");
		produtoTeste.setMarca("Nike");
		produtoTeste.setClassificacao(Classificacao.NOVO);
		produtoTeste.setCategoria(Categoria.CAMISAS);
		produtoTeste.setDescricao("Camiseta de algodão 100%");
		produtoTeste.setPreco(new BigDecimal("79.90"));
		produtoTeste.setImagemUrl("https://exemplo.com/camiseta.jpg");

		varianteTeste = new ProdutoVariante();
		varianteTeste.setId(1L);
		varianteTeste.setProduto(produtoTeste);
		varianteTeste.setTamanho(ProdutoTamanho.M);
		varianteTeste.setQuantidade(10);

		produtoTeste.setTamanhos(List.of(varianteTeste));
	}

	@Test
	@DisplayName("Deve listar todos os produtos com sucesso")
	void testListarTodosProdutos() {

		List<Produto> produtosEsperados = List.of(produtoTeste);
		when(produtoRepository.findAll()).thenReturn(produtosEsperados);

		List<Produto> resultado = produtoService.listarTodos();

		assertNotNull(resultado, "O resultado não deve ser nulo");
		assertEquals(1, resultado.size(), "Deve retornar 1 produto");
		assertEquals("Camiseta Básica", resultado.get(0).getNome(), "Nome do produto deve corresponder");

		verify(produtoRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("Deve retornar lista vazia quando não há produtos")
	void testListarTodosProdutosListaVazia() {

		when(produtoRepository.findAll()).thenReturn(List.of());

		List<Produto> resultado = produtoService.listarTodos();

		assertNotNull(resultado, "O resultado não deve ser nulo");
		assertTrue(resultado.isEmpty(), "A lista deve estar vazia");
		verify(produtoRepository).findAll();
	}

	@Test
	@DisplayName("Deve buscar um produto por ID com sucesso")
	void testBuscarPorIdSucesso() {

		Long idBuscado = 1L;
		when(produtoRepository.findById(idBuscado))
			.thenReturn(Optional.of(produtoTeste));

		Optional<Produto> resultado = produtoService.buscarPorId(idBuscado);

		assertTrue(resultado.isPresent(), "Deve encontrar o produto");
		assertEquals("Camiseta Básica", resultado.get().getNome());
		verify(produtoRepository).findById(idBuscado);
	}

	@Test
	@DisplayName("Deve retornar Optional vazio quando produto não existe")
	void testBuscarPorIdNaoEncontrado() {

		Long idBuscado = 999L;
		when(produtoRepository.findById(idBuscado))
			.thenReturn(Optional.empty());

		Optional<Produto> resultado = produtoService.buscarPorId(idBuscado);

		assertTrue(resultado.isEmpty(), "Não deve encontrar o produto");
		verify(produtoRepository).findById(idBuscado);
	}

	@Test
	@DisplayName("Deve salvar um produto com sucesso")
	void testSalvarProduto() {

		when(produtoRepository.save(any(Produto.class)))
			.thenReturn(produtoTeste);

		Produto resultado = produtoService.salvar(produtoTeste);

		assertNotNull(resultado, "O produto salvo não deve ser nulo");
		assertEquals("Camiseta Básica", resultado.getNome());
		assertEquals(new BigDecimal("79.90"), resultado.getPreco());
		verify(produtoRepository).save(any(Produto.class));
	}

	@Test
	@DisplayName("Deve passar argumentos corretos ao salvar")
	void testSalvarComArgumentosCorretos() {

		when(produtoRepository.save(any(Produto.class)))
			.thenReturn(produtoTeste);

		produtoService.salvar(produtoTeste);

		ArgumentCaptor<Produto> captor = ArgumentCaptor.forClass(Produto.class);
		verify(produtoRepository).save(captor.capture());

		Produto produtoCapturado = captor.getValue();
		assertEquals("Camiseta Básica", produtoCapturado.getNome());
		assertEquals(Categoria.CAMISAS, produtoCapturado.getCategoria());
	}

	@Test
	@DisplayName("Deve deletar um produto com sucesso")
	void testDeletarProdutoComSucesso() {

		Long idParaDeletar = 1L;
		when(produtoRepository.existsById(idParaDeletar))
			.thenReturn(true);

		assertDoesNotThrow(() -> produtoService.deletar(idParaDeletar));

		verify(produtoRepository).existsById(idParaDeletar);
		verify(produtoRepository).deleteById(idParaDeletar);
	}

	@Test
	@DisplayName("Deve lançar exceção ao deletar produto inexistente")
	void testDeletarProdutoInexistente() {

		Long idInexistente = 999L;
		when(produtoRepository.existsById(idInexistente))
			.thenReturn(false);

		RuntimeException excecao = assertThrows(
			RuntimeException.class,
			() -> produtoService.deletar(idInexistente),
			"Deve lançar RuntimeException"
		);

		assertTrue(excecao.getMessage().contains("não encontrado"));

		verify(produtoRepository, never()).deleteById(any());
	}

	@Test
	@DisplayName("Deve atualizar um produto com sucesso")
	void testAtualizarProdutoComSucesso() {

		Long idProduto = 1L;
		Produto produtoAtualizado = new Produto();
		produtoAtualizado.setNome("Camiseta Premium");
		produtoAtualizado.setMarca("Adidas");
		produtoAtualizado.setClassificacao(Classificacao.DESTAQUE);
		produtoAtualizado.setCategoria(Categoria.CAMISAS);
		produtoAtualizado.setDescricao("Camiseta premium");
		produtoAtualizado.setPreco(new BigDecimal("129.90"));
		produtoAtualizado.setImagemUrl("https://exemplo.com/nova.jpg");

		Produto produtoExistente = new Produto();
		produtoExistente.setIdProduto(idProduto);
		produtoExistente.setNome("Camiseta Básica");
		produtoExistente.setMarca("Nike");
		produtoExistente.setPreco(new BigDecimal("79.90"));

		when(produtoRepository.findById(idProduto))
			.thenReturn(Optional.of(produtoExistente));

		when(produtoRepository.save(any(Produto.class)))
			.thenReturn(produtoAtualizado);

		Produto resultado = produtoService.atualizar(idProduto, produtoAtualizado);

		assertNotNull(resultado);
		assertEquals("Camiseta Premium", resultado.getNome());
		assertEquals(new BigDecimal("129.90"), resultado.getPreco());
		verify(produtoRepository).findById(idProduto);
		verify(produtoRepository).save(any(Produto.class));
	}

	@Test
	@DisplayName("Deve lançar exceção ao atualizar produto inexistente")
	void testAtualizarProdutoInexistente() {

		Long idInexistente = 999L;
		when(produtoRepository.findById(idInexistente))
			.thenReturn(Optional.empty());

		assertThrows(
			RuntimeException.class,
			() -> produtoService.atualizar(idInexistente, produtoTeste)
		);

		verify(produtoRepository, never()).save(any());
	}

	@Test
	@DisplayName("Deve filtrar produtos por classificação NOVO")
	void testFiltrarPorClassificacaoNovo() {

		Classificacao classificacao = Classificacao.NOVO;
		List<Produto> produtosFiltrados = List.of(produtoTeste);
		when(produtoRepository.findByClassificacao(classificacao))
			.thenReturn(produtosFiltrados);

		List<Produto> resultado = produtoService.filtrarPorClassificacao(classificacao);

		assertNotNull(resultado);
		assertEquals(1, resultado.size());
		assertEquals(Classificacao.NOVO, resultado.get(0).getClassificacao());
		verify(produtoRepository).findByClassificacao(classificacao);
	}

	@Test
	@DisplayName("Deve retornar lista vazia ao filtrar por classificação sem resultados")
	void testFiltrarPorClassificacaoSemResultados() {

		when(produtoRepository.findByClassificacao(any(Classificacao.class)))
			.thenReturn(List.of());

		List<Produto> resultado = produtoService.filtrarPorClassificacao(Classificacao.DESTAQUE);

		assertNotNull(resultado);
		assertTrue(resultado.isEmpty());
	}

	@Test
	@DisplayName("Deve buscar produtos por nome com sucesso")
	void testBuscarPorNome() {

		String nomeBuscado = "Camiseta";
		List<Produto> produtosBuscados = List.of(produtoTeste);
		when(produtoRepository.findByNomeContainingIgnoreCase(nomeBuscado))
			.thenReturn(produtosBuscados);

		List<Produto> resultado = produtoService.buscarPorNome(nomeBuscado);

		assertNotNull(resultado);
		assertEquals(1, resultado.size());
		assertTrue(resultado.get(0).getNome().contains("Camiseta"));
		verify(produtoRepository).findByNomeContainingIgnoreCase(nomeBuscado);
	}

	@Test
	@DisplayName("Deve buscar produtos por nome ignorando maiúsculas/minúsculas")
	void testBuscarPorNomeCaseInsensitive() {

		String nomeBuscado = "CAMISETA";
		when(produtoRepository.findByNomeContainingIgnoreCase(nomeBuscado))
			.thenReturn(List.of(produtoTeste));

		List<Produto> resultado = produtoService.buscarPorNome(nomeBuscado);

		assertFalse(resultado.isEmpty());
		verify(produtoRepository).findByNomeContainingIgnoreCase(nomeBuscado);
	}

	@Test
	@DisplayName("Deve retornar lista vazia ao buscar por nome inexistente")
	void testBuscarPorNomeInexistente() {

		String nomeBuscado = "XYZ_INEXISTENTE";
		when(produtoRepository.findByNomeContainingIgnoreCase(nomeBuscado))
			.thenReturn(List.of());

		List<Produto> resultado = produtoService.buscarPorNome(nomeBuscado);

		assertTrue(resultado.isEmpty());
	}

	@Test
	@DisplayName("Deve executar métodos de modificação com sucesso")
	void testTransacoes() {

		when(produtoRepository.save(any(Produto.class)))
			.thenReturn(produtoTeste);

		Produto resultado = produtoService.salvar(produtoTeste);
		assertNotNull(resultado);
	}
}

