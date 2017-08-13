package br.com.abevieiramota.service.preditor;

import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import br.com.abevieiramota.model.Bicho;
import br.com.abevieiramota.model.Premio;
import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.TipoDezena;
import br.com.abevieiramota.model.TipoLoteria;
import br.com.abevieiramota.model.Turno;
import br.com.abevieiramota.model.dao.ResultadoDao;
import br.com.abevieiramota.service.preditor.Predicao.PredicaoBuilder;

public class PredicaoService {
	private final static String LINE_SEPARATOR = System.getProperty("line.separator");

	private final static String FORMATO_ARQUIVO_RESUMIDO_HEADER = "| 1  | 2  | 3  | 4  | 5  | 6  | 7  | 8  | 9  | 10 |"
			+ LINE_SEPARATOR;

	private final static String FORMATO_ARQUIVO_COMPLETO_LINHA = "%10s\t%10s\t%10s\t%10s\t%10s" + LINE_SEPARATOR;
	private static final String FORMATO_LINHA = "%2s %10s";

	private ResultadoDao resDao;
	private TipoDezena tipoDezena;
	private TipoLoteria tipo;

	public PredicaoService(TipoDezena tipoDezena, TipoLoteria tipo) throws SQLException {
		// TODO: injeção de dependência
		this.resDao = new ResultadoDao();
		this.tipoDezena = tipoDezena;
		this.tipo = tipo;
	}

	private List<Predicao> predicoes(EnumSet<Turno> turnos) throws SQLException {
		checkNotNull(turnos);

		List<Resultado> resultados = this.resDao.all(turnos, this.tipo);

		List<Predicao> predicoes = new ArrayList<>();
		for (Premio premio : Premio.values()) {
			PredicaoBuilder predicaoBuilder = new Predicao.PredicaoBuilder(premio, this.tipoDezena);
			for (Resultado res : resultados) {
				predicaoBuilder.atualizar(res);
			}

			predicoes.add(predicaoBuilder.build());
		}

		return predicoes;
	}

	private static String predicoesParaImpressaoCompletaRow(List<Predicao> predicoes, TipoDezena tipoDezena) {
		checkNotNull(predicoes);

		Predicao p1 = predicoes.get(0);
		Predicao p2 = predicoes.get(1);
		Predicao p3 = predicoes.get(2);
		Predicao p4 = predicoes.get(3);
		Predicao p5 = predicoes.get(4);

		StringBuilder sb = new StringBuilder();
		String primeiraLinha = String.format(FORMATO_ARQUIVO_COMPLETO_LINHA, p1.getPremio().ordinal() + 1,
				p2.getPremio().ordinal() + 1, p3.getPremio().ordinal() + 1, p4.getPremio().ordinal() + 1,
				p5.getPremio().ordinal() + 1);
		sb.append(primeiraLinha);
		sb.append(LINE_SEPARATOR);

		for (int i = 0; i < 25; i++) {
			StringBuilder sb2 = new StringBuilder();
			for (Predicao predicao : predicoes) {
				sb2.append(String.format("%10s\t", String.format(FORMATO_LINHA,
						Bicho.fromResultado(predicao.ordenadoPorVelhice()[i].premio(predicao.getPremio()), tipoDezena)
								.ordinal() + 1,
						predicao.ordenadoPorVelhice()[i].getData())));
			}
			sb2.delete(sb2.length() - 1, sb2.length());

			sb.append(sb2.toString());
			sb.append(LINE_SEPARATOR);
		}
		sb.append(LINE_SEPARATOR);
		sb.append(LINE_SEPARATOR);

		return sb.toString();
	}

	public String predicoesParaImpressaoCompleta(EnumSet<Turno> turnos) throws SQLException {
		checkNotNull(turnos);

		List<Predicao> predicoes = predicoes(turnos);
		StringBuilder sb = new StringBuilder();
		sb.append(predicoesParaImpressaoCompletaRow(predicoes.subList(0, 5), this.tipoDezena));
		sb.append(LINE_SEPARATOR);
		sb.append(predicoesParaImpressaoCompletaRow(predicoes.subList(5, 10), this.tipoDezena));

		return sb.toString();
	}

	// TODO: aqui rebaixei meu nível de junior para baby
	public String predicoesParaImpressaoResumida() throws SQLException {

		List<Predicao> predicoesDiurno = predicoes(EnumSet.of(Turno.DIURNO));
		List<Predicao> predicoesNoturno = predicoes(EnumSet.of(Turno.NOTURNO));
		List<Predicao> predicoesDiuNoturno = predicoes(EnumSet.allOf(Turno.class));

		StringBuilder sb = new StringBuilder();

		// diurno
		sb.append("DIURNO");
		sb.append(LINE_SEPARATOR);
		sb.append(FORMATO_ARQUIVO_RESUMIDO_HEADER);
		sb.append(LINE_SEPARATOR);

		for (int i = 0; i < 4; i++) {
			for (Predicao predicao : predicoesDiurno) {
				sb.append(String.format("| %02d ", Bicho
						.fromResultado(predicao.ordenadoPorVelhice()[i].premio(predicao.getPremio()), this.tipoDezena)
						.ordinal() + 1));
			}
			sb.append("|" + LINE_SEPARATOR);
		}
		sb.append(LINE_SEPARATOR);

		// noturno
		sb.append("NOTURNO");
		sb.append(LINE_SEPARATOR);
		sb.append(FORMATO_ARQUIVO_RESUMIDO_HEADER);
		sb.append(LINE_SEPARATOR);
		for (int i = 0; i < 4; i++) {
			for (Predicao predicao : predicoesNoturno) {
				sb.append(String.format("| %02d ", Bicho
						.fromResultado(predicao.ordenadoPorVelhice()[i].premio(predicao.getPremio()), this.tipoDezena)
						.ordinal() + 1));
			}
			sb.append("|" + LINE_SEPARATOR);
		}
		sb.append(LINE_SEPARATOR);

		// diurno e noturno
		sb.append("DIURNO E NOTURNO");
		sb.append(LINE_SEPARATOR);
		sb.append(FORMATO_ARQUIVO_RESUMIDO_HEADER);
		sb.append(LINE_SEPARATOR);
		for (int i = 0; i < 4; i++) {
			for (Predicao predicao : predicoesDiuNoturno) {
				sb.append(String.format("| %02d ", Bicho
						.fromResultado(predicao.ordenadoPorVelhice()[i].premio(predicao.getPremio()), this.tipoDezena)
						.ordinal() + 1));
			}
			sb.append("|" + LINE_SEPARATOR);
		}
		sb.append(LINE_SEPARATOR);

		return sb.toString();
	}

	public static String tabelaCompleta(Predicao predicao, TipoDezena tipoDezena) {
		checkNotNull(predicao);
		checkNotNull(tipoDezena);

		StringBuilder sb = new StringBuilder();
		sb.append("\t");
		sb.append(predicao.getPremio().ordinal() + 1);
		sb.append(LINE_SEPARATOR + LINE_SEPARATOR);
		for (Resultado res : predicao.getMaisVelhos()) {
			sb.append(String.format("%20s %10s%s", Bicho.fromResultado(res.premio(predicao.getPremio()), tipoDezena),
					res.getData(), LINE_SEPARATOR));
		}

		return sb.toString();
	}

}