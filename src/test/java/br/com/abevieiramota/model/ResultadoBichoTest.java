package br.com.abevieiramota.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ResultadoBichoTest {

	@Parameters(name = "{index} : {0} - {1}")
	public static Collection<? extends Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ "1201", Bicho.AVESTRUZ },
				{ "0000", Bicho.VACA },
				{ "7757", Bicho.JACARE },
				{ "1116", Bicho.BORBOLETA },
				{ "4313", Bicho.BORBOLETA } });
	}

	private String resultado;
	private Bicho bichoEsperado;

	public ResultadoBichoTest(String resultado, Bicho bichoEsperado) {
		this.resultado = resultado;
		this.bichoEsperado = bichoEsperado;
	}

	@Test
	public void test() {
		Bicho bichoRetornado = Bicho.fromResultado(this.resultado, TipoDezena.TERCEIRA);
		assertEquals(bichoEsperado, bichoRetornado);
	}
}
