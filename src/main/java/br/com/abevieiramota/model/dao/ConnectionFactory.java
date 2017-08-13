package br.com.abevieiramota.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.abevieiramota.messages.Messages;

public class ConnectionFactory {

	public static Connection instance() throws SQLException {
		return DriverManager.getConnection(Messages.getString("db.url")); //$NON-NLS-1$
	}
}
