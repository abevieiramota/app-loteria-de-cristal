package br.com.abevieiramota.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class BVoltar extends JButton {
	
	private static final long serialVersionUID = 1L;
	private static final String LABEL = "Voltar";

	public BVoltar(DefaultWindow frame) {
		super(LABEL);
		
		final DefaultWindow window = frame;
		addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				window.voltar();
			}
		});
	}

}
