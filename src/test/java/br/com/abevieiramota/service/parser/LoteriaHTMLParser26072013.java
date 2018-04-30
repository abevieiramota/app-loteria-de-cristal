package br.com.abevieiramota.service.parser;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.Resultado.ResultadoBuilder;

/**
 * Para o arquivo de 26/07/2013 deve encontrar dois resultados de 26/07/2013.
 * 
 * @author abelardo
 *
 */
@RunWith(JUnit4.class)
public class LoteriaHTMLParser26072013 extends LoteriaHTMLParserBaseTest {

	private static final String DATA_RESULTADO = "26/07/2013";
	private static final Integer QUANTIDADE_DE_RESULTADOS_NO_HTML = 2;
	private static final String filePath = "resources/resultado_26_07_2013.html";

	private static final Set<Resultado> resultadosEsperados = new HashSet<Resultado>();

	static {
		Resultado resultadoNoturno = new ResultadoBuilder().data(DATA_RESULTADO)
				.premios(new String[] { "0728", "0383", "8157", "0863", "7532", "0874", "4668", "3967", "5237", "0375"

				}).turno(TURNO_NOTURNO).build();
		Resultado resultadoDiurno = new ResultadoBuilder().data(DATA_RESULTADO)
				.premios(
						new String[] { "8695", "3702", "7560", "7463", "1343", "4517", "7386", "2779", "6821", "9368" })
				.turno(TURNO_DIURNO).build();
		resultadosEsperados.add(resultadoDiurno);
		resultadosEsperados.add(resultadoNoturno);
	}

	public LoteriaHTMLParser26072013() throws IOException {
		super(filePath, QUANTIDADE_DE_RESULTADOS_NO_HTML, DATA_RESULTADO, resultadosEsperados);
	}
}
