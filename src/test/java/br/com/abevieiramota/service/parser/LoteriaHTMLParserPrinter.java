package br.com.abevieiramota.service.parser;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.TipoLoteria;

/**
 * Criei apenas para imprimir os resultados extraídos de arquivos diversos.
 * 
 * @author abelardo
 *
 */
@RunWith(JUnit4.class)
public class LoteriaHTMLParserPrinter {

	@Test
	public void doPrint() throws IOException {
		String html = Files.asCharSource(new File("resources/resultado_25_07_2013.html"), Charsets.UTF_8).read();
		Set<Resultado> resultados = LoteriaHTMLParser.extrairResultadosDoHTML(html, TipoLoteria.LOCAL);

		for (Resultado resultado : resultados) {
			System.out.println(String.format("%s\n\n", resultado));
		}
	}
}
