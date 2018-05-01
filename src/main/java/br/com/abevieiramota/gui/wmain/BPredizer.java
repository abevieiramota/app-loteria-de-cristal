package br.com.abevieiramota.gui.wmain;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.google.common.base.Charsets;
import com.google.common.collect.Sets;
import com.google.common.io.Files;

import br.com.abevieiramota.messages.Messages;
import br.com.abevieiramota.model.Dezena;
import br.com.abevieiramota.model.Turno;
import br.com.abevieiramota.model.dao.Dao;
import br.com.abevieiramota.service.preditor.PredicaoService;

public class BPredizer extends JButton {

	private static final long serialVersionUID = -1197515908558191308L;

	private static final String FILE_NAME_PREDICAO_POR_TURNO = Messages
			.getString("ui.b_predizer.file_predicao_turno.name");
	private static final String FILE_NAME_PREDICAO_RESUMIDO = Messages
			.getString("ui.b_predizer.file_predicao_resumido.name");
	private static final String LABEL = Messages.getString("ui.b_predizer.label");
	private static final String MSG_ERRO = Messages.getString("ui.erro_generico");

	public BPredizer(JFrame window) {
		super(LABEL);

		addActionListener(new PredizerActionListener());
	}

	private static class PredizerActionListener implements ActionListener {

		private static final Charset DEFAULT_CHARSET = Charsets.UTF_8;

		public void actionPerformed(ActionEvent e) {

			Dao dao = new Dao();

			try {
				List<Dezena> dezenas = dao.dezenas();
				List<Turno> turnosDaLoteria = dao.turnosDaLoteria(Parametros.getLoteria());

				Set<Set<Turno>> combinacoes1Turnos = Sets.combinations(Sets.newHashSet(turnosDaLoteria), 1);
				Set<Set<Turno>> combinacoes2Turnos = Sets.combinations(Sets.newHashSet(turnosDaLoteria), 2);

				// Set<Set<Turno>> combinacoesDeTurnos = Sets.union(combinacoes1Turnos,
				// combinacoes2Turnos);
				Set<Set<Turno>> combinacoesDeTurnos = Sets.filter(Sets.powerSet(Sets.newHashSet(turnosDaLoteria)),
						s -> !s.isEmpty());

				for (Dezena dezena : dezenas) {
					PredicaoService predicaoService = new PredicaoService(dezena, Parametros.getLoteria());

					for (Set<Turno> turnosAPredizer : combinacoesDeTurnos) {
						gerarArquivoPorTurnos(dezena, predicaoService, turnosAPredizer);
					}

					gerarArquivoResumido(dezena, predicaoService, combinacoesDeTurnos);
				}

			} catch (Exception ex) {
				ex.printStackTrace();

				JOptionPane.showMessageDialog(null, MSG_ERRO);
			}
		}

		private void gerarArquivoResumido(Dezena dezena, PredicaoService predicaoService,
				Set<Set<Turno>> combinacoesDeTurnos) throws IOException, SQLException {
			String filenameResumido = String.format(FILE_NAME_PREDICAO_RESUMIDO, Parametros.getLoteria(), dezena);

			File fileResumido = new File(filenameResumido);

			String contentResumido = predicaoService.predicoesParaImpressaoResumida(combinacoesDeTurnos);

			Files.asCharSink(fileResumido, DEFAULT_CHARSET).write(contentResumido);
		}

		private void gerarArquivoPorTurnos(Dezena dezena, PredicaoService predicaoService, Set<Turno> turnosAPredizer)
				throws SQLException, IOException {
			checkNotNull(turnosAPredizer);
			checkArgument(!turnosAPredizer.isEmpty());

			String contentPorTurno = predicaoService.predicoesParaImpressaoCompleta(turnosAPredizer);

			File filenamePorTurno = new File(
					String.format(FILE_NAME_PREDICAO_POR_TURNO, Parametros.getLoteria(), dezena, turnosAPredizer));

			Files.asCharSink(filenamePorTurno, DEFAULT_CHARSET).write(contentPorTurno);
		}
	}

}
