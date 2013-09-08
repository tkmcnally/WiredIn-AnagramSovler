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
	private static String DB_NAME = "dictionarydb.db";//the extension may be .sqlite or .db
	public SQLiteDatabase myDataBase;
	private Resources resources;
	/*private String DB_PATH = "/data/data/"
                        + mycontext.getApplicationContext().getPackageName()
                        + "/databases/";*/

	/*public DataBaseHelper(Context context) throws IOException {
		super(context,DB_NAME,null,1);
		mycontext=context;
		boolean dbexist = checkdatabase();
		if (dbexist) {
			System.out.println("Database exists");
			opendatabase();
		} else {
			System.out.println("Database doesn't exist");
			createdatabase();
		}
	}*/
	public DataBaseHelper(Context context) {
		
	    super(context, DB_NAME, null, 1);
	    Log.d("DATABASE", DB_PATH + " = " + DB_NAME);
	    resources = context.getResources();
	}

	private boolean checkdatabase() {
		Log.d("DATABASE", "123123");
		//SQLiteDatabase checkdb = null;
		boolean checkdb = false;
		try {
			String myPath = DB_PATH + DB_NAME;
			File dbfile = new File(myPath);
			//checkdb = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
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

/*	private void copydatabase() throws IOException {
		//Open your local db as the input stream
		InputStream myinput = mycontext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outfilename = DB_PATH + DB_NAME;

		//Open the empty db as the output stream
		OutputStream myoutput = new FileOutputStream("/data/data/(packagename)/databases   /(datbasename).sqlite");

		// transfer byte to inputfile to outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myinput.read(buffer))>0) {
			myoutput.write(buffer,0,length);
		}

		//Close the streams
		myoutput.flush();
		myoutput.close();
		myinput.close();
	}*/
	private void copydatabase() throws IOException{
		Log.d("DATABASE", "123123");
	    InputStream databaseInput = null;
	    String outFileName = DB_PATH + DB_NAME;
	    OutputStream databaseOutput = new FileOutputStream(outFileName);

	    byte[] buffer = new byte[1024];
	    int length;

	    databaseInput = resources.openRawResource(R.raw.db1pack);
	    while((length = databaseInput.read(buffer)) > 0) {
	        databaseOutput.write(buffer, 0, length);
	        databaseOutput.flush();
	    }
	    databaseInput.close();

	    databaseInput = resources.openRawResource(R.raw.db2pack);
	    while((length = databaseInput.read(buffer)) > 0) {
	        databaseOutput.write(buffer);
	        databaseOutput.flush();
	    }
	    databaseInput = resources.openRawResource(R.raw.db3pack);
	    while((length = databaseInput.read(buffer)) > 0) {
	        databaseOutput.write(buffer, 0, length);
	        databaseOutput.flush();
	    }
	    databaseInput.close();

	    databaseInput = resources.openRawResource(R.raw.db4pack);
	    while((length = databaseInput.read(buffer)) > 0) {
	        databaseOutput.write(buffer);
	        databaseOutput.flush();
	    }
	    databaseInput = resources.openRawResource(R.raw.db5pack);
	    while((length = databaseInput.read(buffer)) > 0) {
	        databaseOutput.write(buffer, 0, length);
	        databaseOutput.flush();
	    }
	    databaseInput.close();

	    databaseInput = resources.openRawResource(R.raw.db6pack);
	    while((length = databaseInput.read(buffer)) > 0) {
	        databaseOutput.write(buffer);
	        databaseOutput.flush();
	    }
	    databaseInput = resources.openRawResource(R.raw.db7pack);
	    while((length = databaseInput.read(buffer)) > 0) {
	        databaseOutput.write(buffer, 0, length);
	        databaseOutput.flush();
	    }
	    databaseInput.close();

	    databaseInput = resources.openRawResource(R.raw.db8pack);
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