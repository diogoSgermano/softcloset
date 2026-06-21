package com.testesII.backend.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do enum Categoria")
class CategoriaEnumTest {

	@Test
	@DisplayName("Deve conter todas as categorias esperadas")
	void testTodosOsValores() {

		assertNotNull(Categoria.CALCADOS);
		assertNotNull(Categoria.CAMISAS);
		assertNotNull(Categoria.CALCAS);
		assertNotNull(Categoria.VESTIDOS);
	}

	@Test
	@DisplayName("Deve conter exatamente 4 categorias")
	void testQuantidadeDeCategorias() {

		Categoria[] categorias = Categoria.values();

		assertEquals(4, categorias.length);
	}

	@Test
	@DisplayName("values() deve retornar todas as categorias")
	void testValuesArray() {

		Categoria[] categorias = Categoria.values();

		assertArrayEquals(
			new Categoria[]{
				Categoria.CALCADOS,
				Categoria.CAMISAS,
				Categoria.CALCAS,
				Categoria.VESTIDOS
			},
			categorias
		);
	}

	@Test
	@DisplayName("Descrição de CALCADOS deve ser 'Calçados'")
	void testDescricaoCalculados() {

		assertEquals("Calçados", Categoria.CALCADOS.getDescricao());
	}

	@Test
	@DisplayName("Descrição de CAMISAS deve ser 'Camisas'")
	void testDescricaoCamisas() {

		assertEquals("Camisas", Categoria.CAMISAS.getDescricao());
	}

	@Test
	@DisplayName("Descrição de CALCAS deve ser 'Calças'")
	void testDescricaoCalculcas() {

		assertEquals("Calças", Categoria.CALCAS.getDescricao());
	}

	@Test
	@DisplayName("Descrição de VESTIDOS deve ser 'Vestidos'")
	void testDescricaoVestidos() {

		assertEquals("Vestidos", Categoria.VESTIDOS.getDescricao());
	}

	@Test
	@DisplayName("Nenhuma categoria deve ter descrição nula")
	void testNenhumaDescricaoNula() {

		Categoria[] categorias = Categoria.values();

		for (Categoria categoria : categorias) {
			assertNotNull(categoria.getDescricao());
			assertFalse(categoria.getDescricao().isEmpty());
		}
	}

	@Test
	@DisplayName("fromDescricao('Calçados') deve retornar CALCADOS")
	void testFromDescricaoCalculados() {

		Categoria categoria = Categoria.fromDescricao("Calçados");

		assertEquals(Categoria.CALCADOS, categoria);
	}

	@Test
	@DisplayName("fromDescricao('Camisas') deve retornar CAMISAS")
	void testFromDescricaoCamisas() {

		Categoria categoria = Categoria.fromDescricao("Camisas");

		assertEquals(Categoria.CAMISAS, categoria);
	}

	@Test
	@DisplayName("fromDescricao('Calças') deve retornar CALCAS")
	void testFromDescricaoCalculcas() {

		Categoria categoria = Categoria.fromDescricao("Calças");

		assertEquals(Categoria.CALCAS, categoria);
	}

	@Test
	@DisplayName("fromDescricao('Vestidos') deve retornar VESTIDOS")
	void testFromDescricaoVestidos() {

		Categoria categoria = Categoria.fromDescricao("Vestidos");

		assertEquals(Categoria.VESTIDOS, categoria);
	}

	@Test
	@DisplayName("fromDescricao deve funcionar com minúsculas")
	void testFromDescricaoMinusculas() {

		Categoria categoria = Categoria.fromDescricao("camisas");

		assertEquals(Categoria.CAMISAS, categoria);
	}

	@Test
	@DisplayName("fromDescricao deve funcionar com MAIÚSCULAS")
	void testFromDescricaoMaiusculas() {

		Categoria categoria = Categoria.fromDescricao("CALCADOS");

		assertEquals(Categoria.CALCADOS, categoria);
	}

