package br.com.abevieiramota.model.dao;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.abevieiramota.model.Resultado.Turno;
import br.com.abevieiramota.service.parser.Patterns;

public class ConfiguracoesDAO {

	private static final String MSG_ERRO_DB_SEM_CONFIG = "Tabela de configuração sem registros.";

	private Connection conn;

	private static final String CAMPO_ULTIMA_ATUALIZACAO_DATA = "ultimaAtualizacaoData";
	private static final String QUERY_ULTIMA_ATUALIZACAO_DATA = "select ultimaAtualizacaoData from config";

	private static final String CAMPO_ULTIMA_ATUALIZACAO_TURNO = "ultimaAtualizacaoTurno";
	private static final String QUERY_ULTIMA_ATUALIZACAO_TURNO = "select ultimaAtualizacaoTurno from config";

	private static final String QUERY_UPDATE_ULTIMA_ATUALIZACAO = "update config set " + CAMPO_ULTIMA_ATUALIZACAO_DATA
			+ "=?, " + CAMPO_ULTIMA_ATUALIZACAO_TURNO + "=?";

	public ConfiguracoesDAO() throws SQLException {
		this.conn = ConnectionFactory.instance();
	}

	public String ultimaDataAtualizado() throws SQLException {
		String dataUltimaAtualizacao;

		try (PreparedStatement stmt = this.conn.prepareStatement(QUERY_ULTIMA_ATUALIZACAO_DATA)) {
			stmt.setQueryTimeout(30);
			stmt.setMaxRows(1);

			try (ResultSet rs = stmt.executeQuery()) {
				if (!rs.next()) {
					throw new IllegalArgumentException(MSG_ERRO_DB_SEM_CONFIG);
				}

				dataUltimaAtualizacao = rs.getString(CAMPO_ULTIMA_ATUALIZACAO_DATA);
			}
		}

		return dataUltimaAtualizacao;
	}

	public Turno ultimoTurnoAtualizado() throws SQLException {

		int turnoOrdinal;

		try (PreparedStatement stmt = this.conn.prepareStatement(QUERY_ULTIMA_ATUALIZACAO_TURNO)) {
			stmt.setQueryTimeout(30);
			stmt.setMaxRows(1);

			try (ResultSet rs = stmt.executeQuery()) {
				if (!rs.next()) {
					throw new IllegalArgumentException(MSG_ERRO_DB_SEM_CONFIG);
				}

				turnoOrdinal = rs.getInt(CAMPO_ULTIMA_ATUALIZACAO_TURNO);
			}
		}

		return Turno.values()[turnoOrdinal];
	}

	public void atualizarUltimaAtualizacao(String data, Turno turno) throws SQLException {
		checkNotNull(data);
		checkNotNull(turno);
		checkArgument(Patterns.REGEX_DATA.matcher(data).find());

		try (PreparedStatement stmt = this.conn.prepareStatement(QUERY_UPDATE_ULTIMA_ATUALIZACAO)) {
			stmt.setQueryTimeout(30);

			stmt.setString(1, data);
			stmt.setInt(2, turno.ordinal());

			stmt.execute();
		}
	}

}
