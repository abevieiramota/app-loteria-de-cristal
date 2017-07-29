package br.com.abevieiramota.gui;

import javax.swing.JFrame;

public abstract class DefaultWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JFrame anterior;
	
	public DefaultWindow(String title) {
		try {
			initFields();
		} catch(Exception ex) {
			// TODO: tratar
			ex.printStackTrace();
		}
		setTitle(title);
		initGUI();
		pack();
		setSize();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public DefaultWindow(String title, JFrame anterior) {
		this(title);
		this.anterior = anterior;
	}

	public void voltar() {
		this.setVisible(false);
		this.anterior.setVisible(true);
	}
	
	protected abstract void initGUI();
	
	protected abstract void initFields() throws Exception;
	
	protected void setSize() {
		
	}
	
}
