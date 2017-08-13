package br.com.abevieiramota.gui.wmain;

import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import br.com.abevieiramota.messages.Messages;
import br.com.abevieiramota.model.TipoDezena;
import br.com.abevieiramota.model.TipoLoteria;
import br.com.abevieiramota.model.Turno;
import br.com.abevieiramota.service.preditor.PredicaoService;

public class BPredizer extends JButton {

	private static final long serialVersionUID = -1197515908558191308L;

	private static final String FILE_NAME_PREDICAO_POR_TURNO = Messages
			.getString("ui.b_predizer.file_predicao_turno.name");
	private static final String FILE_NAME_PREDICAO_RESUMIDO = Messages
			.getString("ui.b_predizer.file_predicao_resumido.name");
	private static final String LABEL = Messages.getString("ui.b_predizer.label");
	private static final String MSG_ERRO = Messages.getString("ui.erro_generico");

	private static final List<EnumSet<Turno>> TURNOS_A_PREDIZER = Collections.unmodifiableList(
			Lists.newArrayList(EnumSet.of(Turno.DIURNO), EnumSet.of(Turno.NOTURNO), EnumSet.allOf(Turno.class)));
	private static final List<TipoDezena> TIPOS_DEZENA_A_PREDIZER = Collections
			.unmodifiableList(Lists.newArrayList(TipoDezena.values()));

	public BPredizer(JFrame window) {
		super(LABEL);

		addActionListener(new PredizerActionListener(Configuracao.getTipoLoteria()));
	}

	private static class PredizerActionListener implements ActionListener {

		private static final Charset DEFAULT_CHARSET = Charsets.UTF_8;
		private TipoLoteria tipo;

		public PredizerActionListener(TipoLoteria tipoLoteria) {
			checkNotNull(tipoLoteria);

			this.tipo = tipoLoteria;
		}

		public void actionPerformed(ActionEvent e) {
			try {
				for (TipoDezena tipoDezena : TIPOS_DEZENA_A_PREDIZER) {
					PredicaoService predicaoService = new PredicaoService(tipoDezena, this.tipo);

					for (EnumSet<Turno> turnosAPredizer : TURNOS_A_PREDIZER) {
						gerarArquivoPorTurno(tipoDezena, predicaoService, turnosAPredizer);
					}

					gerarArquivoResumido(tipoDezena, predicaoService);
				}

			} catch (Exception ex) {
				ex.printStackTrace();

				JOptionPane.showMessageDialog(null, MSG_ERRO);
			}
		}

		private void gerarArquivoResumido(TipoDezena tipoDezena, PredicaoService predicaoService)
				throws IOException, SQLException {
			String filenameResumido = String.format(FILE_NAME_PREDICAO_RESUMIDO, Configuracao.getTipoLoteria(),
					tipoDezena);

			File fileResumido = new File(filenameResumido);

			String contentResumido = predicaoService.predicoesParaImpressaoResumida();

			Files.asCharSink(fileResumido, DEFAULT_CHARSET).write(contentResumido);
		}

		private void gerarArquivoPorTurno(TipoDezena tipoDezena, PredicaoService predicaoService,
				EnumSet<Turno> turnosAPredizer) throws SQLException, IOException {
			String contentPorTurno = predicaoService.predicoesParaImpressaoCompleta(turnosAPredizer);

			File filenamePorTurno = new File(String.format(FILE_NAME_PREDICAO_POR_TURNO, Configuracao.getTipoLoteria(),
					tipoDezena, turnosAPredizer));
			
			Files.asCharSink(filenamePorTurno, DEFAULT_CHARSET).write(contentPorTurno);
		}
	}

}
