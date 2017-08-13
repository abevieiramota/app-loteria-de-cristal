package br.com.abevieiramota.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import br.com.abevieiramota.messages.Messages;

public class BVoltar extends JButton {

	private static final long serialVersionUID = 547030000151550106L;
	
	private static final String LABEL = Messages.getString("ui.b_voltar.label");

	public BVoltar(final WDefault frame) {
		super(LABEL);

		addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				frame.voltar();
			}
		});
	}

}
