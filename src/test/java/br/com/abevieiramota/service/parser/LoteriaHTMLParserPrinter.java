package br.com.abevieiramota.service.parser;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import br.com.abevieiramota.model.Loteria;
import br.com.abevieiramota.model.Resultado;

/**
 * Criei apenas para imprimir os resultados extraídos de arquivos diversos.
 * 
 * @author abelardo
 *
 */
@RunWith(JUnit4.class)
public class LoteriaHTMLParserPrinter {

	private static final Loteria LOTERIA_LOCAL = new Loteria();
	static {
//		LOTERIA_LOCAL.setId(0);
//		LOTERIA_LOCAL.setLabel("Local");
	}

	@Test
	public void doPrint() throws IOException {
		String html = Files.asCharSource(new File("resources/resultado_25_07_2013.html"), Charsets.UTF_8).read();
		Set<Resultado> resultados = LoteriaHTMLParser.extrairResultadosDoHTML(html, LOTERIA_LOCAL);

		for (Resultado resultado : resultados) {
			System.out.println(String.format("%s\n\n", resultado));
		}
	}
}
