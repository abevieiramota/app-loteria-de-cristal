package br.com.abevieiramota.service.preditor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.Resultado.Bicho;
import br.com.abevieiramota.model.Resultado.Premio;
import br.com.abevieiramota.model.dao.ResultadoDAO;

/**
 * TODO: documentar
 * 
 * @author abevi
 *
 */
public class Predicao {

	public enum TurnosAPredizer {
		DIURNO, NOTURNO, DIURNO_E_NOTURNO
	}

	private final static String LINE_SEPARATOR = System.getProperty("line.separator");

	private final static String FORMATO_ARQUIVO_RESUMIDO_HEADER = "| 1  | 2  | 3  | 4  | 5  | 6  | 7  | 8  | 9  | 10 |"
			+ LINE_SEPARATOR;

	private final static String FORMATO_ARQUIVO_COMPLETO_LINHA = "%10s\t%10s\t%10s\t%10s\t%10s" + LINE_SEPARATOR;

	private Premio premio;
	private Stack<Resultado> maisVelhos;

	private static List<Predicao> predicoes(TurnosAPredizer turnosAConsiderar) throws SQLException {

		ResultadoDAO resDAO = new ResultadoDAO();
		List<Resultado> resultados = null;
		switch (turnosAConsiderar) {
		case DIURNO:
			resultados = resDAO.allDiurno();
			break;
		case NOTURNO:
			resultados = resDAO.allNoturno();
			break;
		case DIURNO_E_NOTURNO:
			resultados = resDAO.all();
			break;
		default:
			throw new IllegalArgumentException();
		}

		List<Predicao> predicoes = new ArrayList<>();
		for (Premio premio : Premio.values()) {
			Predicao predicao = new Predicao(premio);
			for (Resultado res : resultados) {
				predicao.atualizar(res);
			}

			predicoes.add(predicao);
		}

		return predicoes;
	}

	private static String predicoesParaImpressaoCompletaRow(List<Predicao> predicoes) {

		Predicao p1 = predicoes.get(0);
		Predicao p2 = predicoes.get(1);
		Predicao p3 = predicoes.get(2);
		Predicao p4 = predicoes.get(3);
		Predicao p5 = predicoes.get(4);

		StringBuilder sb = new StringBuilder();
		String primeiraLinha = String.format(FORMATO_ARQUIVO_COMPLETO_LINHA, p1.premio.ordinal() + 1,
				p2.premio.ordinal() + 1, p3.premio.ordinal() + 1, p4.premio.ordinal() + 1, p5.premio.ordinal() + 1);
		sb.append(primeiraLinha);
		sb.append(LINE_SEPARATOR);

		for (int i = 0; i < 25; i++) {
			String linhaDePremio = String.format(FORMATO_ARQUIVO_COMPLETO_LINHA,
					String.format("%2s %10s",
							Bicho.fromResultado(p1.ordenadoPorVelhice()[i].premio(p1.premio)).ordinal() + 1,
							p1.ordenadoPorVelhice()[i].data),
					String.format("%2s %10s",
							Bicho.fromResultado(p2.ordenadoPorVelhice()[i].premio(p2.premio)).ordinal() + 1,
							p2.ordenadoPorVelhice()[i].data),
					String.format("%2s %10s",
							Bicho.fromResultado(p3.ordenadoPorVelhice()[i].premio(p3.premio)).ordinal() + 1,
							p3.ordenadoPorVelhice()[i].data),
					String.format("%2s %10s",
							Bicho.fromResultado(p4.ordenadoPorVelhice()[i].premio(p4.premio)).ordinal() + 1,
							p4.ordenadoPorVelhice()[i].data),
					String.format("%2s %10s",
							Bicho.fromResultado(p5.ordenadoPorVelhice()[i].premio(p5.premio)).ordinal() + 1,
							p5.ordenadoPorVelhice()[i].data));
			sb.append(linhaDePremio);
		}
		sb.append(LINE_SEPARATOR);
		sb.append(LINE_SEPARATOR);

		return sb.toString();
	}

	public static String predicoesParaImpressaoCompleta(TurnosAPredizer turnos) throws SQLException {
		List<Predicao> predicoes = predicoes(turnos);
		StringBuilder sb = new StringBuilder();
		sb.append(predicoesParaImpressaoCompletaRow(predicoes.subList(0, 5)));
		sb.append(LINE_SEPARATOR);
		sb.append(predicoesParaImpressaoCompletaRow(predicoes.subList(5, 10)));

		// for(Predicao predicao: predicoes) {
		// sb.append(predicao.tabelaCompleta());
		// sb.append(LINE_SEPARATOR);
		// }

		return sb.toString();
	}

	// TODO: aqui rebaixei meu nível de junior para baby
	public static String predicoesParaImpressaoResumida() throws SQLException {

		List<Predicao> predicoesDiurno = predicoes(TurnosAPredizer.DIURNO);
		List<Predicao> predicoesNoturno = predicoes(TurnosAPredizer.NOTURNO);
		List<Predicao> predicoesDiuNoturno = predicoes(TurnosAPredizer.DIURNO_E_NOTURNO);

		StringBuilder sb = new StringBuilder();

		// diurno
		sb.append("DIURNO");
		sb.append(LINE_SEPARATOR);
		sb.append(FORMATO_ARQUIVO_RESUMIDO_HEADER);
		sb.append(LINE_SEPARATOR);

		for (int i = 0; i < 4; i++) {
			for (Predicao predicao : predicoesDiurno) {
				sb.append(String.format("| %02d ",
						Bicho.fromResultado(predicao.ordenadoPorVelhice()[i].premio(predicao.premio)).ordinal() + 1));
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
				sb.append(String.format("| %02d ",
						Bicho.fromResultado(predicao.ordenadoPorVelhice()[i].premio(predicao.premio)).ordinal() + 1));
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
				sb.append(String.format("| %02d ",
						Bicho.fromResultado(predicao.ordenadoPorVelhice()[i].premio(predicao.premio)).ordinal() + 1));
			}
			sb.append("|" + LINE_SEPARATOR);
		}
		sb.append(LINE_SEPARATOR);

		return sb.toString();
	}

	public Predicao(Premio premio) {
		this.premio = premio;
		maisVelhos = new Stack<Resultado>();
	}

	public void atualizar(Resultado resultado) {
		String valorDoPremio = resultado.premio(this.premio);
		Bicho bichoDoPremio = Bicho.fromResultado(valorDoPremio);

		Iterator<Resultado> maisVelhosIter = this.maisVelhos.iterator();

		while (maisVelhosIter.hasNext()) {
			Resultado maisVelhosLoopElem = maisVelhosIter.next();
			Bicho bichoLoopElem = Bicho.fromResultado(maisVelhosLoopElem.premio(this.premio));
			if (bichoDoPremio.equals(bichoLoopElem)) {
				maisVelhosIter.remove();
			}
		}

		this.maisVelhos.push(resultado);
	}

	public Resultado[] ordenadoPorVelhice() {
		return maisVelhos.toArray(new Resultado[this.maisVelhos.size()]);
	}

	public String tabelaCompleta() {
		StringBuilder sb = new StringBuilder();
		sb.append("\t");
		sb.append(this.premio.ordinal() + 1);
		sb.append(LINE_SEPARATOR + LINE_SEPARATOR);
		for (Resultado res : this.maisVelhos) {
			sb.append(String.format("%20s %10s%s", Bicho.fromResultado(res.premio(this.premio)), res.data,
					LINE_SEPARATOR));
		}

		return sb.toString();
	}

}
