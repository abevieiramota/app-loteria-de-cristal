package br.com.abevieiramota.gui.wmain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import br.com.abevieiramota.gui.wresultado.ResultadoWindow;

public class BBuscarResultado extends JButton {
	private static final long serialVersionUID = 3959443379130014480L;
	private static final String LABEL = "Buscar Resultado";

	public BBuscarResultado(JFrame window) {
		super(LABEL);
		
		final JFrame anterior = window;
		addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new ResultadoWindow(anterior).setVisible(true);
				anterior.setVisible(false);
			}
		});
	}

}
