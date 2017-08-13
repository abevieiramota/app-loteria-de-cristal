package br.com.abevieiramota.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.Arrays;
import java.util.Objects;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;

import br.com.abevieiramota.service.parser.Patterns;

public class Resultado {

	private static final String FORMATO_TO_TABLE = "\tPrêmio %d:\t%s\n";
	private static final String FORMATO_TO_STRING = "Data [%s][%s] Premios[%s]";

	static public class ResultadoBuilder {

		private Resultado resultado = new Resultado();

		private boolean premiosSetados = false;
		private boolean tipoLoteriaSetado = false;

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
		
		public ResultadoBuilder tipoLoteria(TipoLoteria tipoLoteria) {
			checkNotNull(tipoLoteria);
			
			this.resultado.tipoLoteria = tipoLoteria;
			this.tipoLoteriaSetado = true;
			
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
			checkState(this.tipoLoteriaSetado);

			return this.resultado;
		}
	}

	private String[] premios;
	private String data;
	private Turno turno;
	private TipoLoteria tipoLoteria;

	private Resultado() {
		this.premios = new String[Premio.QUANTIDADE];
	}

	public String premio(Premio posicao) {
		return this.premios[posicao.ordinal()];
	}
	
	public TipoLoteria getTipoLoteria() {
		return this.tipoLoteria;
	}
	
	public String getData() {
		return this.data;
	}
	
	public Turno getTurno() {
		return this.turno;
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
