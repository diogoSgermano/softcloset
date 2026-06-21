package com.testesII.backend.entity;

import com.testesII.backend.enums.Categoria;
import com.testesII.backend.enums.Classificacao;
import com.testesII.backend.enums.ProdutoTamanho;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da entidade Produto")
class ProdutoTest {

	private Produto produto;

	@BeforeEach
	void setUp() {

		produto = new Produto();
		produto.setIdProduto(1L);
		produto.setNome("Camiseta Básica");
		produto.setMarca("Nike");
		produto.setClassificacao(Classificacao.NOVO);
		produto.setCategoria(Categoria.CAMISAS);
		produto.setDescricao("Camiseta de algodão");
		produto.setPreco(new BigDecimal("79.90"));
		produto.setImagemUrl("https://exemplo.com/camiseta.jpg");
	}

	@Test
	@DisplayName("Deve criar Produto com construtor padrão")
	void testConstruroPadrao() {

		Produto novoProduto = new Produto();

		assertNotNull(novoProduto);
	}

	@Test
	@DisplayName("Deve criar Produto com construtor completo")
	void testConstrutorCompleto() {

		List<ProdutoVariante> tamanhos = new ArrayList<>();

		Produto novoProduto = new Produto(
			1L,
			"Camiseta",
			"Nike",
			Classificacao.NOVO,
			Categoria.CAMISAS,
			"Descrição",
			new BigDecimal("79.90"),
			"http://img.jpg",
			tamanhos
		);

		assertEquals(1L, novoProduto.getIdProduto());
		assertEquals("Camiseta", novoProduto.getNome());
		assertEquals("Nike", novoProduto.getMarca());
	}

	@Test
	@DisplayName("Deve setar e obter ID do produto")
	void testSetterGetterIdProduto() {

		Long idEsperado = 1L;

		produto.setIdProduto(idEsperado);
		Long idObtido = produto.getIdProduto();

		assertEquals(idEsperado, idObtido);
	}

	@Test
	@DisplayName("Deve setar e obter nome do produto")
	void testSetterGetterNome() {

		String nomeEsperado = "Camiseta Premium";

		produto.setNome(nomeEsperado);
		String nomeObtido = produto.getNome();

		assertEquals(nomeEsperado, nomeObtido);
	}

	@Test
	@DisplayName("Deve setar e obter marca do produto")
	void testSetterGetterMarca() {

		String marcaEsperada = "Adidas";

		produto.setMarca(marcaEsperada);
		String marcaObtida = produto.getMarca();

		assertEquals(marcaEsperada, marcaObtida);
	}

	@Test
	@DisplayName("Deve setar e obter classificação do produto")
	void testSetterGetterClassificacao() {

		Classificacao classificacaoEsperada = Classificacao.DESTAQUE;

		produto.setClassificacao(classificacaoEsperada);
		Classificacao classificacaoObtida = produto.getClassificacao();

		assertEquals(classificacaoEsperada, classificacaoObtida);
	}

	@Test
	@DisplayName("Deve setar e obter categoria do produto")
	void testSetterGetterCategoria() {

		Categoria categoriaEsperada = Categoria.CALCADOS;

		produto.setCategoria(categoriaEsperada);
		Categoria categoriaObtida = produto.getCategoria();

		assertEquals(categoriaEsperada, categoriaObtida);
	}

	@Test
	@DisplayName("Deve setar e obter preço do produto")
	void testSetterGetterPreco() {

		BigDecimal precoEsperado = new BigDecimal("99.99");

		produto.setPreco(precoEsperado);
		BigDecimal precoObtido = produto.getPreco();

		assertEquals(precoEsperado, precoObtido);
	}

	@Test
	@DisplayName("Deve setar e obter lista de tamanhos")
	void testSetterGetterTamanhos() {

		List<ProdutoVariante> tamanhosEsperados = new ArrayList<>();
		ProdutoVariante variante = new ProdutoVariante();
		variante.setTamanho(ProdutoTamanho.M);
		tamanhosEsperados.add(variante);

		produto.setTamanhos(tamanhosEsperados);
		List<ProdutoVariante> tamanhosObtidos = produto.getTamanhos();

		assertEquals(1, tamanhosObtidos.size());
		assertEquals(ProdutoTamanho.M, tamanhosObtidos.get(0).getTamanho());
	}

	@Test
	@DisplayName("Deve setar marca padrão quando nula")
	void testDefaultValuesMarcaNula() {

		Produto novoProduto = new Produto();
		novoProduto.setMarca(null);

		novoProduto.defaultValues();

		assertEquals("Sem marca", novoProduto.getMarca());
	}

	@Test
	@DisplayName("Deve setar marca padrão quando vazia")
	void testDefaultValuesMarcaVazia() {

		Produto novoProduto = new Produto();
		novoProduto.setMarca("   ");

		novoProduto.defaultValues();

		assertEquals("Sem marca", novoProduto.getMarca());
	}

