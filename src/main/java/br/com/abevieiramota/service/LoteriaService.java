package br.com.abevieiramota.service;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;

import br.com.abevieiramota.model.Configuracao;
import br.com.abevieiramota.model.Loteria;
import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.dao.Dao;
import br.com.abevieiramota.service.downloader.LoteriaDownloader;
import br.com.abevieiramota.service.parser.LoteriaHTMLParser;
import br.com.abevieiramota.util.DataUtil;

public class LoteriaService {

	public void atualizar(Loteria loteria) throws ParseException, IOException {
		checkNotNull(loteria);
		checkArgument(loteria.isAtualizavel(), "Atualização para loteria federal ainda não implementada");

		Dao dao = new Dao();

		String dtHoje = DataUtil.hoje();

		Configuracao config = loteria.getConfiguracao();
		
		List<String> datasAAtualizar = DataUtil.range(config.getDataUltimaAtualizacao(), dtHoje);

		List<Resultado> resultadosASalvar = Lists.newArrayList();

		for (String data : datasAAtualizar) {

			String html = LoteriaDownloader.download(data);
			Set<Resultado> resultadosDoDia = LoteriaHTMLParser.extrairResultadosDoHTMLDaData(html, data, loteria);
			for (Resultado resultado : resultadosDoDia) {
				if (resultado.isAfter(config.getDataUltimaAtualizacao(), config.getTurnoUltimaAtualizacao())) {
					resultadosASalvar.add(resultado);
				}
			}
		}

		if (!resultadosASalvar.isEmpty()) {
			Resultado ultimoResultado = resultadosASalvar.get(resultadosASalvar.size() - 1);

			config.setDataUltimaAtualizacao(ultimoResultado.getData());
			config.setTurnoUltimaAtualizacao(ultimoResultado.getTurno());
		}

		dao.salvarResultados(resultadosASalvar);
		dao.atualizar(config);
	}
}
