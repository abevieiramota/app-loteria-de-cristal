package br.com.abevieiramota.gui.wmain;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.JLabel;

import br.com.abevieiramota.messages.Messages;
import br.com.abevieiramota.model.Configuracao;
import br.com.abevieiramota.model.dao.EMF;

public class LabelUltimaAtualizacao extends JLabel {

	private static final long serialVersionUID = -7369055521816327024L;
	private static final String DEFAULT_TEXT_FORMAT = Messages.getString("ui.label_ultima_atualizacao.text");

	public LabelUltimaAtualizacao() {
		atualiza();
	}

	public void atualiza() {

		EntityManager manager = EMF.buildManager();

		TypedQuery<Configuracao> query = manager.createQuery("from Configuracao where loteria = :loteria",
				Configuracao.class);
		query.setParameter("loteria", Parametros.getLoteria());

		Configuracao configuracao = query.getSingleResult();
		
		manager.close();

		setText(String.format(DEFAULT_TEXT_FORMAT, configuracao.getDataUltimaAtualizacao(),
				configuracao.getTurnoUltimaAtualizacao()));
	}

}
