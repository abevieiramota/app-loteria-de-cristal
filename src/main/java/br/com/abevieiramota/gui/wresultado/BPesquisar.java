package br.com.abevieiramota.gui.wresultado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.abevieiramota.gui.wmain.Parametros;
import br.com.abevieiramota.messages.Messages;
import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.Turno;
import br.com.abevieiramota.model.dao.Dao;

public class BPesquisar extends JButton {

	private static final long serialVersionUID = 1L;

	private static final String LABEL = Messages.getString("ui.b_pesquisar.label");
	private static final String MSG_RESULTADO_NAO_EXISTE = Messages.getString("ui.sucesso.resultado_nao_existe");
	

	public BPesquisar(JTextField dataField, final Map<Turno, JTextArea> fResultados,
			final Map<Turno, BDeletar> bResultados) {
		super(LABEL);

		final JTextField dataFieldF = dataField;

		addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				Dao dao = new Dao();
				String data = dataFieldF.getText();
				List<Resultado> resultadoByData = dao.resultadoByData(data, Parametros.getLoteria());

				for (BDeletar jButton : bResultados.values()) {
					jButton.setData(data);
				}
				
				for(JTextArea jtextArea: fResultados.values()) {
					jtextArea.setText(MSG_RESULTADO_NAO_EXISTE);
				}

				for (Resultado resultado : resultadoByData) {

					JTextArea jTextArea = fResultados.get(resultado.getTurno());
					jTextArea
							.setText(String.format("\t%s \n%s", resultado.getTurno().toString(), resultado.toTable()));
				}
			}
		});
	}
}
