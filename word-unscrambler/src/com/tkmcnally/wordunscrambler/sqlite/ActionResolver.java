package com.tkmcnally.wordunscrambler.sqlite;

import java.sql.Connection;
public interface ActionResolver {
	public Connection getConnection();
}