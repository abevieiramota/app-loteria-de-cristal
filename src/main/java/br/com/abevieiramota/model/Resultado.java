package br.com.abevieiramota.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;

import br.com.abevieiramota.service.parser.Patterns;

public class Resultado {

	private static final String FORMATO_TO_TABLE = "\tPrêmio %d:\t%s\n";

	private static final String FORMATO_TO_STRING = "Data [%s][%s] Premios[%s]";

	public enum Premio {
		_1, _2, _3, _4, _5, _6, _7, _8, _9, _10;

		public final static Integer QUANTIDADE = Premio.values().length;
	}

	public enum Bicho {
		AVESTRUZ,
		AGUIA,
		BURRO,
		BORBOLETA,
		CACHORRO,
		CABRA,
		CARNEIRO,
		CAMELO,
		COBRA,
		COELHO,
		CAVALO,
		ELEFANTE,
		GALO,
		GATO,
		JACARE,
		LEAO,
		MACACO,
		PORCO,
		PAVAO,
		PERU,
		TOURO,
		TIGRE,
		URSO,
		VEADO,
		VACA;

		public static Bicho fromResultado(String resultado) {
			Integer asInteger = Integer.valueOf(resultado);
			Integer dezena = asInteger % 100;

			if (dezena == 0) {
				dezena = 100;
			}

			return Bicho.values()[(dezena - 1) / 4];
		}
	}

	public enum Turno {

		DIURNO(new String[] { "14:00h", "14:40h", "14:45h", "14:30h" }),
		NOTURNO(new String[] { "19:10h", "19:30h", "19:00h" });

		private static final String MSG_ERRO_REPRESENTACAO = "Representação de turno inexistente. [%s]";
		private static final Map<String, Turno> MAP_REPR;

		static {
			Map<String, Turno> _mapRepr = new HashMap<>();

			for (Turno turno : Turno.values()) {
				for (String repr : turno.reprs) {
					_mapRepr.put(repr, turno);
				}
			}

			MAP_REPR = Collections.unmodifiableMap(_mapRepr);
		}

		private String[] reprs;

		Turno(String[] reprs) {
			this.reprs = reprs;
		}

		public static Turno get(String repr) {
			if (!MAP_REPR.containsKey(repr)) {
				throw new IllegalArgumentException(String.format(MSG_ERRO_REPRESENTACAO, repr));
			}

			return MAP_REPR.get(repr);
		}

	}

	static public class ResultadoBuilder {

		private Resultado resultado = new Resultado();

		private boolean premiosSetados = false;

		public ResultadoBuilder data(String data) {
			checkArgument(Patterns.REGEX_DATA.matcher(data).find());

			this.resultado.data = data;

			return this;
		}

		public ResultadoBuilder premios(String[] valores) {
			checkNotNull(valores);
			checkArgument(valores.length == 10);

			for (String valor : valores) {
				checkNotNull(valor);
				checkArgument(CharMatcher.javaDigit().matchesAllOf(valor));
				checkArgument(valor.length() == 4);
			}

			this.resultado.premios = valores;
			this.premiosSetados = true;

			return this;
		}

		public ResultadoBuilder turno(Turno turno) {
			checkNotNull(turno);

			this.resultado.turno = turno;

			return this;
		}

		public Resultado build() {
			checkState(this.resultado.data != null);
			checkState(this.premiosSetados);
			checkState(this.resultado.turno != null);

			return resultado;
		}
	}

	private String[] premios;

	public String data;

	public Turno turno;

	private Resultado() {
		this.premios = new String[Premio.QUANTIDADE];
	}

	public String premio(Premio posicao) {
		return this.premios[posicao.ordinal()];
	}

	@Override
	public boolean equals(Object obj) {
		boolean saoIguais = false;
		if (obj instanceof Resultado) {
			Resultado objResultado = (Resultado) obj;

			saoIguais = Objects.equals(objResultado.data, this.data)
					&& Objects.deepEquals(objResultado.premios, this.premios)
					&& Objects.equals(objResultado.turno, this.turno);
		}

		return saoIguais;
	}

	@Override
	public int hashCode() {

		return Objects.hash(this.data, this.turno) + 13 * Arrays.hashCode(this.premios);
	}

	@Override
	public String toString() {

		return String.format(FORMATO_TO_STRING, this.data, this.turno, Joiner.on(',').join(this.premios));
	}

	public String toTable() {

		StringBuilder sb = new StringBuilder();
		for (Premio premio : Premio.values()) {
			sb.append(String.format(FORMATO_TO_TABLE, premio.ordinal() + 1, premio(premio)));
		}

		return sb.toString();
	}

	public boolean isAfter(String dtUltimaAtualizacao, Turno turnoUltimaAtualizacao) {

		return !this.data.equals(dtUltimaAtualizacao) || turno.ordinal() > turnoUltimaAtualizacao.ordinal();
	}

}
