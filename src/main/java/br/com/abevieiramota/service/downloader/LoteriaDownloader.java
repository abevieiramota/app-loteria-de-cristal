package br.com.abevieiramota.service.downloader;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;

public class LoteriaDownloader {

	private static final String URL_BASE_TEMPLATE = "http://www.lotece.com.br/v2/?page_id=70&data=%s";
	private static final int CONNECTION_TIMEOUT = 10000000;
	
	public static String download(String data) throws IOException {
		checkNotNull(data);
		
		String url = String.format(URL_BASE_TEMPLATE, data);
		String htmlCodificado = Jsoup.connect(url).timeout(CONNECTION_TIMEOUT).get().html();
		
		return StringEscapeUtils.unescapeHtml4(htmlCodificado);
	}
}
