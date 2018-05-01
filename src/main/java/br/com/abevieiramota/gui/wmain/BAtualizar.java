package br.com.abevieiramota.gui.wmain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import br.com.abevieiramota.messages.Messages;
import br.com.abevieiramota.service.LoteriaService;

public class BAtualizar extends JButton {

	private static final long serialVersionUID = 4206924266248524061L;

	private static final String LABEL = Messages.getString("ui.b_atualizar.text");
	private static final String MSG_ERRO = Messages.getString("ui.erro_generico");
	private static final String MSG_ERRO_SITE_INDISPONIVEL = Messages
			.getString("ui.b_atualizar.erro.site_indisponivel");

	private static final String MSG_ATUALIZACAO_COMPLETA = Messages.getString("ui.b_atualizar.sucesso");


	public BAtualizar(final LabelUltimaAtualizacao lStatusBar) throws ClassNotFoundException {
		super(LABEL);

		addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					LoteriaService service = new LoteriaService();

					service.atualizar(Parametros.getLoteria());
					
					lStatusBar.atualiza();
					
					JOptionPane.showMessageDialog(null, MSG_ATUALIZACAO_COMPLETA);
				} catch (IOException ex) {
					ex.printStackTrace();

					JOptionPane.showMessageDialog(null, MSG_ERRO_SITE_INDISPONIVEL);
				} catch (Exception ex) {
					ex.printStackTrace();

					JOptionPane.showMessageDialog(null, MSG_ERRO + " " + ex.getMessage());
				}
			}
		});
	}

}
