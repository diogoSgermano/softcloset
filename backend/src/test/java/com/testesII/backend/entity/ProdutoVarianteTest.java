package com.testesII.backend.entity;

import com.testesII.backend.enums.Categoria;
import com.testesII.backend.enums.Classificacao;
import com.testesII.backend.enums.ProdutoTamanho;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da entidade ProdutoVariante")
class ProdutoVarianteTest {

	private ProdutoVariante produtoVariante;
	private Produto produto;

	@BeforeEach
	void setUp() {

		produto = new Produto();
		produto.setIdProduto(1L);
		produto.setNome("Camiseta");
		produto.setMarca("Nike");
		produto.setClassificacao(Classificacao.NOVO);
		produto.setCategoria(Categoria.CAMISAS);
		produto.setDescricao("Camiseta básica");
		produto.setPreco(new BigDecimal("79.90"));
		produto.setImagemUrl("https://exemplo.com/img.jpg");

		produtoVariante = new ProdutoVariante();
		produtoVariante.setId(1L);
		produtoVariante.setProduto(produto);
		produtoVariante.setTamanho(ProdutoTamanho.M);
		produtoVariante.setQuantidade(50);
	}

	@Test
	@DisplayName("Deve criar ProdutoVariante com construtor padrão")
	void testConstrutorPadrao() {

		ProdutoVariante novaVariante = new ProdutoVariante();

		assertNotNull(novaVariante);
		assertNull(novaVariante.getId());
		assertNull(novaVariante.getProduto());
		assertNull(novaVariante.getTamanho());
		assertNull(novaVariante.getQuantidade());
	}

	@Test
	@DisplayName("Deve criar ProdutoVariante com construtor completo")
	void testConstrutorCompleto() {

		ProdutoVariante novaVariante = new ProdutoVariante(
			1L,
			produto,
			ProdutoTamanho.G,
			100
		);

		assertEquals(1L, novaVariante.getId());
		assertEquals(produto, novaVariante.getProduto());
		assertEquals(ProdutoTamanho.G, novaVariante.getTamanho());
		assertEquals(100, novaVariante.getQuantidade());
	}

	@Test
	@DisplayName("Deve setar e obter ID da variante")
	void testSetterGetterID() {

		Long idEsperado = 5L;

		produtoVariante.setId(idEsperado);
		Long idObtido = produtoVariante.getId();

		assertEquals(idEsperado, idObtido);
	}

	@Test
	@DisplayName("Deve setar e obter produto da variante")
	void testSetterGetterProduto() {

		Produto novoProduto = new Produto();
		novoProduto.setIdProduto(2L);
		novoProduto.setNome("Outro Produto");

		produtoVariante.setProduto(novoProduto);
		Produto produtoObtido = produtoVariante.getProduto();

		assertEquals(novoProduto, produtoObtido);
		assertEquals(2L, produtoObtido.getIdProduto());
	}

	@Test
	@DisplayName("Deve setar e obter tamanho da variante")
	void testSetterGetterTamanho() {

		ProdutoTamanho tamanhoEsperado = ProdutoTamanho.GG;

		produtoVariante.setTamanho(tamanhoEsperado);
		ProdutoTamanho tamanhoObtido = produtoVariante.getTamanho();

		assertEquals(tamanhoEsperado, tamanhoObtido);
	}

	@Test
	@DisplayName("Deve setar e obter quantidade da variante")
	void testSetterGetterQuantidade() {

		Integer quantidadeEsperada = 150;

		produtoVariante.setQuantidade(quantidadeEsperada);
		Integer quantidadeObtida = produtoVariante.getQuantidade();

		assertEquals(quantidadeEsperada, quantidadeObtida);
	}

	@Test
	@DisplayName("Variante deve ter referência ao produto pai")
	void testRelacionamentoComProduto() {

		assertNotNull(produtoVariante.getProduto());
		assertEquals(1L, produtoVariante.getProduto().getIdProduto());
		assertEquals("Camiseta", produtoVariante.getProduto().getNome());
	}

	@Test
	@DisplayName("Deve permitir atualizar referência ao produto")
	void testAtualizarReferenciaAoProduto() {

		Produto novoProduto = new Produto();
		novoProduto.setIdProduto(2L);
		novoProduto.setNome("Novo Produto");

		produtoVariante.setProduto(novoProduto);

		assertEquals(2L, produtoVariante.getProduto().getIdProduto());
		assertEquals("Novo Produto", produtoVariante.getProduto().getNome());
	}

	@Test
	@DisplayName("Deve considerar variantes iguais se tiverem o mesmo ID")
	void testEqualsComMesmoId() {

		ProdutoVariante variante1 = new ProdutoVariante();
		variante1.setId(1L);
		variante1.setTamanho(ProdutoTamanho.M);

		ProdutoVariante variante2 = new ProdutoVariante();
		variante2.setId(1L);
		variante2.setTamanho(ProdutoTamanho.G);

		assertEquals(variante1, variante2);
	}

	@Test
	@DisplayName("Deve considerar variantes diferentes se tiverem IDs diferentes")
	void testEqualsComIdDiferente() {

		ProdutoVariante variante1 = new ProdutoVariante();
		variante1.setId(1L);

		ProdutoVariante variante2 = new ProdutoVariante();
		variante2.setId(2L);

		assertNotEquals(variante1, variante2);
	}

	@Test
	@DisplayName("Variante deve ser igual a si mesma")
	void testEqualsReflexivo() {

		assertEquals(produtoVariante, produtoVariante);
	}

	@Test
	@DisplayName("Variante não deve ser igual a null")
	void testEqualsNull() {

		assertNotEquals(produtoVariante, null);
	}

