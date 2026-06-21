package com.testesII.backend.service;

import com.testesII.backend.entity.Produto;
import com.testesII.backend.entity.ProdutoVariante;
import com.testesII.backend.enums.Categoria;
import com.testesII.backend.enums.Classificacao;
import com.testesII.backend.enums.ProdutoTamanho;
import com.testesII.backend.repository.ProdutoVarianteRepository;
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
@DisplayName("Testes da classe ProdutoVarianteService")
class ProdutoVarianteServiceTest {

	@Mock
	private ProdutoVarianteRepository produtoVarianteRepository;

	@InjectMocks
	private ProdutoVarianteService produtoVarianteService;

	private ProdutoVariante varianteTeste;
	private Produto produtoTeste;

	@BeforeEach
	void setUp() {

		produtoTeste = new Produto();
		produtoTeste.setIdProduto(1L);
		produtoTeste.setNome("Camiseta");
		produtoTeste.setMarca("Nike");
		produtoTeste.setClassificacao(Classificacao.NOVO);
		produtoTeste.setCategoria(Categoria.CAMISAS);
		produtoTeste.setDescricao("Camiseta");
		produtoTeste.setPreco(new BigDecimal("79.90"));
		produtoTeste.setImagemUrl("http://exemplo.com/img.jpg");

		varianteTeste = new ProdutoVariante();
		varianteTeste.setId(1L);
		varianteTeste.setProduto(produtoTeste);
		varianteTeste.setTamanho(ProdutoTamanho.M);
		varianteTeste.setQuantidade(50);
	}

	@Test
	@DisplayName("Deve listar todas as variantes com sucesso")
	void testListarTodasVariantes() {

		List<ProdutoVariante> variantes = List.of(varianteTeste);
		when(produtoVarianteRepository.findAll()).thenReturn(variantes);

		List<ProdutoVariante> resultado = produtoVarianteService.listarTodas();

		assertNotNull(resultado);
		assertEquals(1, resultado.size());
		assertEquals(ProdutoTamanho.M, resultado.get(0).getTamanho());
		verify(produtoVarianteRepository).findAll();
	}

	@Test
	@DisplayName("Deve retornar lista vazia quando não há variantes")
	void testListarTodasVariantesVazio() {

		when(produtoVarianteRepository.findAll()).thenReturn(List.of());

		List<ProdutoVariante> resultado = produtoVarianteService.listarTodas();

		assertNotNull(resultado);
		assertTrue(resultado.isEmpty());
	}

	@Test
	@DisplayName("Deve buscar uma variante por ID com sucesso")
	void testBuscarPorIdSucesso() {

		Long idVariante = 1L;
		when(produtoVarianteRepository.findById(idVariante))
			.thenReturn(Optional.of(varianteTeste));

		Optional<ProdutoVariante> resultado = produtoVarianteService.buscarPorId(idVariante);

		assertTrue(resultado.isPresent());
		assertEquals(ProdutoTamanho.M, resultado.get().getTamanho());
		assertEquals(50, resultado.get().getQuantidade());
		verify(produtoVarianteRepository).findById(idVariante);
	}

	@Test
	@DisplayName("Deve retornar Optional vazio quando variante não existe")
	void testBuscarPorIdNaoEncontrado() {

		Long idInexistente = 999L;
		when(produtoVarianteRepository.findById(idInexistente))
			.thenReturn(Optional.empty());

		Optional<ProdutoVariante> resultado = produtoVarianteService.buscarPorId(idInexistente);

		assertTrue(resultado.isEmpty());
	}

	@Test
	@DisplayName("Deve salvar uma variante com sucesso")
	void testSalvarVariante() {

		when(produtoVarianteRepository.save(any(ProdutoVariante.class)))
			.thenReturn(varianteTeste);

		ProdutoVariante resultado = produtoVarianteService.salvar(varianteTeste);

		assertNotNull(resultado);
		assertEquals(ProdutoTamanho.M, resultado.getTamanho());
		assertEquals(50, resultado.getQuantidade());
		verify(produtoVarianteRepository).save(any(ProdutoVariante.class));
	}

