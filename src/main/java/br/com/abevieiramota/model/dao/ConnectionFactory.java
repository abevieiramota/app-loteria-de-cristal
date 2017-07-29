package br.com.abevieiramota.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public static Connection instance() throws SQLException {
		return DriverManager.getConnection("jdbc:sqlite:loteria.db");
	}

}
