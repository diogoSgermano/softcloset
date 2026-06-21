package com.testesII.backend.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do enum ProdutoTamanho")
class ProdutoTamanhoEnumTest {

	@Test
	@DisplayName("Deve conter todos os tamanhos de roupa")
	void testTamanhosRoupa() {
		assertNotNull(ProdutoTamanho.PP);
		assertNotNull(ProdutoTamanho.P);
		assertNotNull(ProdutoTamanho.M);
		assertNotNull(ProdutoTamanho.G);
		assertNotNull(ProdutoTamanho.GG);
		assertNotNull(ProdutoTamanho.GGG);
	}

	@Test
	@DisplayName("Deve conter todos os tamanhos de sapatos")
	void testTamanhosSapatos() {
		assertNotNull(ProdutoTamanho.N34);
		assertNotNull(ProdutoTamanho.N35);
		assertNotNull(ProdutoTamanho.N36);
		assertNotNull(ProdutoTamanho.N37);
		assertNotNull(ProdutoTamanho.N38);
		assertNotNull(ProdutoTamanho.N39);
		assertNotNull(ProdutoTamanho.N40);
		assertNotNull(ProdutoTamanho.N41);
		assertNotNull(ProdutoTamanho.N42);
		assertNotNull(ProdutoTamanho.N43);
		assertNotNull(ProdutoTamanho.N44);
		assertNotNull(ProdutoTamanho.N45);
	}

	@Test
	@DisplayName("Deve conter exatamente 18 tamanhos")
	void testQuantidadeDeTamanhos() {
		ProdutoTamanho[] tamanhos = ProdutoTamanho.values();
		assertEquals(18, tamanhos.length);
	}

	@Test
	@DisplayName("Valor de PP deve ser 'PP'")
	void testValorPP() {
		assertEquals("PP", ProdutoTamanho.PP.getValor());
	}

	@Test
	@DisplayName("Valor de M deve ser 'M'")
	void testValorM() {
		assertEquals("M", ProdutoTamanho.M.getValor());
	}

	@Test
	@DisplayName("Valor de GG deve ser 'GG'")
	void testValorGG() {
		assertEquals("GG", ProdutoTamanho.GG.getValor());
	}

	@Test
	@DisplayName("Valor de N34 deve ser '34'")
	void testValorN34() {
		assertEquals("34", ProdutoTamanho.N34.getValor());
	}

	@Test
	@DisplayName("Valor de N45 deve ser '45'")
	void testValorN45() {
		assertEquals("45", ProdutoTamanho.N45.getValor());
	}

	@Test
	@DisplayName("Nenhum tamanho deve ter valor nulo")
	void testNenhumValorNulo() {
		for (ProdutoTamanho tamanho : ProdutoTamanho.values()) {
			assertNotNull(tamanho.getValor());
			assertFalse(tamanho.getValor().isEmpty());
		}
	}

	@Test
	@DisplayName("fromValor('PP') deve retornar PP")
	void testFromValorPP() {
		ProdutoTamanho tamanho = ProdutoTamanho.fromValor("PP");
		assertEquals(ProdutoTamanho.PP, tamanho);
	}

	@Test
	@DisplayName("fromValor('M') deve retornar M")
	void testFromValorM() {
		ProdutoTamanho tamanho = ProdutoTamanho.fromValor("M");
		assertEquals(ProdutoTamanho.M, tamanho);
	}

	@Test
	@DisplayName("fromValor('34') deve retornar N34")
	void testFromValor34() {
		ProdutoTamanho tamanho = ProdutoTamanho.fromValor("34");
		assertEquals(ProdutoTamanho.N34, tamanho);
	}

	@Test
	@DisplayName("fromValor('45') deve retornar N45")
	void testFromValor45() {
		ProdutoTamanho tamanho = ProdutoTamanho.fromValor("45");
		assertEquals(ProdutoTamanho.N45, tamanho);
	}

	@Test
	@DisplayName("fromValor deve funcionar com minúsculas")
	void testFromValorMinusculas() {
		ProdutoTamanho tamanho = ProdutoTamanho.fromValor("pp");
		assertEquals(ProdutoTamanho.PP, tamanho);
	}

	@Test
	@DisplayName("fromValor deve funcionar com MAIÚSCULAS")
	void testFromValorMaiusculas() {
		ProdutoTamanho tamanho = ProdutoTamanho.fromValor("GG");
		assertEquals(ProdutoTamanho.GG, tamanho);
	}

	@Test
	@DisplayName("fromValor deve funcionar com misto")
	void testFromValorMisto() {
		ProdutoTamanho tamanho = ProdutoTamanho.fromValor("Gg");
		assertEquals(ProdutoTamanho.GG, tamanho);
	}

	@Test
	@DisplayName("fromValor(null) deve lançar IllegalArgumentException")
	void testFromValorNull() {
		IllegalArgumentException excecao = assertThrows(
			IllegalArgumentException.class,
			() -> ProdutoTamanho.fromValor(null)
		);
		assertTrue(excecao.getMessage().contains("obrigatório"));
	}

	@Test
	@DisplayName("fromValor('') deve lançar IllegalArgumentException")
	void testFromValorVazio() {
		assertThrows(
			IllegalArgumentException.class,
			() -> ProdutoTamanho.fromValor("")
		);
	}

	@Test
	@DisplayName("fromValor('   ') deve lançar IllegalArgumentException")
	void testFromValorEmBranco() {
		assertThrows(
			IllegalArgumentException.class,
			() -> ProdutoTamanho.fromValor("   ")
		);
	}

