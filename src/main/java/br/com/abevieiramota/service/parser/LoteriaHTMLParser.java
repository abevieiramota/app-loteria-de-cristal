package br.com.abevieiramota.service.parser;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.abevieiramota.model.Loteria;
import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.Resultado.Premio;
import br.com.abevieiramota.model.Resultado.ResultadoBuilder;
import br.com.abevieiramota.model.dao.EMF;
import br.com.abevieiramota.model.Turno;
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
		String hora = matcher.group(GrupoDoRegex.TURNO.grupo);
		EntityManager manager = EMF.buildManager();
		
		TypedQuery<Turno> query = manager.createQuery("select he.turno from HoraExpr he where value = :hora and he.turno.loteria = :loteria", Turno.class);
		query.setParameter("hora", hora);
		query.setParameter("loteria", loteria);
		
		Turno turno = query.getSingleResult();
		
		manager.close();
		
		for (Premio premio : Premio.values()) {
			premios[premio.ordinal()] = matcher.group(GrupoDoRegex.grupoDoPremio(premio));
		}

		return new ResultadoBuilder().data(data).premios(premios).turno(turno).loteria(loteria).build();
	}
}
