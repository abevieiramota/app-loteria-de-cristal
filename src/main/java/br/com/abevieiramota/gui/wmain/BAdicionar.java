package br.com.abevieiramota.gui.wmain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import br.com.abevieiramota.gui.wadicionar.WAdicionar;
import br.com.abevieiramota.messages.Messages;

public class BAdicionar extends JButton {

	private static final long serialVersionUID = -1197515908558191308L;

	private static final String LABEL = Messages.getString("ui.b_adicionar.label");

	public BAdicionar(JFrame caller) {
		super(LABEL);

		addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WAdicionar adicionarWindow = new WAdicionar(caller);

				adicionarWindow.setVisible(true);
				caller.setVisible(false);
			}
		});
	}
}
