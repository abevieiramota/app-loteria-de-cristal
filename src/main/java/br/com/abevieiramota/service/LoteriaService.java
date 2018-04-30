package br.com.abevieiramota.service;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.abevieiramota.gui.wmain.Parametros;
import br.com.abevieiramota.model.Configuracao;
import br.com.abevieiramota.model.Loteria;
import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.dao.EMF;
import br.com.abevieiramota.model.dao.ResultadoDao;
import br.com.abevieiramota.service.downloader.LoteriaDownloader;
import br.com.abevieiramota.service.parser.LoteriaHTMLParser;
import br.com.abevieiramota.util.DataUtil;

public class LoteriaService {

	private ResultadoDao resDao;

	public LoteriaService() throws ClassNotFoundException, SQLException {
		this.resDao = new ResultadoDao();
	}

	public void atualizar(Loteria loteria) throws SQLException, ParseException, IOException {
		checkNotNull(loteria);
		checkArgument(loteria.getId() == 0, "Atualização para loteria federal ainda não implementada");

		EntityManager manager = EMF.buildManager();

		TypedQuery<Configuracao> query = manager.createQuery("from Configuracao where loteria = :loteria",
				Configuracao.class);
		query.setParameter("loteria", Parametros.getLoteria());

		Configuracao config = query.getSingleResult();

		String dtHoje = DataUtil.hoje();

		List<String> datasAAtualizar = DataUtil.range(config.getDataUltimaAtualizacao(), dtHoje);

		manager.getTransaction().begin();

		for (String data : datasAAtualizar) {

			String html = LoteriaDownloader.download(data);
			Set<Resultado> resultadosDoDia = LoteriaHTMLParser.extrairResultadosDoHTMLDaData(html, data, loteria);
			for (Resultado resultado : resultadosDoDia) {
				if (resultado.isAfter(config.getDataUltimaAtualizacao(), config.getTurnoUltimaAtualizacao())) {
					this.resDao.salvar(resultado);

					config.setDataUltimaAtualizacao(resultado.getData());
					config.setTurnoUltimaAtualizacao(resultado.getTurno());
				}
			}
		}

		manager.getTransaction().commit();
		manager.close();
	}
}
