package br.com.abevieiramota.gui.wmain;

import java.sql.SQLException;

import javax.swing.JLabel;

import br.com.abevieiramota.messages.Messages;
import br.com.abevieiramota.model.Turno;
import br.com.abevieiramota.model.dao.ConfiguracoesDAO;

public class LabelUltimaAtualizacao extends JLabel {

	private ConfiguracoesDAO confDAO = null;
	private static final long serialVersionUID = -7369055521816327024L;
	private static final String DEFAULT_TEXT_FORMAT = Messages.getString("ui.label_ultima_atualizacao.text");

	public LabelUltimaAtualizacao() throws ClassNotFoundException, SQLException {
		this.confDAO = new ConfiguracoesDAO();

		atualiza();
	}

	public void atualiza() throws SQLException {
		String ultimaDataAtualizado = this.confDAO.ultimaDataAtualizado(Configuracao.getTipoLoteria());
		Turno ultimoTurnoAtualizado = this.confDAO.ultimoTurnoAtualizado(Configuracao.getTipoLoteria());

		setText(String.format(DEFAULT_TEXT_FORMAT, ultimaDataAtualizado, ultimoTurnoAtualizado));
	}

}