	@Test
	@DisplayName("Variante não deve ser igual a outro tipo de objeto")
	void testEqualsOutroTipo() {

		assertNotEquals(produtoVariante, "String");
		assertNotEquals(produtoVariante, 123);
		assertNotEquals(produtoVariante, produto);
	}

	@Test
	@DisplayName("Variantes iguais devem ter mesmo hashCode")
	void testHashCodeIguais() {

		ProdutoVariante variante1 = new ProdutoVariante();
		variante1.setId(1L);

		ProdutoVariante variante2 = new ProdutoVariante();
		variante2.setId(1L);

		assertEquals(variante1.hashCode(), variante2.hashCode());
	}

	@Test
	@DisplayName("Variantes sem ID (null) devem ter hashCode consistente")
	void testHashCodeSemId() {

		ProdutoVariante variante1 = new ProdutoVariante();
		ProdutoVariante variante2 = new ProdutoVariante();

		assertNotNull(variante1.hashCode());
		assertNotNull(variante2.hashCode());
	}

	@Test
	@DisplayName("Deve permitir quantidade zero")
	void testQuantidadeZero() {

		produtoVariante.setQuantidade(0);

		assertEquals(0, produtoVariante.getQuantidade());
	}

	@Test
	@DisplayName("Deve permitir quantidade positiva")
	void testQuantidadePositiva() {

		produtoVariante.setQuantidade(1000);

		assertEquals(1000, produtoVariante.getQuantidade());
	}

	@Test
	@DisplayName("Deve permitir atualizar quantidade")
	void testAtualizarQuantidade() {

		Integer quantidadeInicial = 50;
		Integer novaQuantidade = 100;

		produtoVariante.setQuantidade(quantidadeInicial);

		produtoVariante.setQuantidade(novaQuantidade);

		assertEquals(novaQuantidade, produtoVariante.getQuantidade());
		assertNotEquals(quantidadeInicial, produtoVariante.getQuantidade());
	}

	@Test
	@DisplayName("Deve permitir todos os tamanhos válidos")
	void testTodosTamanhosValidos() {

		produtoVariante.setTamanho(ProdutoTamanho.PP);
		assertEquals(ProdutoTamanho.PP, produtoVariante.getTamanho());

		produtoVariante.setTamanho(ProdutoTamanho.P);
		assertEquals(ProdutoTamanho.P, produtoVariante.getTamanho());

		produtoVariante.setTamanho(ProdutoTamanho.M);
		assertEquals(ProdutoTamanho.M, produtoVariante.getTamanho());

		produtoVariante.setTamanho(ProdutoTamanho.G);
		assertEquals(ProdutoTamanho.G, produtoVariante.getTamanho());

		produtoVariante.setTamanho(ProdutoTamanho.GG);
		assertEquals(ProdutoTamanho.GG, produtoVariante.getTamanho());

		produtoVariante.setTamanho(ProdutoTamanho.N34);
		assertEquals(ProdutoTamanho.N34, produtoVariante.getTamanho());

		produtoVariante.setTamanho(ProdutoTamanho.N44);
		assertEquals(ProdutoTamanho.N44, produtoVariante.getTamanho());
	}

	@Test
	@DisplayName("Deve permitir múltiplas variantes do mesmo produto")
	void testMultiplasVariantesDoMesmoProduto() {

		ProdutoVariante varianteM = new ProdutoVariante();
		varianteM.setId(1L);
		varianteM.setProduto(produto);
		varianteM.setTamanho(ProdutoTamanho.M);
		varianteM.setQuantidade(50);

		ProdutoVariante varianteG = new ProdutoVariante();
		varianteG.setId(2L);
		varianteG.setProduto(produto);
		varianteG.setTamanho(ProdutoTamanho.G);
		varianteG.setQuantidade(30);

		ProdutoVariante varianteGG = new ProdutoVariante();
		varianteGG.setId(3L);
		varianteGG.setProduto(produto);
		varianteGG.setTamanho(ProdutoTamanho.GG);
		varianteGG.setQuantidade(20);

		assertEquals(produto, varianteM.getProduto());
		assertEquals(produto, varianteG.getProduto());
		assertEquals(produto, varianteGG.getProduto());

		assertEquals(ProdutoTamanho.M, varianteM.getTamanho());
		assertEquals(ProdutoTamanho.G, varianteG.getTamanho());
		assertEquals(ProdutoTamanho.GG, varianteGG.getTamanho());
	}

	@Test
	@DisplayName("Deve permitir múltiplas alterações na variante")
	void testMultiplasAlteracoes() {

		produtoVariante.setTamanho(ProdutoTamanho.G);
		produtoVariante.setQuantidade(100);
		produtoVariante.setId(5L);

		assertEquals(ProdutoTamanho.G, produtoVariante.getTamanho());
		assertEquals(100, produtoVariante.getQuantidade());
		assertEquals(5L, produtoVariante.getId());
	}

	@Test
	@DisplayName("Deve funcionar em estruturas de Set")
	void testEmSet() {

		ProdutoVariante variante1 = new ProdutoVariante();
		variante1.setId(1L);

		ProdutoVariante variante2 = new ProdutoVariante();
		variante2.setId(1L);

		var conjunto = new java.util.HashSet<>();
		conjunto.add(variante1);
		conjunto.add(variante2);

		assertEquals(1, conjunto.size());
	}

	@Test
	@DisplayName("Deve funcionar em estruturas de Map")
	void testEmMap() {

		ProdutoVariante variante1 = new ProdutoVariante();
		variante1.setId(1L);

		var mapa = new java.util.HashMap<>();
		mapa.put(1L, variante1);

		assertTrue(mapa.containsKey(1L));
		assertEquals(variante1, mapa.get(1L));
	}
}

