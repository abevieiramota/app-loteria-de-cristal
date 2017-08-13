package br.com.abevieiramota.model;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import br.com.abevieiramota.model.Resultado.ResultadoBuilder;

@RunWith(JUnit4.class)
public class ResultadoTest {

	private static final String[] VALORES_PREMIOS_CORRETOS = new String[] {
			"0728",
			"0383",
			"8157",
			"0863",
			"7532",
			"0874",
			"4668",
			"3967",
			"5237",
			"0375"

	};
	private static final String[] VALORES_PREMIOS_UM_VALOR_INCORRETO = new String[] {
			"0728",
			"0383",
			"8157",
			"Sou um pr�mio incorreto",
			"7532",
			"0874",
			"4668",
			"3967",
			"5237",
			"0375"

	};
	private static final String[] VALORES_PREMIOS_MENOS_DE_10_VALORES = new String[] {
			"0728",
			"0383",
			"8157",
			"7532",
			"0874",
			"4668",
			"3967",
			"5237",
			"0375"

	};

	private static final String DATA_RESULTADO_CORRETO = "21/07/2013";
	private static final String DATA_RESULTADO_INCORRETO = "Oi Abelardo";

	@Test
	public void equalsDeveFuncionarPelaMorDeDeus() {
		Resultado resultado1 = criaResultadoIgual();
		Resultado resultado2 = criaResultadoIgual();

		assertTrue(resultado1.equals(resultado2));
		assertTrue(resultado2.equals(resultado1));
	}

	@Test
	public void equalsDeveFuncionaPelaMorDeDeusSet() {
		Set<Resultado> resultados1 = new HashSet<Resultado>(Arrays.asList(criaResultadoIgual()));

		assertTrue(resultados1.contains(criaResultadoIgual()));
	}

	@Test(expected = IllegalStateException.class)
	public void deveLancarStateExceptCasoBuilderSemData() {
		
		new ResultadoBuilder().premios(VALORES_PREMIOS_CORRETOS).turno(Turno.NOTURNO).build();
	}

	@Test(expected = IllegalStateException.class)
	public void deveLancarStateExceptCasoBuilderSemPremios() {
		
		new ResultadoBuilder().data(DATA_RESULTADO_CORRETO).turno(Turno.NOTURNO).build();
	}

	@Test(expected = IllegalStateException.class)
	public void deveLancarStateExceptCasoBuilderSemTurno() {
		
		new ResultadoBuilder().data(DATA_RESULTADO_CORRETO).premios(VALORES_PREMIOS_CORRETOS).build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void deveLancarArgExceptCasoDataInvalida() {
		
		new ResultadoBuilder().data(DATA_RESULTADO_INCORRETO).premios(VALORES_PREMIOS_CORRETOS).turno(Turno.NOTURNO)
				.build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void deveLancarArgExceptCasoPremioInvalidoUmValorIncorreto() {
		
		new ResultadoBuilder().data(DATA_RESULTADO_INCORRETO).premios(VALORES_PREMIOS_UM_VALOR_INCORRETO)
				.turno(Turno.NOTURNO).build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void deveLancarArgExceptCasoPremioInvalidoQtdDif10() {
		
		new ResultadoBuilder().data(DATA_RESULTADO_INCORRETO).premios(VALORES_PREMIOS_MENOS_DE_10_VALORES)
				.turno(Turno.NOTURNO).build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void deveLancarArgExceptCasoTurnoInvalido() {
		
		new ResultadoBuilder().data(DATA_RESULTADO_INCORRETO).premios(VALORES_PREMIOS_CORRETOS).turno(null).build();
	}

	private Resultado criaResultadoIgual() {
		
		return new ResultadoBuilder().data(DATA_RESULTADO_CORRETO).premios(VALORES_PREMIOS_CORRETOS)
				.turno(Turno.NOTURNO).build();
	}
}
