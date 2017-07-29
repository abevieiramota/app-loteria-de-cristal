package br.com.abevieiramota.service.parser;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.Resultado.Premio;
import br.com.abevieiramota.model.Resultado.ResultadoBuilder;
import br.com.abevieiramota.model.Resultado.Turno;
import br.com.abevieiramota.service.parser.Patterns.GrupoDoRegex;

public final class LoteriaHTMLParser {

	public static Set<Resultado> extrairResultadosDoHTMLDaData(String html, String data) {
		
		Set<Resultado> resultadosDaData = new HashSet<>();

		Set<Resultado> resultados = extrairResultadosDoHTML(html);

		for (Resultado resultado : resultados) {
			if (resultado.data.equals(data)) {
				resultadosDaData.add(resultado);
			}
		}

		return resultadosDaData;
	}

	public static Set<Resultado> extrairResultadosDoHTML(String html) {
		Matcher matchsResultado = Patterns.REGEX_RESULTADO.matcher(html);

		Set<Resultado> resultados = new HashSet<>();
		while (matchsResultado.find()) {
			Resultado resultado = extrairResultadoDoMatcher(matchsResultado);
			resultados.add(resultado);
		}
		
		return resultados;
	}

	private static Resultado extrairResultadoDoMatcher(Matcher matcher) {
		
		String[] premios = new String[Premio.values().length];

		String data = matcher.group(GrupoDoRegex.DATA.grupo);
		Turno turno = Turno.get(matcher.group(GrupoDoRegex.TURNO.grupo));
		for (Premio premio : Premio.values()) {
			premios[premio.ordinal()] = matcher.group(GrupoDoRegex.grupoDoPremio(premio));
		}

		return new ResultadoBuilder().data(data).premios(premios).turno(turno).build();
	}
}
