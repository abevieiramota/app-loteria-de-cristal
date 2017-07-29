package br.com.abevieiramota.model.dao;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.Resultado.Premio;
import br.com.abevieiramota.model.Resultado.ResultadoBuilder;
import br.com.abevieiramota.model.Resultado.Turno;
import br.com.abevieiramota.service.parser.Patterns;

public class ResultadoDAO {

	private Connection connection;

	private final static String INSERT_TEMPLATE = "insert into resultado values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final static String SELECT_ALL = "select * from resultado";
	private final static String SELECT_BY_DATA = "select * from resultado where data = ? order by turno asc";
	private final static String SELECT_ALL_DIURNO = "select * from resultado where turno = 0";
	private final static String SELECT_ALL_NOTURNO = "select * from resultado where turno = 1";

	public ResultadoDAO() throws SQLException {
		connection = ConnectionFactory.instance();
	}

	public void salvar(Resultado resultado) throws SQLException {

		try (PreparedStatement stmt = connection.prepareStatement(INSERT_TEMPLATE)) {
			stmt.setQueryTimeout(30);

			preencheStatement(stmt, resultado);

			stmt.execute();
		}
	}

	// TODO: melhorar
	public void salvar(List<Resultado> resultados) throws SQLException {

		for (Resultado resultado : resultados) {
			try (PreparedStatement stmt = this.connection.prepareStatement(INSERT_TEMPLATE)) {
				stmt.setQueryTimeout(30);

				preencheStatement(stmt, resultado);

				stmt.execute();
			}
		}
	}

	public List<Resultado> all() throws SQLException {
		List<Resultado> resultados = new ArrayList<>();
		try (Statement stmt = this.connection.createStatement()) {
			stmt.setQueryTimeout(30);

			try (ResultSet rs = stmt.executeQuery(SELECT_ALL)) {
				while (rs.next()) {
					Resultado resultado = resultadoDoResultSet(rs);

					resultados.add(resultado);
				}
			}
		}

		return resultados;
	}

	public List<Resultado> allDiurno() throws SQLException {

		List<Resultado> resultados = new ArrayList<>();

		try (Statement stmt = connection.createStatement()) {
			stmt.setQueryTimeout(30);

			try (ResultSet rs = stmt.executeQuery(SELECT_ALL_DIURNO)) {
				while (rs.next()) {
					Resultado resultado = resultadoDoResultSet(rs);

					resultados.add(resultado);
				}
			}
		}

		return resultados;
	}

	public List<Resultado> allNoturno() throws SQLException {

		List<Resultado> resultados = new ArrayList<>();

		try (Statement stmt = connection.createStatement()) {
			stmt.setQueryTimeout(30);

			try (ResultSet rs = stmt.executeQuery(SELECT_ALL_NOTURNO)) {
				while (rs.next()) {
					Resultado resultado = resultadoDoResultSet(rs);

					resultados.add(resultado);
				}
			}
		}

		return resultados;
	}

	public List<Resultado> byData(String data) throws SQLException {
		checkNotNull(data);
		checkArgument(Patterns.REGEX_DATA.matcher(data).find());

		List<Resultado> resultadosDoDia = new ArrayList<>(2);

		try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_DATA)) {
			stmt.setString(1, data);
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

		Resultado resultado = new ResultadoBuilder().data(data).premios(premios).turno(turno).build();

		return resultado;
	}

	private void preencheStatement(PreparedStatement stmt, Resultado resultado) throws SQLException {

		stmt.setString(1, resultado.data);

		for (int i = 2; i <= 11; i++) {
			stmt.setString(i, resultado.premio(Premio.values()[i - 2]));
		}

		stmt.setInt(12, resultado.turno.ordinal());
	}

}
