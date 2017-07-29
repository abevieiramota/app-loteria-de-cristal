package br.com.abevieiramota.gui.wmain;

import java.sql.SQLException;

import javax.swing.JLabel;

import br.com.abevieiramota.model.dao.ConfiguracoesDAO;

public class LabelUltimaAtualizacao extends JLabel {
	
	private ConfiguracoesDAO confDAO = null;
	private static final long serialVersionUID = -7369055521816327024L;
	private static final String DEFAULT_TEXT_FORMAT = "Última data de atualização: %s %s";
	
	public LabelUltimaAtualizacao() throws ClassNotFoundException, SQLException {
		confDAO = new ConfiguracoesDAO();
		atualiza();
	}
	
	public void atualiza() throws SQLException {
		setText(
				String.format(
						DEFAULT_TEXT_FORMAT,
						confDAO.ultimaDataAtualizado(),
						confDAO.ultimoTurnoAtualizado()
						));
	}
	
}
