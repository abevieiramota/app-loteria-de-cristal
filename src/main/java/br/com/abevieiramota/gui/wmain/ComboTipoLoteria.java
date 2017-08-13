package br.com.abevieiramota.gui.wmain;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;

import br.com.abevieiramota.model.TipoLoteria;

public class ComboTipoLoteria extends JComboBox<TipoLoteria> {

	private static final long serialVersionUID = -579854775706297019L;

	public ComboTipoLoteria() {
		super(TipoLoteria.values());

		setSelectedItem(Configuracao.getTipoLoteria());

		addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					Configuracao.setTipoLoteria((TipoLoteria) e.getItem());
				}
			}
		});
	}

}