	@Test
	@DisplayName("fromDescricao deve funcionar com MaIúScUlAs MiStAs")
	void testFromDescricaoMisto() {

		Categoria categoria = Categoria.fromDescricao("VeStIdOs");

		assertEquals(Categoria.VESTIDOS, categoria);
	}

	@Test
	@DisplayName("fromDescricao(null) deve lançar IllegalArgumentException")
	void testFromDescricaoNull() {

		IllegalArgumentException excecao = assertThrows(
			IllegalArgumentException.class,
			() -> Categoria.fromDescricao(null)
		);

		assertTrue(excecao.getMessage().contains("obrigatória"));
	}

	@Test
	@DisplayName("fromDescricao('') deve lançar IllegalArgumentException")
	void testFromDescricaoVazio() {

		assertThrows(
			IllegalArgumentException.class,
			() -> Categoria.fromDescricao("")
		);
	}

	@Test
	@DisplayName("fromDescricao('   ') deve lançar IllegalArgumentException")
	void testFromDescricaoEmBranco() {

		assertThrows(
			IllegalArgumentException.class,
			() -> Categoria.fromDescricao("   ")
		);
	}

	@Test
	@DisplayName("fromDescricao('INVÁLIDO') deve lançar IllegalArgumentException")
	void testFromDescricaoInvalido() {

		IllegalArgumentException excecao = assertThrows(
			IllegalArgumentException.class,
			() -> Categoria.fromDescricao("INVÁLIDO")
		);

		assertTrue(excecao.getMessage().contains("inválida"));
	}

	@ParameterizedTest
	@ValueSource(strings = {"Calçados", "Camisas", "Calças", "Vestidos"})
	@DisplayName("Todas as descrições válidas devem converter sem erro")
	void testTodosValoresValidos(String descricao) {

		assertDoesNotThrow(() -> Categoria.fromDescricao(descricao));
	}

	@ParameterizedTest
	@ValueSource(strings = {"Sapatos", "Shorts", "Bonés", "XYZ"})
	@DisplayName("Descrições inválidas devem lançar exceção")
	void testDescricoesInvalidas(String descricao) {

		assertThrows(
			IllegalArgumentException.class,
			() -> Categoria.fromDescricao(descricao)
		);
	}

	@Test
	@DisplayName("valueOf() deve retornar categoria pelo nome")
	void testValueOf() {

		Categoria categoria = Categoria.valueOf("CALCADOS");

		assertEquals(Categoria.CALCADOS, categoria);
	}

	@Test
	@DisplayName("valueOf() com nome inválido deve lançar exceção")
	void testValueOfInvalido() {

		assertThrows(
			IllegalArgumentException.class,
			() -> Categoria.valueOf("INVALIDO")
		);
	}

	@Test
	@DisplayName("Instâncias do mesmo valor devem ser iguais")
	void testComparacao() {

		Categoria cat1 = Categoria.CAMISAS;
		Categoria cat2 = Categoria.CAMISAS;

		assertEquals(cat1, cat2);
		assertTrue(cat1 == cat2);
	}

	@Test
	@DisplayName("Instâncias diferentes devem ser diferentes")
	void testComparacaoDiferentes() {

		Categoria cat1 = Categoria.CAMISAS;
		Categoria cat2 = Categoria.CALCADOS;

		assertNotEquals(cat1, cat2);
	}

	@Test
	@DisplayName("Ordinal de CALCADOS deve ser 0")
	void testOrdinalCalculados() {

		assertEquals(0, Categoria.CALCADOS.ordinal());
	}

	@Test
	@DisplayName("Ordinais devem estar em ordem")
	void testTodosOrdinais() {

		assertEquals(0, Categoria.CALCADOS.ordinal());
		assertEquals(1, Categoria.CAMISAS.ordinal());
		assertEquals(2, Categoria.CALCAS.ordinal());
		assertEquals(3, Categoria.VESTIDOS.ordinal());
	}
}