	@Test
	@DisplayName("Não deve alterar marca se já tiver valor")
	void testDefaultValuesMarcaComValor() {

		Produto novoProduto = new Produto();
		novoProduto.setMarca("Adidas");

		novoProduto.defaultValues();

		assertEquals("Adidas", novoProduto.getMarca());
	}

	@Test
	@DisplayName("Deve considerar produtos iguais se tiverem o mesmo ID")
	void testEqualsComMesmoId() {

		Produto produto1 = new Produto();
		produto1.setIdProduto(1L);
		produto1.setNome("Produto A");

		Produto produto2 = new Produto();
		produto2.setIdProduto(1L);
		produto2.setNome("Produto B");

		assertEquals(produto1, produto2);
	}

	@Test
	@DisplayName("Deve considerar produtos diferentes se tiverem IDs diferentes")
	void testEqualsComIdDiferente() {

		Produto produto1 = new Produto();
		produto1.setIdProduto(1L);

		Produto produto2 = new Produto();
		produto2.setIdProduto(2L);

		assertNotEquals(produto1, produto2);
	}

	@Test
	@DisplayName("Um produto deve ser igual a si mesmo")
	void testEqualsReflexivo() {

		assertEquals(produto, produto);
	}

	@Test
	@DisplayName("Um produto não deve ser igual a null")
	void testEqualsNull() {

		assertNotEquals(produto, null);
	}

	@Test
	@DisplayName("Um produto não deve ser igual a outro tipo de objeto")
	void testEqualsOutroTipo() {

		assertNotEquals(produto, "String");
		assertNotEquals(produto, 123);
	}

	@Test
	@DisplayName("Produtos iguais devem ter mesmo hashCode")
	void testHashCodeIguais() {

		Produto produto1 = new Produto();
		produto1.setIdProduto(1L);

		Produto produto2 = new Produto();
		produto2.setIdProduto(1L);

		assertEquals(produto1.hashCode(), produto2.hashCode());
	}

	@Test
	@DisplayName("Produtos diferentes devem ter hashCodes diferentes")
	void testHashCodeDiferentes() {

		Produto produto1 = new Produto();
		produto1.setIdProduto(1L);

		Produto produto2 = new Produto();
		produto2.setIdProduto(2L);

		assertNotEquals(produto1.hashCode(), produto2.hashCode());
	}

	@Test
	@DisplayName("toString deve conter nome do produto")
	void testToStringContemNome() {

		String resultado = produto.toString();

		assertTrue(resultado.contains("Camiseta Básica"));
		assertTrue(resultado.contains("Nike"));
	}

	@Test
	@DisplayName("toString não deve retornar null")
	void testToStringNaoNulo() {

		assertNotNull(produto.toString());
		assertFalse(produto.toString().isEmpty());
	}

	@Test
	@DisplayName("Produto pode ter lista de tamanhos vazia inicialmente")
	void testTamanhosVazio() {

		Produto novoProduto = new Produto();

		assertNotNull(novoProduto.getTamanhos());
	}

	@Test
	@DisplayName("Deve aceitar preços válidos")
	void testPrecoValido() {

		BigDecimal preco = new BigDecimal("99.99");

		produto.setPreco(preco);

		assertEquals(new BigDecimal("99.99"), produto.getPreco());
	}

	@Test
	@DisplayName("Deve funcionar em estruturas de Set (por equals e hashCode)")
	void testEmSet() {

		Produto produto1 = new Produto();
		produto1.setIdProduto(1L);

		Produto produto2 = new Produto();
		produto2.setIdProduto(1L);

		var conjunto = new java.util.HashSet<>();
		conjunto.add(produto1);
		conjunto.add(produto2);

		assertEquals(1, conjunto.size());
	}

	@Test
	@DisplayName("Produto deve manter referências para variantes")
	void testRelacionamentoComVariantes() {

		ProdutoVariante variante1 = new ProdutoVariante();
		variante1.setTamanho(ProdutoTamanho.M);

		ProdutoVariante variante2 = new ProdutoVariante();
		variante2.setTamanho(ProdutoTamanho.G);

		List<ProdutoVariante> tamanhos = List.of(variante1, variante2);

		produto.setTamanhos(tamanhos);

		assertEquals(2, produto.getTamanhos().size());
		assertTrue(produto.getTamanhos().contains(variante1));
		assertTrue(produto.getTamanhos().contains(variante2));
	}

	@Test
	@DisplayName("Deve permitir múltiplas alterações no produto")
	void testMultiplasAlteracoes() {

		produto.setNome("Novo Nome");
		produto.setPreco(new BigDecimal("199.99"));
		produto.setMarca("Nova Marca");

		assertEquals("Novo Nome", produto.getNome());
		assertEquals(new BigDecimal("199.99"), produto.getPreco());
		assertEquals("Nova Marca", produto.getMarca());
	}
}

