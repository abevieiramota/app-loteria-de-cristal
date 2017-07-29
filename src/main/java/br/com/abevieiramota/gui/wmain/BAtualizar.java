package br.com.abevieiramota.gui.wmain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import br.com.abevieiramota.service.LoteriaService;

public class BAtualizar extends JButton {

	private static final long serialVersionUID = 4206924266248524061L;
	private static final String LABEL = "Atualizar";

	public BAtualizar() throws ClassNotFoundException, SQLException {
		super(LABEL);
		final LoteriaService service = new LoteriaService();

		addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					service.atualizar();
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Site indispon�vel");
				}
			}
		});
	}

}
