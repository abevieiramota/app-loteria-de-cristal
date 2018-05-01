package br.com.abevieiramota.gui.wadicionar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import br.com.abevieiramota.gui.wmain.Parametros;
import br.com.abevieiramota.messages.Messages;
import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.Resultado.ResultadoBuilder;
import br.com.abevieiramota.model.Turno;
import br.com.abevieiramota.model.dao.Dao;

public class BSalvar extends JButton {

	private static final String MSG_ERRO_JA_EXISTE_RESULTADO = Messages.getString("ui.erro.resultado_ja_existe");
	private static final String MSG_SUCESSO_ADICIONAR = Messages.getString("ui.sucesso.resultado_adicionado");

	private static final long serialVersionUID = 1L;

	private static final String LABEL = Messages.getString("ui.b_salvar.label");

	public BSalvar(WAdicionar wAdicionar) {
		super(LABEL);

		addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String data = wAdicionar.getData();
				Turno turno = wAdicionar.getTurno();
				String[] premios = wAdicionar.getPremios();

				Dao dao = new Dao();
				if (dao.existsResultado(data, turno, Parametros.getLoteria())) {
					JOptionPane.showMessageDialog(null,
							String.format(MSG_ERRO_JA_EXISTE_RESULTADO, Parametros.getLoteria(), data, turno));
					return;
				}

				try {
					Resultado resultado = new ResultadoBuilder().data(data).premios(premios).turno(turno)
							.loteria(Parametros.getLoteria()).build();

					dao.persistir(resultado);
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(wAdicionar, ex.getMessage());
					return;
				}

				JOptionPane.showMessageDialog(null, MSG_SUCESSO_ADICIONAR);
			}
		});
	}
}
