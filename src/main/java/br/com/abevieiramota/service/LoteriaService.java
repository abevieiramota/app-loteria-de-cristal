package br.com.abevieiramota.service;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.TipoLoteria;
import br.com.abevieiramota.model.Turno;
import br.com.abevieiramota.model.dao.ConfiguracoesDAO;
import br.com.abevieiramota.model.dao.ResultadoDao;
import br.com.abevieiramota.service.downloader.LoteriaDownloader;
import br.com.abevieiramota.service.parser.LoteriaHTMLParser;
import br.com.abevieiramota.util.DataUtil;

public class LoteriaService {

	private ConfiguracoesDAO confDAO;
	private ResultadoDao resultadoDAO;

	public LoteriaService() throws ClassNotFoundException, SQLException {
		this.confDAO = new ConfiguracoesDAO();
		this.resultadoDAO = new ResultadoDao();
	}

	public void atualizar(TipoLoteria tipo) throws SQLException, ParseException, IOException {
		checkNotNull(tipo);
		checkArgument(tipo == TipoLoteria.LOCAL, "Atualização para loteria federal ainda não implementada");

		String dtUltimaAtualizacao = this.confDAO.ultimaDataAtualizado(tipo);
		Turno turnoUltimaAtualizacao = this.confDAO.ultimoTurnoAtualizado(tipo);
		String dtHoje = DataUtil.hoje();

		List<String> datasAAtualizar = DataUtil.range(dtUltimaAtualizacao, dtHoje);

		for (String data : datasAAtualizar) {

			String html = LoteriaDownloader.download(data);
			Set<Resultado> resultadosDoDia = LoteriaHTMLParser.extrairResultadosDoHTMLDaData(html, data, tipo);
			for (Resultado resultado : resultadosDoDia) {
				if (resultado.isAfter(dtUltimaAtualizacao, turnoUltimaAtualizacao)) {
					this.resultadoDAO.salvar(resultado);
					this.confDAO.atualizarUltimaAtualizacao(resultado.getData(), resultado.getTurno(), tipo);
				}
			}
		}
	}
}
