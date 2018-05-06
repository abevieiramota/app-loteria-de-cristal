package br.com.abevieiramota.gui.wresultado;

import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import br.com.abevieiramota.gui.wmain.Parametros;
import br.com.abevieiramota.messages.Messages;
import br.com.abevieiramota.model.Turno;
import br.com.abevieiramota.model.dao.Dao;

public class BDeletar extends JButton {

	private static final long serialVersionUID = 1L;
	private static final String LABEL = Messages.getString("ui.b_deletar.label");
	private static final String MSG_RESULTADO_DELETADO = Messages.getString("ui.sucesso.resultado_deletado");

	private final Turno turno;
	private String data;
	private JTextArea jTextArea;

	public BDeletar(Turno turno, JTextArea jTextArea) {
		super(LABEL);

		this.turno = turno;
		this.jTextArea = jTextArea;

		addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				Dao dao = new Dao();

				dao.deletarResultado(Parametros.getLoteria(), BDeletar.this.turno, BDeletar.this.data);

				BDeletar.this.jTextArea.setText("");
				
				JOptionPane.showMessageDialog(null, MSG_RESULTADO_DELETADO);
			}
		});
	}

	public void setData(String data) {
		checkNotNull(data);

		this.data = data;
	}

}
