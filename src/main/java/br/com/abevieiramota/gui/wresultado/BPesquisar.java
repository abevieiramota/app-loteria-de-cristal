package br.com.abevieiramota.gui.wresultado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.abevieiramota.gui.wmain.Configuracao;
import br.com.abevieiramota.messages.Messages;
import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.dao.ResultadoDao;

public class BPesquisar extends JButton {

	private static final long serialVersionUID = 1L;

	private ResultadoDao resDAO;

	private static final String LABEL = Messages.getString("ui.b_pesquisar.label");
	private static final String MSG_ERRO = Messages.getString("ui.erro_generico");

	public BPesquisar(JTextField dataField, JTextArea resDiurno, JTextArea resNoturno)
			throws ClassNotFoundException, SQLException {
		super(LABEL);

		this.resDAO = new ResultadoDao();

		final JTextField dataFieldF = dataField;
		final JTextArea resFieldDiurnoF = resDiurno;
		final JTextArea resFieldNoturnoF = resNoturno;

		addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String data = dataFieldF.getText();
				try {
					List<Resultado> byData = BPesquisar.this.resDAO.byData(data, Configuracao.getTipoLoteria());
					if (byData.size() > 0) {
						resFieldDiurnoF.setText(String.format("\t DIURNO\n%s", byData.get(0).toTable()));
					} else {
						resFieldDiurnoF.setText(String.format("\t DIURNO\n%s", "Sem resultado"));
					}
					if (byData.size() > 1) {
						resFieldNoturnoF.setText(String.format("\t NOTURNO\n%s", byData.get(1).toTable())); 
					} else {
						resFieldNoturnoF.setText(String.format("\t NOTURNO\n%s", "Sem resultado"));
					}
				} catch (SQLException ex) {
					ex.printStackTrace();

					JOptionPane.showMessageDialog(null, MSG_ERRO);
				}
			}
		});
	}
}
