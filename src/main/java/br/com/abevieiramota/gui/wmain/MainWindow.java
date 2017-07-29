package br.com.abevieiramota.gui.wmain;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import br.com.abevieiramota.gui.DefaultWindow;

public class MainWindow extends DefaultWindow {
	
	private static final long serialVersionUID = -759970510148809073L;
	private static final String TITLE = "Loteria da sorte";
	private JLabel lStatusBar;
	private JButton bBuscarResultado;
	private JButton bAtualizar;
	private JButton bPredizer;
	
	public MainWindow() {
		super(TITLE);
	}
	
	public void initGUI() {
		JPanel pButoes = new JPanel();
		pButoes.setLayout(new BoxLayout(pButoes, BoxLayout.Y_AXIS));
		pButoes.add(bBuscarResultado);
		pButoes.add(Box.createRigidArea(new Dimension(0, 10)));
		pButoes.add(bAtualizar);
		pButoes.add(Box.createRigidArea(new Dimension(0, 10)));
		pButoes.add(bPredizer);
		pButoes.setBorder(new EmptyBorder(new Insets(20, 60, 20, 60)));
		
		add(pButoes);
		add(lStatusBar, BorderLayout.SOUTH);
	}
	
	public void initFields() throws ClassNotFoundException, SQLException {
		lStatusBar = new LabelUltimaAtualizacao();
		bBuscarResultado = new BBuscarResultado(this);
		bAtualizar = new BAtualizar();
		bPredizer = new BPredizer(this);
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	        	MainWindow ex = new MainWindow();
	            ex.setVisible(true);
	        }
	    });
	}

}
