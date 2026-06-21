package com.testesII.backend.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do enum Classificacao")
class ClassificacaoEnumTest {

	@Test
	@DisplayName("Deve conter todas as classificações esperadas")
	void testTodosOsValores() {
		assertNotNull(Classificacao.NOVO);
		assertNotNull(Classificacao.DESTAQUE);
		assertNotNull(Classificacao.NORMAL);
	}

	@Test
	@DisplayName("Deve conter exatamente 3 classificações")
	void testQuantidadeDeClassificacoes() {

		Classificacao[] classificacoes = Classificacao.values();

		assertEquals(3, classificacoes.length);
	}

	@Test
	@DisplayName("Descrição de NOVO deve ser 'Novo'")
	void testDescricaoNovo() {
		assertEquals("Novo", Classificacao.NOVO.getDescricao());
	}

	@Test
	@DisplayName("Descrição de DESTAQUE deve ser 'Destaque'")
	void testDescricaoDestaque() {
		assertEquals("Destaque", Classificacao.DESTAQUE.getDescricao());
	}

	@Test
	@DisplayName("Descrição de NORMAL deve ser 'Normal'")
	void testDescricaoNormal() {
		assertEquals("Normal", Classificacao.NORMAL.getDescricao());
	}

	@Test
	@DisplayName("Nenhuma classificação deve ter descrição nula")
	void testNenhumaDescricaoNula() {
		for (Classificacao classificacao : Classificacao.values()) {
			assertNotNull(classificacao.getDescricao());
			assertFalse(classificacao.getDescricao().isEmpty());
		}
	}

	@Test
	@DisplayName("fromDescricao('Novo') deve retornar NOVO")
	void testFromDescricaoNovo() {
		Classificacao classificacao = Classificacao.fromDescricao("Novo");
		assertEquals(Classificacao.NOVO, classificacao);
	}

	@Test
	@DisplayName("fromDescricao('Destaque') deve retornar DESTAQUE")
	void testFromDescricaoDestaque() {
		Classificacao classificacao = Classificacao.fromDescricao("Destaque");
		assertEquals(Classificacao.DESTAQUE, classificacao);
	}

	@Test
	@DisplayName("fromDescricao('Normal') deve retornar NORMAL")
	void testFromDescricaoNormal() {
		Classificacao classificacao = Classificacao.fromDescricao("Normal");
		assertEquals(Classificacao.NORMAL, classificacao);
	}

	@Test
	@DisplayName("fromDescricao deve funcionar com minúsculas")
	void testFromDescricaoMinusculas() {
		Classificacao classificacao = Classificacao.fromDescricao("novo");
		assertEquals(Classificacao.NOVO, classificacao);
	}

	@Test
	@DisplayName("fromDescricao deve funcionar com MAIÚSCULAS")
	void testFromDescricaoMaiusculas() {
		Classificacao classificacao = Classificacao.fromDescricao("DESTAQUE");
		assertEquals(Classificacao.DESTAQUE, classificacao);
	}

	@Test
	@DisplayName("fromDescricao(null) deve lançar IllegalArgumentException")
	void testFromDescricaoNull() {
		IllegalArgumentException excecao = assertThrows(
			IllegalArgumentException.class,
			() -> Classificacao.fromDescricao(null)
		);
		assertTrue(excecao.getMessage().contains("obrigatória"));
	}

	@Test
	@DisplayName("fromDescricao('') deve lançar IllegalArgumentException")
	void testFromDescricaoVazio() {
		assertThrows(
			IllegalArgumentException.class,
			() -> Classificacao.fromDescricao("")
		);
	}

	@Test
	@DisplayName("fromDescricao('INVÁLIDO') deve lançar IllegalArgumentException")
	void testFromDescricaoInvalido() {
		IllegalArgumentException excecao = assertThrows(
			IllegalArgumentException.class,
			() -> Classificacao.fromDescricao("INVÁLIDO")
		);
		assertTrue(excecao.getMessage().contains("inválida"));
	}

	@ParameterizedTest
	@ValueSource(strings = {"Novo", "Destaque", "Normal"})
	@DisplayName("Todas as descrições válidas devem converter sem erro")
	void testTodosValoresValidos(String descricao) {
		assertDoesNotThrow(() -> Classificacao.fromDescricao(descricao));
	}

	@ParameterizedTest
	@ValueSource(strings = {"Antigo", "Especial", "Premium", "XYZ"})
	@DisplayName("Descrições inválidas devem lançar exceção")
	void testDescricoesInvalidas(String descricao) {
		assertThrows(
			IllegalArgumentException.class,
			() -> Classificacao.fromDescricao(descricao)
		);
	}

	@Test
	@DisplayName("valueOf() deve retornar classificação pelo nome")
	void testValueOf() {
		Classificacao classificacao = Classificacao.valueOf("NOVO");
		assertEquals(Classificacao.NOVO, classificacao);
	}

	@Test
	@DisplayName("valueOf() com nome inválido deve lançar exceção")
	void testValueOfInvalido() {
		assertThrows(
			IllegalArgumentException.class,
			() -> Classificacao.valueOf("INVALIDO")
		);
	}

	@Test
	@DisplayName("Instâncias do mesmo valor devem ser iguais")
	void testComparacao() {
		Classificacao clf1 = Classificacao.NOVO;
		Classificacao clf2 = Classificacao.NOVO;
		assertEquals(clf1, clf2);
		assertTrue(clf1 == clf2);
	}

	@Test
	@DisplayName("Instâncias diferentes devem ser diferentes")
	void testComparacaoDiferentes() {
		Classificacao clf1 = Classificacao.NOVO;
		Classificacao clf2 = Classificacao.DESTAQUE;
		assertNotEquals(clf1, clf2);
	}

	@Test
	@DisplayName("Ordinais devem estar em ordem")
	void testTodosOrdinais() {
		assertEquals(0, Classificacao.NOVO.ordinal());
		assertEquals(1, Classificacao.DESTAQUE.ordinal());
		assertEquals(2, Classificacao.NORMAL.ordinal());
	}
}

