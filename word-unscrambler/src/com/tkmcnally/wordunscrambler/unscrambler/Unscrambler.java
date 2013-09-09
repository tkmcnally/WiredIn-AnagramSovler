package com.tkmcnally.wordunscrambler.unscrambler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.badlogic.gdx.Gdx;
import com.sun.rowset.CachedRowSetImpl;
import com.tkmcnally.wordunscrambler.MyGdxGame;
import com.tkmcnally.wordunscrambler.screens.MainMenu;

public class Unscrambler {

	private static HashMap<String, List<String>> map;
	public static boolean loaded = true;
	public static HashMap<String, List<String>> sm = new HashMap<String, List<String>>();
	
	public static Connection dbConnection;
	
	public static List<String> getAnagram(String anagram) throws SQLException {
		
		
		Statement statement = dbConnection.createStatement();
		statement.setFetchSize(10);
		statement.setPoolable(true);
		ResultSet rs = statement.executeQuery("SELECT originalword FROM " + anagram.charAt(0) + "words WHERE sortedword like '" + anagram + "'");
		
		
		List<String> returnList = new ArrayList<String>();
		long start = System.currentTimeMillis();
		
		while(rs.next()) {
			returnList.add(rs.getString(1));
		}
		rs.close();
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		returnList = stripList(returnList);

		return returnList;
	}

		public static List<String> stripList(List<String> list) {
			List<String> newList = new ArrayList<String>();
			int markerOne = 0;
			for(int i = 0; i < list.size(); i++) {
				for(int k = 0; k < list.get(i).length(); k++) {
					if(k == list.get(i).length() - 1) {
						newList.add(list.get(i).substring(markerOne, k + 1));
					}
					if(list.get(i).charAt(k) == '?') {
						newList.add(list.get(i).substring(markerOne, k));
						markerOne = k + 1;
					}
				}
			}
			return newList;
		}
	
	public static HashMap<Integer, List<String>> unscrambleWord(final MyGdxGame game, final String scrambled) throws IOException {
		
		List<String> testing = new ArrayList<String>();
		List<String> combinations = Combinations.getCombinations(scrambled);
		
		
		HashMap<Integer, List<String>> returnList = new HashMap<Integer, List<String>>();
		try {
			for(int i = 0; i < combinations.size(); i++) {
				testing = new ArrayList<String>();
				long start = System.currentTimeMillis();
				
				testing.addAll(getAnagram(combinations.get(i)));
				
				long end = System.currentTimeMillis();
				System.out.println("Here: " + (end - start));
				
				if(testing.size() > 0) {
					if(returnList.get(combinations.get(i).length()) == null) {
						returnList.put(combinations.get(i).length(), testing);
					} else {
						returnList.get(combinations.get(i).length()).addAll(testing);
					}
				}
			}
			System.out.println(returnList);
		} catch (SQLException e) {
			testing.add("Error!");
			e.printStackTrace();
		}

		return returnList;
	}
	
	public static void setupMap1() {
		long starttime = System.nanoTime();
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
//		System.out.println(Gdx.files.internal("data/testlist.txt").readString());
	//	File file = new File(Gdx.files.internal("data/testlist.txt").path());
	//	FileInputStream fis = null;

	
		BufferedReader br = Gdx.files.internal("data/testlist.txt").reader(512);
		// The name of the file to open.
	

		try {
		
			String line;
			String key;
			String valueLine;
			String value = null;
			int markerOne = 0;
			List<String> list;
			HashMap<String, List<String>> iMap = new HashMap<String, List<String>>();
			while((line = br.readLine()) != null) {
				int cIndex = line.indexOf(',');
				int lLength = line.length();
				key = line.substring(0, cIndex);	
				
	 			value = line.substring(cIndex + 1, lLength);
	 			
				
				List<String> iList;
			
					iList = iMap.get(key);
					
					if(iList == null) {
						list = new ArrayList<String>();
						list.add(value);
						iMap.put(key, list);
					} else {
						iMap.get(key).add(value);
					}
				
							
				
			}
			br.close();
			Unscrambler.map = iMap;
		} catch(Exception e) {
			
		}
		
		long endtime = System.nanoTime();
		long total = endtime - starttime;
		
		System.out.println("Total: " + total);
		loaded = true;	
	}

	public static String sort(String s) {
		char[] c = s.toCharArray();
		
		Arrays.sort(c);
		String string = new String(c);
		return string;
	}
}
