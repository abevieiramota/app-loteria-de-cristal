package br.com.abevieiramota.gui.wresultado;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import br.com.abevieiramota.gui.BVoltar;
import br.com.abevieiramota.gui.DefaultWindow;

public class ResultadoWindow extends DefaultWindow {
	
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Resultados";
	private JButton bVoltar;
	private JFormattedTextField fData;
	private JButton bPesquisar;
	
	private JTextArea fResultadoDiurno;
	private JTextArea fResultadoNoturno;
	
	
	public ResultadoWindow(JFrame anterior) {
		super(TITLE, anterior);
		setResizable(false);
	}

	protected void initGUI() {
		JPanel pTopo = new JPanel();
		pTopo.add(fData);
		pTopo.add(Box.createRigidArea(new Dimension(10, 0)));
		pTopo.add(bPesquisar);
		pTopo.add(Box.createRigidArea(new Dimension(10, 0)));
		pTopo.add(bVoltar);
		pTopo.setBorder(new EmptyBorder(new Insets(20, 60, 20, 60)));
		

		JPanel pMid = new JPanel();
		pMid.setLayout(new BoxLayout(pMid, BoxLayout.X_AXIS));
		
		pMid.add(fResultadoDiurno);
		pMid.add(Box.createRigidArea(new Dimension(20, 20)));
		pMid.add(fResultadoNoturno);
		pMid.setBorder(new EmptyBorder(new Insets(20, 60, 20, 60)));
		
		add(pTopo, BorderLayout.NORTH);
		add(pMid, BorderLayout.SOUTH);
	}

	protected void initFields() throws Exception {
		bVoltar = new BVoltar(this);
		fResultadoDiurno = new JTextArea();
		fResultadoDiurno.setPreferredSize(new Dimension(300, 300));
		fResultadoNoturno = new JTextArea();
		fResultadoNoturno.setPreferredSize(new Dimension(300, 300));
		fData = new JFormattedTextField(new MaskFormatter("##/##/####"));
		bPesquisar = new BPesquisar(fData, fResultadoDiurno, fResultadoNoturno);
		fData.setToolTipText("Exemplo: 23/04/2012");
		fData.setPreferredSize(new Dimension(70, 20));
	}

}
