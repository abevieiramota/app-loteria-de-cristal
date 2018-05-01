package br.com.abevieiramota.gui.wadicionar;

import java.util.List;

import javax.swing.JComboBox;

import br.com.abevieiramota.gui.wmain.Parametros;
import br.com.abevieiramota.model.Turno;
import br.com.abevieiramota.model.dao.Dao;

public class ComboTurno extends JComboBox<Turno> {

	private static final long serialVersionUID = -579854775706297019L;

	public ComboTurno() {

		Dao dao = new Dao();

		List<Turno> turnos = dao.turnosDaLoteria(Parametros.getLoteria());

		for (Turno turno : turnos) {
			addItem(turno);
		}
	}

}
