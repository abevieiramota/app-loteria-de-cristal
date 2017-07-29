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

import br.com.abevieiramota.model.Resultado;

@Ignore
public class LoteriaHTMLParserBaseTest {

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
				this.dataDosResultados);
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
