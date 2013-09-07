package com.tkmcnally.wordunscrambler.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DesktopActionResolver implements ActionResolver {

	@Override
	public Connection getConnection() {
		String url = "jdbc:sqlite:dictionarydb";
		try {
			Class.forName("org.sqlite.JDBC");
			return DriverManager.getConnection(url);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}