package br.com.abevieiramota.model.dao;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.abevieiramota.messages.Messages;
import br.com.abevieiramota.model.TipoLoteria;
import br.com.abevieiramota.model.Turno;
import br.com.abevieiramota.service.parser.Patterns;

public class ConfiguracoesDAO {

	private static final String MSG_ERRO_DB_SEM_CONFIG = Messages.getString("db.erro.configuracao.sem_registros");

	private static final String QUERY_ULTIMA_ATUALIZACAO_DATA = Messages
			.getString("sql.configuracao.ultima_atualizacao_data");
	private static final String QUERY_ULTIMA_ATUALIZACAO_TURNO = Messages
			.getString("sql.configuracao.ultima_atualizacao_turno");
	private static final String QUERY_UPDATE_ULTIMA_ATUALIZACAO = Messages.getString("sql.configuracao.update");

	private Connection conn;

	public ConfiguracoesDAO() throws SQLException {
		this.conn = ConnectionFactory.instance();
	}

	public String ultimaDataAtualizado(TipoLoteria tipo) throws SQLException {
		checkNotNull(tipo);

		String dataUltimaAtualizacao;

		try (PreparedStatement stmt = this.conn.prepareStatement(QUERY_ULTIMA_ATUALIZACAO_DATA)) {
			stmt.setInt(1, tipo.ordinal());
			stmt.setQueryTimeout(30);
			stmt.setMaxRows(1);

			try (ResultSet rs = stmt.executeQuery()) {
				if (!rs.next()) {
					throw new IllegalArgumentException(MSG_ERRO_DB_SEM_CONFIG);
				}

				dataUltimaAtualizacao = rs.getString("ultimaAtualizacaoData"); //$NON-NLS-1$
			}
		}

		return dataUltimaAtualizacao;
	}

	public Turno ultimoTurnoAtualizado(TipoLoteria tipo) throws SQLException {
		checkNotNull(tipo);

		int turnoOrdinal;

		try (PreparedStatement stmt = this.conn.prepareStatement(QUERY_ULTIMA_ATUALIZACAO_TURNO)) {
			stmt.setInt(1, tipo.ordinal());
			stmt.setQueryTimeout(30);
			stmt.setMaxRows(1);

			try (ResultSet rs = stmt.executeQuery()) {
				if (!rs.next()) {
					throw new IllegalArgumentException(MSG_ERRO_DB_SEM_CONFIG);
				}

				turnoOrdinal = rs.getInt("ultimaAtualizacaoTurno"); //$NON-NLS-1$
			}
		}

		return Turno.values()[turnoOrdinal];
	}

	public void atualizarUltimaAtualizacao(String data, Turno turno, TipoLoteria tipo) throws SQLException {
		checkNotNull(data);
		checkNotNull(turno);
		checkNotNull(tipo);
		checkArgument(Patterns.REGEX_DATA.matcher(data).find());

		try (PreparedStatement stmt = this.conn.prepareStatement(QUERY_UPDATE_ULTIMA_ATUALIZACAO)) {
			stmt.setQueryTimeout(30);

			stmt.setString(1, data);
			stmt.setInt(2, turno.ordinal());
			stmt.setInt(3, tipo.ordinal());

			stmt.execute();
		}
	}

}
