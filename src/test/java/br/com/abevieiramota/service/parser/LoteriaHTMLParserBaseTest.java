package br.com.abevieiramota.service.parser;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import br.com.abevieiramota.model.Loteria;
import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.Turno;

@Ignore
public class LoteriaHTMLParserBaseTest {

	protected static final Turno TURNO_DIURNO = new Turno();
	static {
		TURNO_DIURNO.setId(0);
		TURNO_DIURNO.setLabel("Diurno");
		TURNO_DIURNO.setHoraMinima(10);
		TURNO_DIURNO.setHoraMinima(14);
	}
	protected static final Turno TURNO_NOTURNO = new Turno();
	static {
		TURNO_NOTURNO.setId(0);
		TURNO_NOTURNO.setLabel("Noturno");
		TURNO_NOTURNO.setHoraMinima(18);
		TURNO_NOTURNO.setHoraMinima(20);
	}
	protected static final Loteria LOTERIA_LOCAL = new Loteria();
	static {
		LOTERIA_LOCAL.setId(0);
		LOTERIA_LOCAL.setLabel("Local");
	}

	private Integer qtdDeResultadosEsperada;

	private String dataDosResultados;

	private static String HTML_COM_RESULTADO_CORRETO;
	private Set<Resultado> resultadosExtraidos;
	private Set<Resultado> resultadosEsperados;

	public LoteriaHTMLParserBaseTest(String filePath, Integer quantidadeDeResultados, String dataDosResultados,
			Set<Resultado> resultadosEsperados) throws IOException {
		this.qtdDeResultadosEsperada = quantidadeDeResultados;
		this.dataDosResultados = dataDosResultados;
		this.resultadosEsperados = resultadosEsperados;
		HTML_COM_RESULTADO_CORRETO = Files.asCharSource(new File(filePath), Charsets.UTF_8).read();
	}

	@Before
	public void setUp() {
		resultadosExtraidos = LoteriaHTMLParser.extrairResultadosDoHTMLDaData(HTML_COM_RESULTADO_CORRETO,
				this.dataDosResultados, LOTERIA_LOCAL);
		assertThat(resultadosExtraidos, is(notNullValue()));

	}

	@Test
	public void deveRetornarQuantidadeDeResultadosCorretaParaADataInformada() {
		assertThat(resultadosExtraidos.size(), is(qtdDeResultadosEsperada));
	}

	@Test
	public void deveExtrairOsResultadosEsperados() {
		assertTrue(resultadosEsperados.equals(resultadosExtraidos));
	}

}
