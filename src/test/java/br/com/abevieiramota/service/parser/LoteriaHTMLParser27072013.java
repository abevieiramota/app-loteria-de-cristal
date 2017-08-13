package br.com.abevieiramota.service.parser;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.Resultado.ResultadoBuilder;
import br.com.abevieiramota.model.Turno;

/**
 * Para o arquivo de 27/07/2013 deve encontrar dois resultados de 27/07/2013.
 * @author abelardo
 *
 */
@RunWith(JUnit4.class)
public class LoteriaHTMLParser27072013 extends LoteriaHTMLParserBaseTest {
	
	private static final String DATA_RESULTADO = "27/07/2013";
	private static final Integer QUANTIDADE_DE_RESULTADOS_NO_HTML = 2;
	private static final String filePath = "resources/resultado_27_07_2013.html";
	
	private static final Set<Resultado> resultadosEsperados = new HashSet<Resultado>();
	
	static {
		Resultado resultadoNoturno = new ResultadoBuilder().data(DATA_RESULTADO).premios(new String[]{
				"0842",
				"3028",
				"8523",
				"2808",
				"3891",
				"3941",
				"7353",
				"7102",
				"7438",
				"4925"
				
		}).turno(Turno.NOTURNO).build();
		Resultado resultadoDiurno = new ResultadoBuilder().data(DATA_RESULTADO).premios(new String[]{
				"6018",
				"2602",
				"8254",
				"1459",
				"9504",
				"0000",
				"1654",
				"3603",
				"7451",
				"7508"
				
		}).turno(Turno.DIURNO).build();
		resultadosEsperados.add(resultadoDiurno);
		resultadosEsperados.add(resultadoNoturno);
	}

	public LoteriaHTMLParser27072013() throws IOException {
		super(filePath, QUANTIDADE_DE_RESULTADOS_NO_HTML, DATA_RESULTADO, resultadosEsperados);
	}
}
