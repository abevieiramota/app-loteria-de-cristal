package br.com.abevieiramota.gui.wmain;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JComboBox;

import br.com.abevieiramota.model.Loteria;
import br.com.abevieiramota.model.dao.Dao;

public class ComboLoteria extends JComboBox<Loteria> {

	private static final long serialVersionUID = -579854775706297019L;

	public ComboLoteria() {

		Dao dao = new Dao();
		
		List<Loteria> loterias = dao.allLoteria();

		for (Loteria loteria : loterias) {
			addItem(loteria);
		}

		Parametros.setLoteria(getItemAt(0));

		setSelectedItem(Parametros.getLoteria());

		addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					Parametros.setLoteria((Loteria) e.getItem());
				}
			}
		});
	}

}
