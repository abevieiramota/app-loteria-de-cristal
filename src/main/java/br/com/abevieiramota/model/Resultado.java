package br.com.abevieiramota.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.text.ParseException;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import br.com.abevieiramota.util.DataUtil;

@Entity
@Table(name = "resultado")
public class Resultado {

	public static class ResultadoBuilder {

		private Resultado resultado = new Resultado();

		public ResultadoBuilder data(String data) {
			checkNotNull(data);

			this.resultado.data = data;
			return this;
		}

		public ResultadoBuilder premios(String[] premios) {
			checkNotNull(premios);
			checkArgument(premios.length == Premio.values().length);

			this.resultado.premio1 = premios[0];
			this.resultado.premio2 = premios[1];
			this.resultado.premio3 = premios[2];
			this.resultado.premio4 = premios[3];
			this.resultado.premio5 = premios[4];
			this.resultado.premio6 = premios[5];
			this.resultado.premio7 = premios[6];
			this.resultado.premio8 = premios[7];
			this.resultado.premio9 = premios[8];
			this.resultado.premio10 = premios[9];

			return this;
		}

		public ResultadoBuilder turno(Turno turno) {
			checkNotNull(turno);

			this.resultado.turno = turno;
			return this;
		}

		public ResultadoBuilder loteria(Loteria loteria) {
			checkNotNull(loteria);

			this.resultado.loteria = loteria;
			return this;
		}

		public Resultado build() {
			return this.resultado;
		}

	}

	public static enum Premio {
		_1, _2, _3, _4, _5, _6, _7, _8, _9, _10;
	}

	private static final String FORMATO_TO_TABLE = "\tPrêmio %d:\t%s\n";
	private static final String FORMATO_TO_STRING = "Data [%s][%s] Premios[%s]";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_resultado")
	private Integer id;
	@Column(name = "data")
	private String data;
	@ManyToOne
	@JoinColumn(name = "id_turno")
	private Turno turno;
	@ManyToOne
	@JoinColumn(name = "id_loteria")
	private Loteria loteria;
	@Column(name = "premio1")
	private String premio1;
	@Column(name = "premio2")
	private String premio2;
	@Column(name = "premio3")
	private String premio3;
	@Column(name = "premio4")
	private String premio4;
	@Column(name = "premio5")
	private String premio5;
	@Column(name = "premio6")
	private String premio6;
	@Column(name = "premio7")
	private String premio7;
	@Column(name = "premio8")
	private String premio8;
	@Column(name = "premio9")
	private String premio9;
	@Column(name = "premio10")
	private String premio10;
	@Transient
	private List<String> premiosAsArray;

	public String premio(Premio posicao) {
		return this.getPremios().get(posicao.ordinal());
	}

	public String getData() {
		return this.data;
	}

	public Turno getTurno() {
		return this.turno;
	}

	public List<String> getPremios() {

		if (this.premiosAsArray == null) {
			this.premiosAsArray = Lists.newArrayList(this.premio1, this.premio2, this.premio3, this.premio4,
					this.premio5, this.premio6, this.premio7, this.premio8, this.premio9, this.premio10);
		}

		return this.premiosAsArray;
	}

	@Override
	public String toString() {

		return String.format(FORMATO_TO_STRING, this.data, this.turno, Joiner.on(',').join(this.getPremios()));
	}

	public String toTable() {

		StringBuilder sb = new StringBuilder();
		for (Premio premio : Premio.values()) {
			sb.append(String.format(FORMATO_TO_TABLE, premio.ordinal() + 1, premio(premio)));
		}

		return sb.toString();
	}

	public boolean isAfter(String data, Turno turno) throws ParseException {

		return DataUtil.compare(this.data, data) > 0 || this.turno.getHoraMinima() > turno.getHoraMaxima();
	}

}
