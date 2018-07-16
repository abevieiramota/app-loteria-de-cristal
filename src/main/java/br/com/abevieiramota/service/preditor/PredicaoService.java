package br.com.abevieiramota.service.preditor;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.base.Joiner;

import br.com.abevieiramota.model.Bicho;
import br.com.abevieiramota.model.Dezena;
import br.com.abevieiramota.model.Loteria;
import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.Resultado.Premio;
import br.com.abevieiramota.model.Turno;
import br.com.abevieiramota.model.dao.Dao;
import br.com.abevieiramota.service.preditor.Predicao.PredicaoBuilder;

public class PredicaoService {
	private final static String LINE_SEPARATOR = System.getProperty("line.separator");

	private final static String FORMATO_ARQUIVO_RESUMIDO_HEADER = "| 1  | 2  | 3  | 4  | 5  | 6  | 7  | 8  | 9  | 10 |"
			+ LINE_SEPARATOR;

	private final static String FORMATO_ARQUIVO_COMPLETO_LINHA = "%10s\t%10s\t%10s\t%10s\t%10s" + LINE_SEPARATOR;
	private static final String FORMATO_LINHA = "%2s %10s";

	private Dezena dezena;
	private Loteria loteria;

	public PredicaoService(Dezena tipoDezena, Loteria loteria) {
		// TODO: injeção de dependência
		this.dezena = tipoDezena;
		this.loteria = loteria;
	}

	private List<Predicao> gerarPredicoes(Set<Turno> turnos) {
		checkNotNull(turnos);

		Dao dao = new Dao();

		List<Resultado> resultados = dao.allResultados(turnos, this.loteria);

		List<Predicao> predicoes = new ArrayList<>();
		for (Premio premio : Premio.values()) {
			PredicaoBuilder predicaoBuilder = new Predicao.PredicaoBuilder(premio, this.dezena);
			for (Resultado res : resultados) {
				predicaoBuilder.atualizar(res);
			}

			predicoes.add(predicaoBuilder.build());
		}

		return predicoes;
	}

	private static String predicoesParaImpressaoCompletaRow(List<Predicao> predicoes, Dezena tipoDezena) {
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
				Resultado[] ordenadoPorVelhice = predicao.ordenadoPorVelhice();
				if (i >= ordenadoPorVelhice.length) {
					sb2.append(String.format("%13s\t", ""));
					continue;
				}
				sb2.append(String.format("%10s\t", String.format(FORMATO_LINHA,
						Bicho.fromResultado(ordenadoPorVelhice[i].premio(predicao.getPremio()), tipoDezena).ordinal()
								+ 1,
						ordenadoPorVelhice[i].getData())));
			}
			if (sb2.length() > 0) {
				sb2.delete(sb2.length() - 1, sb2.length());
			}

			sb.append(sb2.toString());
			sb.append(LINE_SEPARATOR);
		}
		sb.append(LINE_SEPARATOR);
		sb.append(LINE_SEPARATOR);

		return sb.toString();
	}

	public String predicoesParaImpressaoCompleta(Set<Turno> turnos) {
		checkNotNull(turnos);

		List<Predicao> predicoes = gerarPredicoes(turnos);
		StringBuilder sb = new StringBuilder();
		sb.append(predicoesParaImpressaoCompletaRow(predicoes.subList(0, 5), this.dezena));
		sb.append(LINE_SEPARATOR);
		sb.append(predicoesParaImpressaoCompletaRow(predicoes.subList(5, 10), this.dezena));

		return sb.toString();
	}

	public String predicoesParaImpressaoResumida(Set<Set<Turno>> combinacoesDeTurnos) {

		StringBuilder sb = new StringBuilder();

		for (Set<Turno> turnos : combinacoesDeTurnos) {
			List<Predicao> predicoes = gerarPredicoes(turnos);

			sb.append(Joiner.on(" ").join(turnos));
			sb.append(LINE_SEPARATOR);
			sb.append(FORMATO_ARQUIVO_RESUMIDO_HEADER);
			sb.append(LINE_SEPARATOR);

			for (int i = 0; i < 4; i++) {
				for (Predicao predicao : predicoes) {
					Resultado[] ordenadoPorVelhice = predicao.ordenadoPorVelhice();

					if (i >= ordenadoPorVelhice.length) {
						continue;
					}

					sb.append(String.format("| %02d ",
							Bicho.fromResultado(ordenadoPorVelhice[i].premio(predicao.getPremio()), this.dezena)
									.ordinal() + 1));
				}
				sb.append("|" + LINE_SEPARATOR);
			}
			sb.append(LINE_SEPARATOR);
		}

		return sb.toString();
	}

	public static String tabelaCompleta(Predicao predicao, Dezena tipoDezena) {
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
