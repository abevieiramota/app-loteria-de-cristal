package br.com.abevieiramota.gui.wmain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;

import br.com.abevieiramota.service.preditor.Predicao;
import br.com.abevieiramota.service.preditor.Predicao.TurnosAPredizer;

import com.google.common.io.Files;

public class BPredizer extends JButton {
	private static final long serialVersionUID = -1197515908558191308L;
	private static final String LABEL = "Predizer";

	public BPredizer(JFrame window) {
		super(LABEL);

		addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					for (TurnosAPredizer turnosFinal : TurnosAPredizer.values()) {
						Files.write(Predicao.predicoesParaImpressaoCompleta(turnosFinal).getBytes(),
								new File(String.format("%s.txt", nomePraTurnos(turnosFinal).toString())));
					}
					Files.write(Predicao.predicoesParaImpressaoResumida().getBytes(), new File("Resumido.txt"));

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}

	// TODO: refatorar -> passar para o enum
	private static String nomePraTurnos(TurnosAPredizer turnos) {
		String res = null;
		switch (turnos) {
		case DIURNO:
			res = "Diurno";
			break;
		case NOTURNO:
			res = "Noturno";
			break;
		case DIURNO_E_NOTURNO:
			res = "Diurno e Noturno";
			break;
		}
		return res;
	}

}
