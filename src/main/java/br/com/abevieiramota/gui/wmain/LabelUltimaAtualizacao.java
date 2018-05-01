package br.com.abevieiramota.gui.wmain;

import javax.swing.JLabel;

import br.com.abevieiramota.messages.Messages;
import br.com.abevieiramota.model.Configuracao;

public class LabelUltimaAtualizacao extends JLabel {

	private static final long serialVersionUID = -7369055521816327024L;
	private static final String DEFAULT_TEXT_FORMAT = Messages.getString("ui.label_ultima_atualizacao.text");

	public LabelUltimaAtualizacao() {
		atualiza();
	}

	public void atualiza() {

		Configuracao configuracao = Parametros.getLoteria().getConfiguracao();

		setText(String.format(DEFAULT_TEXT_FORMAT, configuracao.getDataUltimaAtualizacao(),
				configuracao.getTurnoUltimaAtualizacao()));
	}

}
