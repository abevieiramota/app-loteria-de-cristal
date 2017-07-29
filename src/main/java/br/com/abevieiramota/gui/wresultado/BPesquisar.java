package br.com.abevieiramota.gui.wresultado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.dao.ResultadoDAO;

public class BPesquisar extends JButton {
	
	private static final long serialVersionUID = 1L;

	private ResultadoDAO resDAO;

	private static final String LABEL = "Buscar Resultado";

	public BPesquisar(JTextField dataField, JTextArea resDiurno, JTextArea resNoturno) throws ClassNotFoundException, SQLException {
		super(LABEL);
		resDAO = new ResultadoDAO();
		
		final JTextField dataFieldF = dataField;
		final JTextArea resFieldDiurnoF = resDiurno;
		final JTextArea resFieldNoturnoF = resNoturno;
		
		addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String data = dataFieldF.getText();
				try {
					List<Resultado> byData = resDAO.byData(data);
					if(byData.size() > 0) {
						resFieldDiurnoF.setText(String.format("\t DIURNO\n%s", byData.get(0).toTable()));
					} else {
						resFieldDiurnoF.setText(String.format("\t DIURNO\n%s", "Sem resultado"));
					}
					if(byData.size() > 1) {
						resFieldNoturnoF.setText(String.format("\t NOTURNO\n%s", byData.get(1).toTable()));
					} else {
						resFieldNoturnoF.setText(String.format("\t NOTURNO\n%s","Sem resultado"));
					}
				} catch(Throwable ex) {
					resFieldDiurnoF.setText("Erro");
					resFieldNoturnoF.setText("Erro");
				}
			}
		});
	}
}
