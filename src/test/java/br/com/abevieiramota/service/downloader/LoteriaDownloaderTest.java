package br.com.abevieiramota.service.downloader;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class LoteriaDownloaderTest {

	@Test
	public void deveRealizarDownloadParaDataInformada() throws IOException {
		LoteriaDownloader.download("25/07/2013");
	}

	// @Test
	// public void deveRealizarDownloadComResultadosParaDataInformada() throws
	// IOException {
	// final String dataDoTeste = "07/01/2013";
	// final String html = LoteriaDownloader.download(dataDoTeste);
	// Set<Resultado> resultados = LoteriaHTMLParser.extrairResultadosDoHTML(html,
	// Loteria.LOCAL);
	//
	// assertNotNull(resultados);
	//
	// Set<Resultado> resultadosDaData = Sets.filter(resultados, new
	// Predicate<Resultado>() {
	// public boolean apply(Resultado resultado) {
	// return resultado.getData().equals(dataDoTeste);
	// }
	// });
	// assertEquals(2, resultadosDaData.size());
	//
	// }
}
