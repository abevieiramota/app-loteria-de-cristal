package br.com.abevieiramota.gui.wpredizer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import br.com.abevieiramota.gui.BVoltar;
import br.com.abevieiramota.gui.DefaultWindow;
import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.Resultado.Premio;
import br.com.abevieiramota.model.dao.ResultadoDAO;
import br.com.abevieiramota.service.preditor.Predicao;

public class PredicaoWindow extends DefaultWindow {
	
	private static final long serialVersionUID = 1L;

	private ResultadoDAO resDAO;
	
	private static final String TITLE = "Predi��o";
	private JButton bVoltar;
	
	private JTextArea f1premio;
	private JTextArea f2premio;
	private JTextArea f3premio;
	private JTextArea f4premio;
	private JTextArea f5premio;
	private JTextArea f6premio;
	private JTextArea f7premio;
	private JTextArea f8premio;
	private JTextArea f9premio;
	private JTextArea f10premio;
	
	private int turnosAConsiderar;
	
	
	// TODO: refatorar pra enum?
	// turnos = 0 >> apenas diurno
	// = 1 >> apenas noturno
	// = 2 >> all
	public PredicaoWindow(JFrame anterior, Integer turnos) throws ClassNotFoundException, SQLException {
		super(TITLE, anterior);
		this.turnosAConsiderar = turnos;
		setResizable(false);
	}

	protected void initGUI() {
		JPanel pTopo = new JPanel();
		pTopo.add(bVoltar);
		pTopo.setBorder(new EmptyBorder(new Insets(20, 60, 20, 60)));
		

		JPanel pMid = new JPanel();
		JPanel pMid1 = new JPanel();
		JPanel pMid2 = new JPanel();
		pMid.add(pMid1);
		pMid.add(pMid2);
		
		pMid.setLayout(new BoxLayout(pMid, BoxLayout.Y_AXIS));
		pMid1.setLayout(new BoxLayout(pMid1, BoxLayout.X_AXIS));
		pMid2.setLayout(new BoxLayout(pMid2, BoxLayout.X_AXIS));
		
		pMid1.add(f1premio);
		pMid1.add(Box.createRigidArea(new Dimension(20, 20)));
		pMid1.add(f2premio);
		pMid1.add(Box.createRigidArea(new Dimension(20, 20)));
		pMid1.add(f3premio);
		pMid1.add(Box.createRigidArea(new Dimension(20, 20)));
		pMid1.add(f4premio);
		pMid1.add(Box.createRigidArea(new Dimension(20, 20)));
		pMid1.add(f5premio);
		pMid1.add(Box.createRigidArea(new Dimension(20, 20)));
		pMid2.add(f6premio);
		pMid2.add(Box.createRigidArea(new Dimension(20, 20)));
		pMid2.add(f7premio);
		pMid2.add(Box.createRigidArea(new Dimension(20, 20)));
		pMid2.add(f8premio);
		pMid2.add(Box.createRigidArea(new Dimension(20, 20)));
		pMid2.add(f9premio);
		pMid2.add(Box.createRigidArea(new Dimension(20, 20)));
		pMid2.add(f10premio);
		
		pMid1.setBorder(new EmptyBorder(new Insets(20, 60, 20, 60)));
		pMid2.setBorder(new EmptyBorder(new Insets(20, 60, 20, 60)));
		
		add(pTopo, BorderLayout.NORTH);
		add(pMid, BorderLayout.SOUTH);
	}

	protected void initFields() throws Exception {
		resDAO = new ResultadoDAO();
		bVoltar = new BVoltar(this);
		f1premio = new JTextArea();
		f1premio.setPreferredSize(new Dimension(300, 450));
		f2premio = new JTextArea();
		f2premio.setPreferredSize(new Dimension(300, 450));
		f3premio = new JTextArea();
		f3premio.setPreferredSize(new Dimension(300, 450));
		f4premio = new JTextArea();
		f4premio.setPreferredSize(new Dimension(300, 450));
		f5premio = new JTextArea();
		f5premio.setPreferredSize(new Dimension(300, 450));
		f6premio = new JTextArea();
		f6premio.setPreferredSize(new Dimension(300, 450));
		f7premio = new JTextArea();
		f7premio.setPreferredSize(new Dimension(300, 450));
		f8premio = new JTextArea();
		f8premio.setPreferredSize(new Dimension(300, 450));
		f9premio = new JTextArea();
		f9premio.setPreferredSize(new Dimension(300, 450));
		f10premio = new JTextArea();
		f10premio.setPreferredSize(new Dimension(300, 450));
		
	}

	public void preencheFields() throws ClassNotFoundException, SQLException {
		List<Resultado> resultados = null;
		switch(turnosAConsiderar) {
		case 0:
			resultados = resDAO.allDiurno();
			break;
		case 1:
			resultados = resDAO.allNoturno();
			break;
		case 2:
			resultados = resDAO.all();
			break;
			default:
		}
		List<Predicao> predicoes = new ArrayList<Predicao>();
		for(Premio premio: Premio.values()) {
			Predicao predicao = new Predicao(premio);
			for(Resultado res: resultados) {
				predicao.atualizar(res);
			}
			predicoes.add(predicao);
		}
		// TODO: refatore-me por favor
		f1premio.setText(predicoes.get(0).tabelaCompleta());
		f2premio.setText(predicoes.get(1).tabelaCompleta());
		f3premio.setText(predicoes.get(2).tabelaCompleta());
		f4premio.setText(predicoes.get(3).tabelaCompleta());
		f5premio.setText(predicoes.get(4).tabelaCompleta());
		f6premio.setText(predicoes.get(5).tabelaCompleta());
		f7premio.setText(predicoes.get(6).tabelaCompleta());
		f8premio.setText(predicoes.get(7).tabelaCompleta());
		f9premio.setText(predicoes.get(8).tabelaCompleta());
		f10premio.setText(predicoes.get(9).tabelaCompleta());
		
	}

}