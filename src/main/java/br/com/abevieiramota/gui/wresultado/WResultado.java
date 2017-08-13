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
import br.com.abevieiramota.gui.WDefault;
import br.com.abevieiramota.messages.Messages;

public class WResultado extends WDefault {

	private static final String DATE_FORMAT_EXAMPLE = Messages.getString("ui.date.example");
	private static final String DATE_MASK = Messages.getString("ui.date.mask");
	private static final long serialVersionUID = 1L;
	private static final String TITLE = Messages.getString("ui.w_resultado.title");
	
	private JButton bVoltar;
	private JFormattedTextField fData;
	private JButton bPesquisar;
	private JTextArea fResultadoDiurno;
	private JTextArea fResultadoNoturno;

	public WResultado(JFrame anterior) {
		super(TITLE, anterior);

		setResizable(false);
	}

	protected void initGUI() {
		JPanel pTopo = new JPanel();
		pTopo.add(this.fData);
		pTopo.add(Box.createRigidArea(new Dimension(10, 0)));
		pTopo.add(this.bPesquisar);
		pTopo.add(Box.createRigidArea(new Dimension(10, 0)));
		pTopo.add(this.bVoltar);
		pTopo.setBorder(new EmptyBorder(new Insets(20, 60, 20, 60)));

		JPanel pMid = new JPanel();
		pMid.setLayout(new BoxLayout(pMid, BoxLayout.X_AXIS));

		pMid.add(this.fResultadoDiurno);
		pMid.add(Box.createRigidArea(new Dimension(20, 20)));
		pMid.add(this.fResultadoNoturno);
		pMid.setBorder(new EmptyBorder(new Insets(20, 60, 20, 60)));

		add(pTopo, BorderLayout.NORTH);
		add(pMid, BorderLayout.SOUTH);
	}

	protected void initFields() throws Exception {
		this.bVoltar = new BVoltar(this);
		
		this.fResultadoDiurno = new JTextArea();
		this.fResultadoDiurno.setPreferredSize(new Dimension(300, 300));
		
		this.fResultadoNoturno = new JTextArea();
		this.fResultadoNoturno.setPreferredSize(new Dimension(300, 300));
		
		this.fData = new JFormattedTextField(new MaskFormatter(DATE_MASK));
		
		this.bPesquisar = new BPesquisar(fData, fResultadoDiurno, fResultadoNoturno);
		
		this.fData.setToolTipText(DATE_FORMAT_EXAMPLE);
		
		this.fData.setPreferredSize(new Dimension(70, 20));
	}

}