	@Test
	@DisplayName("Deve salvar variante com argumentos corretos")
	void testSalvarVarianteArgumentosCorretos() {

		when(produtoVarianteRepository.save(any(ProdutoVariante.class)))
			.thenReturn(varianteTeste);

		produtoVarianteService.salvar(varianteTeste);

		ArgumentCaptor<ProdutoVariante> captor = ArgumentCaptor.forClass(ProdutoVariante.class);
		verify(produtoVarianteRepository).save(captor.capture());

		ProdutoVariante varianteCapturada = captor.getValue();
		assertEquals(ProdutoTamanho.M, varianteCapturada.getTamanho());
		assertEquals(50, varianteCapturada.getQuantidade());
	}

	@Test
	@DisplayName("Deve salvar múltiplas variantes")
	void testSalvarMultiplasVariantes() {

		ProdutoVariante varianteG = new ProdutoVariante(2L, produtoTeste, ProdutoTamanho.G, 30);
		ProdutoVariante varianteGG = new ProdutoVariante(3L, produtoTeste, ProdutoTamanho.GG, 20);

		when(produtoVarianteRepository.save(any(ProdutoVariante.class)))
			.thenReturn(varianteTeste)
			.thenReturn(varianteG)
			.thenReturn(varianteGG);

		produtoVarianteService.salvar(varianteTeste);
		produtoVarianteService.salvar(varianteG);
		produtoVarianteService.salvar(varianteGG);

		verify(produtoVarianteRepository, times(3)).save(any(ProdutoVariante.class));
	}

	@Test
	@DisplayName("Deve deletar uma variante com sucesso")
	void testDeletarVarianteSucesso() {

		Long idVariante = 1L;
		when(produtoVarianteRepository.existsById(idVariante)).thenReturn(true);

		assertDoesNotThrow(() -> produtoVarianteService.deletar(idVariante));

		verify(produtoVarianteRepository).existsById(idVariante);
		verify(produtoVarianteRepository).deleteById(idVariante);
	}

	@Test
	@DisplayName("Deve lançar exceção ao deletar variante inexistente")
	void testDeletarVarianteInexistente() {

		Long idInexistente = 999L;
		when(produtoVarianteRepository.existsById(idInexistente))
			.thenReturn(false);

		RuntimeException excecao = assertThrows(
			RuntimeException.class,
			() -> produtoVarianteService.deletar(idInexistente)
		);

		assertTrue(excecao.getMessage().contains("não encontrado"));

		verify(produtoVarianteRepository, never()).deleteById(any());
	}

	@Test
	@DisplayName("Deve atualizar uma variante com sucesso")
	void testAtualizarVarianteSucesso() {

		Long idVariante = 1L;

		ProdutoVariante varianteExistente = new ProdutoVariante();
		varianteExistente.setId(idVariante);
		varianteExistente.setTamanho(ProdutoTamanho.M);
		varianteExistente.setQuantidade(50);
		varianteExistente.setProduto(produtoTeste);

		ProdutoVariante varianteAtualizada = new ProdutoVariante();
		varianteAtualizada.setTamanho(ProdutoTamanho.G);
		varianteAtualizada.setQuantidade(100);

		when(produtoVarianteRepository.findById(idVariante))
			.thenReturn(Optional.of(varianteExistente));

		when(produtoVarianteRepository.save(any(ProdutoVariante.class)))
			.thenReturn(varianteAtualizada);

		ProdutoVariante resultado = produtoVarianteService.atualizar(idVariante, varianteAtualizada);

		assertNotNull(resultado);
		assertEquals(ProdutoTamanho.G, resultado.getTamanho());
		assertEquals(100, resultado.getQuantidade());
		verify(produtoVarianteRepository).findById(idVariante);
		verify(produtoVarianteRepository).save(any(ProdutoVariante.class));
	}

