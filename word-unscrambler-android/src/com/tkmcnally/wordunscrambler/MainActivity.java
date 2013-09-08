package com.tkmcnally.wordunscrambler;

import java.io.IOException;

import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.tkmcnally.wordunscrambler.helper.DataBaseHelper;
import com.tkmcnally.wordunscrambler.resolver.AndroidActionResolver;

public class MainActivity extends AndroidApplication {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		DataBaseHelper dbh;
		try {
			Log.d("DATABASE", "1231234");
			dbh = new DataBaseHelper(this);
			Log.d("DATABASE", "123123");
			dbh.createdatabase();
			
			dbh.opendatabase();
			dbh.getReadableDatabase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("asads", "TESTINGGG");
			e.printStackTrace();
		}
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = false;

		initialize(new MyGdxGame(new AndroidActionResolver(this)), cfg);
	}
}