	@Test
	@DisplayName("fromValor('XL') deve lançar IllegalArgumentException")
	void testFromValorInvalido() {
		IllegalArgumentException excecao = assertThrows(
			IllegalArgumentException.class,
			() -> ProdutoTamanho.fromValor("XL")
		);
		assertTrue(excecao.getMessage().contains("inválido"));
	}

	@Test
	@DisplayName("fromValor('100') deve lançar IllegalArgumentException")
	void testFromValorNumeroInvalido() {
		assertThrows(
			IllegalArgumentException.class,
			() -> ProdutoTamanho.fromValor("100")
		);
	}

	@ParameterizedTest
	@ValueSource(strings = {"PP", "P", "M", "G", "GG", "GGG"})
	@DisplayName("Todos os tamanhos de roupa devem converter sem erro")
	void testTodosRoupaValidos(String valor) {
		assertDoesNotThrow(() -> ProdutoTamanho.fromValor(valor));
	}

	@ParameterizedTest
	@ValueSource(strings = {"34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45"})
	@DisplayName("Todos os tamanhos de sapatos devem converter sem erro")
	void testTodosSapatosValidos(String valor) {
		assertDoesNotThrow(() -> ProdutoTamanho.fromValor(valor));
	}

	@ParameterizedTest
	@ValueSource(strings = {"XL", "XXL", "50", "99", "MINI"})
	@DisplayName("Valores inválidos devem lançar exceção")
	void testValoresInvalidos(String valor) {
		assertThrows(
			IllegalArgumentException.class,
			() -> ProdutoTamanho.fromValor(valor)
		);
	}

	@Test
	@DisplayName("valueOf() deve retornar tamanho pelo nome")
	void testValueOf() {
		ProdutoTamanho tamanho = ProdutoTamanho.valueOf("M");
		assertEquals(ProdutoTamanho.M, tamanho);
	}

	@Test
	@DisplayName("valueOf() com nome inválido deve lançar exceção")
	void testValueOfInvalido() {
		assertThrows(
			IllegalArgumentException.class,
			() -> ProdutoTamanho.valueOf("INVALIDO")
		);
	}

	@Test
	@DisplayName("Instâncias do mesmo valor devem ser iguais")
	void testComparacao() {
		ProdutoTamanho tam1 = ProdutoTamanho.M;
		ProdutoTamanho tam2 = ProdutoTamanho.M;
		assertEquals(tam1, tam2);
		assertTrue(tam1 == tam2);
	}

	@Test
	@DisplayName("Instâncias diferentes devem ser diferentes")
	void testComparacaoDiferentes() {
		ProdutoTamanho tam1 = ProdutoTamanho.M;
		ProdutoTamanho tam2 = ProdutoTamanho.G;
		assertNotEquals(tam1, tam2);
	}

	@Test
	@DisplayName("Ordinal de PP deve ser 0")
	void testOrdinalPP() {
		assertEquals(0, ProdutoTamanho.PP.ordinal());
	}

	@Test
	@DisplayName("Ordinal de M deve ser 2")
	void testOrdinalM() {
		assertEquals(2, ProdutoTamanho.M.ordinal());
	}

	@Test
	@DisplayName("Ordinal de N34 deve ser 6 (primeiro tamanho de sapato)")
	void testOrdinalN34() {
		assertEquals(6, ProdutoTamanho.N34.ordinal());
	}

	@Test
	@DisplayName("Ordinal de N45 deve ser 17 (último tamanho)")
	void testOrdinalN45() {
		assertEquals(17, ProdutoTamanho.N45.ordinal());
	}

	@Test
	@DisplayName("Tamanhos de roupa devem estar no início")
	void testOrdensTamanhosRoupa() {
		ProdutoTamanho[] tamanhos = ProdutoTamanho.values();

		assertEquals(ProdutoTamanho.PP, tamanhos[0]);
		assertEquals(ProdutoTamanho.P, tamanhos[1]);
		assertEquals(ProdutoTamanho.M, tamanhos[2]);
		assertEquals(ProdutoTamanho.G, tamanhos[3]);
		assertEquals(ProdutoTamanho.GG, tamanhos[4]);
		assertEquals(ProdutoTamanho.GGG, tamanhos[5]);
	}

	@Test
	@DisplayName("Tamanhos de sapatos devem estar no final")
	void testOrdensTamanhosSapatos() {
		ProdutoTamanho[] tamanhos = ProdutoTamanho.values();

		assertEquals(ProdutoTamanho.N34, tamanhos[6]);
		assertEquals(ProdutoTamanho.N45, tamanhos[17]);
	}

	@Test
	@DisplayName("Tamanhos de sapatos devem ir de 34 a 45")
	void testRangeSapatos() {

		int[] numerosEsperados = {34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45};

		for (int numero : numerosEsperados) {
			String valor = String.valueOf(numero);
			ProdutoTamanho tamanho = ProdutoTamanho.fromValor(valor);
			assertEquals(valor, tamanho.getValor());
		}
	}

	@Test
	@DisplayName("Conversão fromValor seguida por getValor deve retornar valor original")
	void testConversaoBidirecionalRoupa() {
		String[] valores = {"PP", "P", "M", "G", "GG", "GGG"};

		for (String valor : valores) {
			ProdutoTamanho tamanho = ProdutoTamanho.fromValor(valor);
			assertEquals(valor, tamanho.getValor());
		}
	}

	@Test
	@DisplayName("Conversão bidirecional para sapatos")
	void testConversaoBidirecionalSapatos() {
		for (int numero = 34; numero <= 45; numero++) {
			String valor = String.valueOf(numero);
			ProdutoTamanho tamanho = ProdutoTamanho.fromValor(valor);
			assertEquals(valor, tamanho.getValor());
		}
	}
}

