package br.com.abevieiramota.model.dao;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import br.com.abevieiramota.gui.wmain.Configuracao;
import br.com.abevieiramota.messages.Messages;
import br.com.abevieiramota.model.Premio;
import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.Resultado.ResultadoBuilder;
import br.com.abevieiramota.model.TipoLoteria;
import br.com.abevieiramota.model.Turno;
import br.com.abevieiramota.service.parser.Patterns;

public class ResultadoDao {

	private Connection connection;

	private final static String INSERT_TEMPLATE = Messages.getString("sql.resultado.insert");
	private final static String SELECT_ALL = Messages.getString("sql.resultado.all_tipo_turno");
	private final static String SELECT_ALL_ = Messages.getString("sql.resultado.all.tipo");
	private final static String SELECT_BY_DATA = Messages.getString("sql.resultado.data_tipo");

	public ResultadoDao() throws SQLException {
		connection = ConnectionFactory.instance();
	}

	public void salvar(Resultado resultado) throws SQLException {
		checkNotNull(resultado);

		try (PreparedStatement stmt = connection.prepareStatement(INSERT_TEMPLATE)) {
			stmt.setQueryTimeout(30);

			preencheStatement(stmt, resultado);

			stmt.execute();
		}
	}

	// TODO: melhorar
	public void salvar(List<Resultado> resultados) throws SQLException {
		checkNotNull(resultados);

		for (Resultado resultado : resultados) {
			try (PreparedStatement stmt = this.connection.prepareStatement(INSERT_TEMPLATE)) {
				stmt.setQueryTimeout(30);

				preencheStatement(stmt, resultado);

				stmt.execute();
			}
		}
	}

	public List<Resultado> all(EnumSet<Turno> turnos, TipoLoteria tipo) throws SQLException {
		checkNotNull(turnos);
		checkNotNull(tipo);

		if (EnumSet.allOf(Turno.class).equals(turnos)) {
			return _all(tipo);
		}

		List<Resultado> resultados = new ArrayList<>();
		try (PreparedStatement stmt = this.connection.prepareStatement(SELECT_ALL)) {
			stmt.setQueryTimeout(30);

			stmt.setInt(1, tipo.ordinal());
			// FIXME: pressupõe que há apenas um elemento
			stmt.setInt(2, turnos.iterator().next().ordinal());

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Resultado resultado = resultadoDoResultSet(rs);

					resultados.add(resultado);
				}
			}
		}

		return resultados;
	}

	private List<Resultado> _all(TipoLoteria tipo) throws SQLException {
		checkNotNull(tipo);

		List<Resultado> resultados = new ArrayList<>();
		try (PreparedStatement stmt = this.connection.prepareStatement(SELECT_ALL_)) {
			stmt.setQueryTimeout(30);

			stmt.setInt(1, tipo.ordinal());

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Resultado resultado = resultadoDoResultSet(rs);

					resultados.add(resultado);
				}
			}
		}

		return resultados;
	}

	public List<Resultado> allDiurno() throws SQLException {

		return all(EnumSet.of(Turno.DIURNO), Configuracao.getTipoLoteria());
	}

	public List<Resultado> allNoturno() throws SQLException {

		return all(EnumSet.of(Turno.NOTURNO), Configuracao.getTipoLoteria());
	}

	public List<Resultado> byData(String data, TipoLoteria tipo) throws SQLException {
		checkNotNull(data);
		checkNotNull(tipo);
		checkArgument(Patterns.REGEX_DATA.matcher(data).find());

		List<Resultado> resultadosDoDia = new ArrayList<>(2);

		try (PreparedStatement stmt = this.connection.prepareStatement(SELECT_BY_DATA)) {
			stmt.setString(1, data);
			stmt.setInt(2, tipo.ordinal());
			stmt.setMaxRows(2);
			stmt.setQueryTimeout(30);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					resultadosDoDia.add(resultadoDoResultSet(rs));
				}
			}
		}

		return resultadosDoDia;
	}

	private Resultado resultadoDoResultSet(ResultSet rs) throws SQLException {

		String data = rs.getString("data");

		String[] premios = new String[] {
				rs.getString("premio1"),
				rs.getString("premio2"),
				rs.getString("premio3"),
				rs.getString("premio4"),
				rs.getString("premio5"),
				rs.getString("premio6"),
				rs.getString("premio7"),
				rs.getString("premio8"),
				rs.getString("premio9"),
				rs.getString("premio10") };

		Turno turno = Turno.values()[rs.getInt("turno")];
		TipoLoteria tipoLoteria = TipoLoteria.values()[rs.getInt("tipo")];

		Resultado resultado = new ResultadoBuilder().data(data).premios(premios).turno(turno).tipoLoteria(tipoLoteria)
				.build();

		return resultado;
	}

	private void preencheStatement(PreparedStatement stmt, Resultado resultado) throws SQLException {

		stmt.setString(1, resultado.getData());

		for (int i = 2; i <= 11; i++) {
			stmt.setString(i, resultado.premio(Premio.values()[i - 2]));
		}

		stmt.setInt(12, resultado.getTurno().ordinal());
		stmt.setInt(13, resultado.getTipoLoteria().ordinal());
	}

}
