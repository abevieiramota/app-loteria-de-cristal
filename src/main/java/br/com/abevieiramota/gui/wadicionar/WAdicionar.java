package br.com.abevieiramota.gui.wadicionar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import br.com.abevieiramota.gui.BVoltar;
import br.com.abevieiramota.gui.WDefault;
import br.com.abevieiramota.gui.wmain.Parametros;
import br.com.abevieiramota.messages.Messages;
import br.com.abevieiramota.model.Turno;

public class WAdicionar extends WDefault {

	private static final String DATE_MASK = Messages.getString("ui.date.mask");
	private static final long serialVersionUID = 1L;
	private static final String TITLE = Messages.getString("ui.w_adicionar.title");

	private JButton bVoltar;
	private JButton bSalvar;
	private JLabel lLoteria;
	private JLabel lLoteriaValue;
	private JLabel lDate;
	private JFormattedTextField fData;
	private ComboTurno comboTurno;
	private JLabel lTurno;

	private JLabel lPremio1;
	private JTextField fPremio1;
	private JLabel lPremio2;
	private JTextField fPremio2;
	private JLabel lPremio3;
	private JTextField fPremio3;
	private JLabel lPremio4;
	private JTextField fPremio4;
	private JLabel lPremio5;
	private JTextField fPremio5;
	private JLabel lPremio6;
	private JTextField fPremio6;
	private JLabel lPremio7;
	private JTextField fPremio7;
	private JLabel lPremio8;
	private JTextField fPremio8;
	private JLabel lPremio9;
	private JTextField fPremio9;
	private JLabel lPremio10;
	private JTextField fPremio10;

	public WAdicionar(JFrame anterior) {
		super(TITLE, anterior);

		setResizable(false);
	}

	protected void initGUI() {
		JPanel pLoteria = new JPanel();
		pLoteria.add(this.lLoteria);
		pLoteria.add(this.lLoteriaValue);

		JPanel pFields = new JPanel();
		pFields.setLayout(new BoxLayout(pFields, BoxLayout.Y_AXIS));

		JPanel pDate = new JPanel();
		pFields.add(pDate);
		pDate.add(this.lDate);
		pDate.add(this.fData);

		JPanel pTurno = new JPanel();
		pFields.add(pTurno);
		pTurno.add(this.lTurno);
		pTurno.add(this.comboTurno);

		JPanel pPremios = new JPanel();
		pPremios.setLayout(new BoxLayout(pPremios, BoxLayout.Y_AXIS));
		pFields.add(pPremios);

		JPanel pPremio1 = new JPanel();
		pPremios.add(pPremio1);
		pPremio1.add(this.lPremio1);
		pPremio1.add(this.fPremio1);

		JPanel pPremio2 = new JPanel();
		pPremios.add(pPremio2);
		pPremio2.add(this.lPremio2);
		pPremio2.add(this.fPremio2);

		JPanel pPremio3 = new JPanel();
		pPremios.add(pPremio3);
		pPremio3.add(this.lPremio3);
		pPremio3.add(this.fPremio3);

		JPanel pPremio4 = new JPanel();
		pPremios.add(pPremio4);
		pPremio4.add(this.lPremio4);
		pPremio4.add(this.fPremio4);

		JPanel pPremio5 = new JPanel();
		pPremios.add(pPremio5);
		pPremio5.add(this.lPremio5);
		pPremio5.add(this.fPremio5);

		JPanel pPremio6 = new JPanel();
		pPremios.add(pPremio6);
		pPremio6.add(this.lPremio6);
		pPremio6.add(this.fPremio6);

		JPanel pPremio7 = new JPanel();
		pPremios.add(pPremio7);
		pPremio7.add(this.lPremio7);
		pPremio7.add(this.fPremio7);

		JPanel pPremio8 = new JPanel();
		pPremios.add(pPremio8);
		pPremio8.add(this.lPremio8);
		pPremio8.add(this.fPremio8);

		JPanel pPremio9 = new JPanel();
		pPremios.add(pPremio9);
		pPremio9.add(this.lPremio9);
		pPremio9.add(this.fPremio9);

		JPanel pPremio10 = new JPanel();
		pPremios.add(pPremio10);
		pPremio10.add(this.lPremio10);
		pPremio10.add(this.fPremio10);

		JPanel pButtons = new JPanel();
		pButtons.add(this.bSalvar);
		pButtons.add(Box.createRigidArea(new Dimension(10, 0)));
		pButtons.add(this.bVoltar);
		pButtons.setBorder(new EmptyBorder(new Insets(20, 60, 20, 60)));

		add(pLoteria, BorderLayout.NORTH);
		add(pFields, BorderLayout.CENTER);
		add(pButtons, BorderLayout.SOUTH);
	}

