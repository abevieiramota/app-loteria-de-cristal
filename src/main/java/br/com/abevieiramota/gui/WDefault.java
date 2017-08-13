package br.com.abevieiramota.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import br.com.abevieiramota.messages.Messages;

public abstract class WDefault extends JFrame {

	private static final long serialVersionUID = -5894358011469590516L;
	
	private static final String MSG_ERRO = Messages.getString("ui.erro_generico");
	private JFrame anterior;

	public WDefault(String title) {
		try {
			initFields();
		} catch (Exception ex) {
			ex.printStackTrace();

			JOptionPane.showMessageDialog(null, MSG_ERRO);
		}
		setTitle(title);
		initGUI();
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public WDefault(String title, JFrame anterior) {
		this(title);

		this.anterior = anterior;
	}

	public void voltar() {
		setVisible(false);
		this.anterior.setVisible(true);
	}

	protected abstract void initGUI();

	protected abstract void initFields() throws Exception;
}
