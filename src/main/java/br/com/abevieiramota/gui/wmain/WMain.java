package br.com.abevieiramota.gui.wmain;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import br.com.abevieiramota.gui.WDefault;
import br.com.abevieiramota.messages.Messages;

public class WMain extends WDefault {

	private static final long serialVersionUID = -759970510148809073L;
	private static final String TITLE = Messages.getString("ui.w_main.title");
	private LabelUltimaAtualizacao lStatusBar;
	private JButton bBuscarResultado;
	private JButton bAtualizar;
	private JButton bPredizer;
	private JButton bAdicionar;
	private ComboLoteria tipoLoteria;

	public WMain() {
		super(TITLE);
	}

	public void initGUI() {
		JPanel pBotoes = new JPanel();
		pBotoes.setLayout(new BoxLayout(pBotoes, BoxLayout.Y_AXIS));
		pBotoes.add(this.bAdicionar);
		pBotoes.add(Box.createRigidArea(new Dimension(0, 10)));
		pBotoes.add(this.bBuscarResultado);
		pBotoes.add(Box.createRigidArea(new Dimension(0, 10)));
		pBotoes.add(this.bAtualizar);
		pBotoes.add(Box.createRigidArea(new Dimension(0, 10)));
		pBotoes.add(this.bPredizer);

		pBotoes.add(Box.createRigidArea(new Dimension(0, 10)));
		pBotoes.add(this.tipoLoteria);

		pBotoes.setBorder(new EmptyBorder(new Insets(20, 60, 20, 60)));

		add(pBotoes);
		add(this.lStatusBar, BorderLayout.SOUTH);
	}

	public void initFields() throws ClassNotFoundException, SQLException {
		this.tipoLoteria = new ComboLoteria();
		this.lStatusBar = new LabelUltimaAtualizacao();
		this.bBuscarResultado = new BBuscarResultado(this);
		this.bAtualizar = new BAtualizar(this.lStatusBar);
		this.bPredizer = new BPredizer(this);
		this.bAdicionar = new BAdicionar(this);
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				WMain wMain = new WMain();

				wMain.setVisible(true);
			}
		});
	}

}