	protected void initFields() throws Exception {
		this.lLoteria = new JLabel("Loteria: ");
		this.lLoteriaValue = new JLabel(Parametros.getLoteria().toString());
		this.lDate = new JLabel("Data: ");
		this.fData = new JFormattedTextField(new MaskFormatter(DATE_MASK));
		this.fData.setPreferredSize(new Dimension(70, 20));
		this.lTurno = new JLabel("Turno: ");
		this.comboTurno = new ComboTurno();

		this.lPremio1 = new JLabel("Premio 1: ");
		this.fPremio1 = new JTextField(4);
		this.fPremio1.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (fPremio1.getText().length() >= 4)
					e.consume();
			}
		});

		this.lPremio2 = new JLabel("Premio 2: ");
		this.fPremio2 = new JTextField(4);
		this.fPremio2.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (fPremio2.getText().length() >= 4)
					e.consume();
			}
		});

		this.lPremio3 = new JLabel("Premio 3: ");
		this.fPremio3 = new JTextField(4);
		this.fPremio3.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (fPremio3.getText().length() >= 4)
					e.consume();
			}
		});

		this.lPremio4 = new JLabel("Premio 4: ");
		this.fPremio4 = new JTextField(4);
		this.fPremio4.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (fPremio4.getText().length() >= 4)
					e.consume();
			}
		});

		this.lPremio5 = new JLabel("Premio 5: ");
		this.fPremio5 = new JTextField(4);
		this.fPremio5.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (fPremio5.getText().length() >= 4)
					e.consume();
			}
		});

		this.lPremio6 = new JLabel("Premio 6: ");
		this.fPremio6 = new JTextField(4);
		this.fPremio6.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (fPremio6.getText().length() >= 4)
					e.consume();
			}
		});

		this.lPremio7 = new JLabel("Premio 7: ");
		this.fPremio7 = new JTextField(4);
		this.fPremio7.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (fPremio7.getText().length() >= 4)
					e.consume();
			}
		});

		this.lPremio8 = new JLabel("Premio 8: ");
		this.fPremio8 = new JTextField(4);
		this.fPremio8.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (fPremio8.getText().length() >= 4)
					e.consume();
			}
		});

		this.lPremio9 = new JLabel("Premio 9: ");
		this.fPremio9 = new JTextField(4);
		this.fPremio9.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (fPremio9.getText().length() >= 4)
					e.consume();
			}
		});

		this.lPremio10 = new JLabel("Premio 10: ");
		this.fPremio10 = new JTextField(4);
		this.fPremio10.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (fPremio10.getText().length() >= 4)
					e.consume();
			}
		});

		this.bVoltar = new BVoltar(this);
		this.bSalvar = new BSalvar(this);
	}

	public String getData() {
		return this.fData.getText();
	}

	public Turno getTurno() {
		return (Turno) this.comboTurno.getSelectedItem();
	}

	public String[] getPremios() {
		String[] premios = new String[] { this.fPremio1.getText(), this.fPremio2.getText(), this.fPremio3.getText(),
				this.fPremio4.getText(), this.fPremio5.getText(), this.fPremio6.getText(), this.fPremio7.getText(),
				this.fPremio8.getText(), this.fPremio9.getText(), this.fPremio10.getText(), };

		return premios;
	}

}
