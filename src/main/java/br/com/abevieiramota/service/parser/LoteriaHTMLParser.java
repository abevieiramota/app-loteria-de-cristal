package br.com.abevieiramota.service.parser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import br.com.abevieiramota.model.Loteria;
import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.Resultado.Premio;
import br.com.abevieiramota.model.Resultado.ResultadoBuilder;
import br.com.abevieiramota.model.Turno;
import br.com.abevieiramota.model.dao.Dao;
import br.com.abevieiramota.service.parser.Patterns.GrupoDoRegex;

public final class LoteriaHTMLParser {

	public static Set<Resultado> extrairResultadosDoHTMLDaData(String html, String data, Loteria tipoLoteria) {

		Set<Resultado> resultadosDaData = new HashSet<>();

		Set<Resultado> resultados = extrairResultadosDoHTML(html, tipoLoteria);

		for (Resultado resultado : resultados) {
			if (resultado.getData().equals(data)) {
				resultadosDaData.add(resultado);
			}
		}

		return resultadosDaData;
	}

	public static Set<Resultado> extrairResultadosDoHTML(String html, Loteria tipoLoteria) {
		Matcher matchsResultado = Patterns.REGEX_RESULTADO.matcher(html);

		Set<Resultado> resultados = new HashSet<>();
		while (matchsResultado.find()) {
			Resultado resultado = extrairResultadoDoMatcher(matchsResultado, tipoLoteria);
			resultados.add(resultado);
		}

		return resultados;
	}

	private static Resultado extrairResultadoDoMatcher(Matcher matcher, Loteria loteria) {

		String[] premios = new String[Premio.values().length];

		String data = matcher.group(GrupoDoRegex.DATA.grupo);
		String time = matcher.group(GrupoDoRegex.TURNO.grupo);

		Dao dao = new Dao();
		List<Turno> turnosDaLoteria = dao.turnosDaLoteria(loteria);

		Turno turnoDoResultado = Turno.getTurnoByHoraPart(time, turnosDaLoteria);

		for (Premio premio : Premio.values()) {
			premios[premio.ordinal()] = matcher.group(GrupoDoRegex.grupoDoPremio(premio));
		}

		return new ResultadoBuilder().data(data).premios(premios).turno(turnoDoResultado).loteria(loteria).build();
	}
}
