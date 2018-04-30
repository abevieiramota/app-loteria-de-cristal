package br.com.abevieiramota.gui.wmain;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.JComboBox;

import br.com.abevieiramota.model.Loteria;
import br.com.abevieiramota.model.dao.EMF;

public class ComboTipoLoteria extends JComboBox<Loteria> {

	private static final long serialVersionUID = -579854775706297019L;

	public ComboTipoLoteria() {

		EntityManager manager = EMF.buildManager();

		List<Loteria> loterias = manager.createQuery("from Loteria", Loteria.class).getResultList();
		for (Loteria loteria : loterias) {
			addItem(loteria);
		}
		
		manager.close();

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
