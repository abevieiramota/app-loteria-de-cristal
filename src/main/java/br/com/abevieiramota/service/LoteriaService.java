package br.com.abevieiramota.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.Resultado.Turno;
import br.com.abevieiramota.model.dao.ConfiguracoesDAO;
import br.com.abevieiramota.model.dao.ResultadoDAO;
import br.com.abevieiramota.service.downloader.LoteriaDownloader;
import br.com.abevieiramota.service.parser.LoteriaHTMLParser;
import br.com.abevieiramota.util.DataUtil;

public class LoteriaService {

	private ConfiguracoesDAO confDAO;
	private ResultadoDAO resultadoDAO;

	public LoteriaService() throws ClassNotFoundException, SQLException {
		confDAO = new ConfiguracoesDAO();
		resultadoDAO = new ResultadoDAO();
	}

	public void atualizar() throws SQLException, ParseException, IOException, ClassNotFoundException {
		String dtUltimaAtualizacao = this.confDAO.ultimaDataAtualizado();
		Turno turnoUltimaAtualizacao = this.confDAO.ultimoTurnoAtualizado();
		String dtHoje = DataUtil.hoje();

		List<String> datasAAtualizar = DataUtil.range(dtUltimaAtualizacao, dtHoje);

		for (String data : datasAAtualizar) {

			String html = LoteriaDownloader.download(data);
			Set<Resultado> resultadosDoDia = LoteriaHTMLParser.extrairResultadosDoHTMLDaData(html, data);
			for (Resultado resultado : resultadosDoDia) {
				if (resultado.isAfter(dtUltimaAtualizacao, turnoUltimaAtualizacao)) {
					this.resultadoDAO.salvar(resultado);
					this.confDAO.atualizarUltimaAtualizacao(resultado.data, resultado.turno);
				}
			}
		}
	}
}
