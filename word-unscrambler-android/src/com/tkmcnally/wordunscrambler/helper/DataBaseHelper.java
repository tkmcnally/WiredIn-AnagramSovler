package com.tkmcnally.wordunscrambler.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.tkmcnally.wordunscrambler.R;

import android.content.Context;
import android.content.res.Resources;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {
	private static Context mycontext;

	private String DB_PATH = "/data/data/com.tkmcnally.wordunscrambler/databases/";
	private static String DB_NAME = "dictionary.db";//the extension may be .sqlite or .db
	public SQLiteDatabase myDataBase;
	private Resources resources;
	
	public DataBaseHelper(Context context) {
		
	    super(context, DB_NAME, null, 1);
	    Log.d("DATABASE", DB_PATH + " = " + DB_NAME);
	    resources = context.getResources();
	}

	private boolean checkdatabase() {
		Log.d("DATABASE", "123123");
		boolean checkdb = false;
		try {
			String myPath = DB_PATH + DB_NAME;
			File dbfile = new File(myPath);
			checkdb = dbfile.exists();
		} catch(SQLiteException e) {
			Log.d("DATABASE", "Database doesn't exist");
		}
		return checkdb;
	}

	@Override
	public synchronized void close() {
		if(myDataBase != null) {
			myDataBase.close();
		}
		super.close();
	}

	private void copydatabase() throws IOException{
		Log.d("DATABASE", "123123");
	    InputStream databaseInput = null;
	    String outFileName = DB_PATH + DB_NAME;
	    OutputStream databaseOutput = new FileOutputStream(outFileName);

	    byte[] buffer = new byte[1024];
	    int length;

	    databaseInput = resources.openRawResource(R.raw.testing1);
	    while((length = databaseInput.read(buffer)) > 0) {
	        databaseOutput.write(buffer, 0, length);
	        databaseOutput.flush();
	    }
	    //databaseInput.close();

	    databaseInput = resources.openRawResource(R.raw.testing2);
	    while((length = databaseInput.read(buffer)) > 0) {
	        databaseOutput.write(buffer);
	        databaseOutput.flush();
	    }
	  //  databaseInput.close();
	    databaseInput = resources.openRawResource(R.raw.testing3);
	    while((length = databaseInput.read(buffer)) > 0) {
	        databaseOutput.write(buffer, 0, length);
	        databaseOutput.flush();
	    }
	//    databaseInput.close();

	    databaseInput = resources.openRawResource(R.raw.testing4);
	    while((length = databaseInput.read(buffer)) > 0) {
	        databaseOutput.write(buffer);
	        databaseOutput.flush();
	    }
	  //  databaseInput.close();
	    databaseInput = resources.openRawResource(R.raw.testing5);
	    while((length = databaseInput.read(buffer)) > 0) {
	        databaseOutput.write(buffer, 0, length);
	        databaseOutput.flush();
	    }
	   // databaseInput.close();

	    databaseInput = resources.openRawResource(R.raw.testing6);
	    while((length = databaseInput.read(buffer)) > 0) {
	        databaseOutput.write(buffer);
	        databaseOutput.flush();
	    }
	 //   databaseInput.close();
	    databaseInput = resources.openRawResource(R.raw.testing7);
	    while((length = databaseInput.read(buffer)) > 0) {
	        databaseOutput.write(buffer, 0, length);
	        databaseOutput.flush();
	    }
	 //   databaseInput.close();

	    databaseInput = resources.openRawResource(R.raw.testing8);
	    while((length = databaseInput.read(buffer)) > 0) {
	        databaseOutput.write(buffer);
	        databaseOutput.flush();
	    }
	    
	    databaseInput = resources.openRawResource(R.raw.testing9);
	    while((length = databaseInput.read(buffer)) > 0) {
	        databaseOutput.write(buffer);
	        databaseOutput.flush();
	    }
	    
	    databaseInput = resources.openRawResource(R.raw.testing10);
	    while((length = databaseInput.read(buffer)) > 0) {
	        databaseOutput.write(buffer);
	        databaseOutput.flush();
	    }
	    databaseInput.close();
	    databaseOutput.flush();
	    databaseOutput.close();
	    System.out.println("HERE");
	}
	

	public void createdatabase() throws IOException {
		boolean dbexist = checkdatabase();
		if(dbexist) {
			System.out.println(" Database exists.");
		} else {
			getReadableDatabase();
			try {
				copydatabase();
			} catch(IOException e) {
				throw new Error("Error copying database");
			}
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public void opendatabase() throws SQLException {
		//Open the database
		String mypath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
	}

}