	@Test
	@DisplayName("Deve atualizar apenas tamanho e quantidade")
	void testAtualizarApenasQuanidadeETamanho() {

		Long idVariante = 1L;

		ProdutoVariante varianteExistente = new ProdutoVariante();
		varianteExistente.setId(idVariante);
		varianteExistente.setTamanho(ProdutoTamanho.P);
		varianteExistente.setQuantidade(20);
		varianteExistente.setProduto(produtoTeste);

		ProdutoVariante novasDados = new ProdutoVariante();
		novasDados.setTamanho(ProdutoTamanho.GG);
		novasDados.setQuantidade(60);

		when(produtoVarianteRepository.findById(idVariante))
			.thenReturn(Optional.of(varianteExistente));

		when(produtoVarianteRepository.save(any(ProdutoVariante.class)))
			.thenAnswer(invocation -> invocation.getArgument(0));

		ProdutoVariante resultado = produtoVarianteService.atualizar(idVariante, novasDados);

		assertEquals(ProdutoTamanho.GG, resultado.getTamanho());
		assertEquals(60, resultado.getQuantidade());
	}

	@Test
	@DisplayName("Deve lançar exceção ao atualizar variante inexistente")
	void testAtualizarVarianteInexistente() {

		Long idInexistente = 999L;
		when(produtoVarianteRepository.findById(idInexistente))
			.thenReturn(Optional.empty());

		assertThrows(
			RuntimeException.class,
			() -> produtoVarianteService.atualizar(idInexistente, varianteTeste)
		);

		verify(produtoVarianteRepository, never()).save(any());
	}

	@Test
	@DisplayName("Deve aceitar variante com tamanho válido")
	void testVarianteComTamanhoValido() {

		ProdutoVariante variante = new ProdutoVariante();
		variante.setTamanho(ProdutoTamanho.M);
		variante.setQuantidade(10);
		variante.setProduto(produtoTeste);

		when(produtoVarianteRepository.save(any(ProdutoVariante.class)))
			.thenReturn(variante);

		ProdutoVariante resultado = produtoVarianteService.salvar(variante);

		assertNotNull(resultado.getTamanho());
		assertEquals(ProdutoTamanho.M, resultado.getTamanho());
	}

	@Test
	@DisplayName("Deve aceitar quantidade válida")
	void testVarianteComQuantidadeValida() {

		ProdutoVariante variante = new ProdutoVariante();
		variante.setTamanho(ProdutoTamanho.G);
		variante.setQuantidade(100);
		variante.setProduto(produtoTeste);

		when(produtoVarianteRepository.save(any(ProdutoVariante.class)))
			.thenReturn(variante);

		ProdutoVariante resultado = produtoVarianteService.salvar(variante);

		assertEquals(100, resultado.getQuantidade());
		assertTrue(resultado.getQuantidade() >= 0);
	}

	@Test
	@DisplayName("Deve chamar findAll exatamente uma vez")
	void testChamadasRepository() {

		when(produtoVarianteRepository.findAll()).thenReturn(List.of(varianteTeste));

		produtoVarianteService.listarTodas();

		verify(produtoVarianteRepository, times(1)).findAll();
		verify(produtoVarianteRepository, never()).save(any());
	}

	@Test
	@DisplayName("Deve usar verify com atLeast e atMost")
	void testVerifyComMultiplas() {

		when(produtoVarianteRepository.findById(any())).thenReturn(Optional.of(varianteTeste));

		produtoVarianteService.buscarPorId(1L);
		produtoVarianteService.buscarPorId(2L);

		verify(produtoVarianteRepository, atLeast(1)).findById(any());
		verify(produtoVarianteRepository, atMost(2)).findById(any());
	}
}

