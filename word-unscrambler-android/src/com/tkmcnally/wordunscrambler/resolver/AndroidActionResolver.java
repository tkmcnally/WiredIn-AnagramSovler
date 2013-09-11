package com.tkmcnally.wordunscrambler.resolver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.tkmcnally.wordunscrambler.sqlite.ActionResolver;

public class AndroidActionResolver implements ActionResolver {

	Handler uiThread;
	Context appContext;

	public AndroidActionResolver(Context appContext) {
		uiThread = new Handler();
		this.appContext = appContext;
	}

	@Override
	public Connection getConnection() {
		String url = "jdbc:sqldroid:/data/data/com.tkmcnally.wordunscrambler/databases/dictionary.db";
		try {
			Class.forName("org.sqldroid.SQLDroidDriver").newInstance();
			return DriverManager.getConnection(url);
		} catch (InstantiationException e) {
			Log.e("sql", e.getMessage());
		} catch (IllegalAccessException e) {
			Log.e("sql", e.getMessage());
		} catch (ClassNotFoundException e) {
			Log.e("sql", e.getMessage());
		} catch (SQLException e) {
			Log.e("sql", e.getMessage());
		}
		return null;
	}
}