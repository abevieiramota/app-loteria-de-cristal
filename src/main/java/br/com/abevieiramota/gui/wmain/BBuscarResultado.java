package br.com.abevieiramota.gui.wmain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import br.com.abevieiramota.gui.wresultado.WResultado;
import br.com.abevieiramota.messages.Messages;

public class BBuscarResultado extends JButton {
	private static final long serialVersionUID = 3959443379130014480L;
	private static final String LABEL = Messages.getString("ui.b_buscar_resultado.text");

	public BBuscarResultado(final JFrame caller) {
		super(LABEL);

		addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WResultado resultadoWindow = new WResultado(caller);
				
				resultadoWindow.setVisible(true);
				caller.setVisible(false);
			}
		});
	}

}
