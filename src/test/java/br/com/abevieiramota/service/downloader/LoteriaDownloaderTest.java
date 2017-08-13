package br.com.abevieiramota.service.downloader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;

import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.TipoLoteria;
import br.com.abevieiramota.service.parser.LoteriaHTMLParser;

@RunWith(JUnit4.class)
public class LoteriaDownloaderTest {

	@Test
	public void deveRealizarDownloadParaDataInformada() throws IOException {
		LoteriaDownloader.download("25/07/2013");
	}

	@Test
	public void deveRealizarDownloadComResultadosParaDataInformada() throws IOException {
		final String dataDoTeste = "07/01/2013";
		final String html = LoteriaDownloader.download(dataDoTeste);
		Set<Resultado> resultados = LoteriaHTMLParser.extrairResultadosDoHTML(html, TipoLoteria.LOCAL);

		assertNotNull(resultados);

		Set<Resultado> resultadosDaData = Sets.filter(resultados, new Predicate<Resultado>() {
			public boolean apply(Resultado resultado) {
				return resultado.getData().equals(dataDoTeste);
			}
		});
		assertEquals(2, resultadosDaData.size());

	}
}
