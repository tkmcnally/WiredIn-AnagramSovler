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

import com.badlogic.gdx.Gdx;
import com.tkmcnally.wordunscrambler.MyGdxGame;

public class Unscrambler {

	private static HashMap<String, List<String>> map;
	public static boolean loaded = true;
	public static HashMap<String, List<String>> sm = new HashMap<String, List<String>>();
	
	public static SQLiteDatabase dbConnection;
	
	public static List<String> getAnagram(String anagram) throws SQLException {
		
		Statement statement = dbConnection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT originalword FROM " + anagram.charAt(0) + "words WHERE sortedword = '" + anagram + "'");

	
		
		List<String> returnList = new ArrayList<String>();
		while(rs.next()) {
			//System.out.println( anagram + " # " + rs.getString(1));
			returnList.add(rs.getString("originalword"));
		}
		returnList = stripList(returnList);
		//List<String> test = map.get(anagram);
		//if(test != null) {
		//	returnList.addAll(test);
			
		//}
		
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
				testing.addAll(getAnagram(combinations.get(i)));
				if(testing.size() > 0) {
					if(returnList.get(combinations.get(i).length()) == null) {
						returnList.put(combinations.get(i).length(), testing);
					} else {
						returnList.get(combinations.get(i).length()).addAll(testing);
					}
				}
			}
			
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
	
	/*public static void setupMap() {
		long starttime = System.nanoTime();
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
//		System.out.println(Gdx.files.internal("data/testlist.txt").readString());
	//	File file = new File(Gdx.files.internal("data/testlist.txt").path());
	//	FileInputStream fis = null;

	
		BufferedReader br = Gdx.files.internal("data/TWL06.txt").reader(512);
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
			
				key = sort(line);	
				
	 			
	 			
				
				List<String> iList;
			
					iList = iMap.get(key);
					
					if(iList == null) {
						list = new ArrayList<String>();
						list.add(value);
						iMap.put(key, list);
					} else {
						iMap.get(key).add(line);
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
		
		
	}*/
	
	public static String sort(String s) {
		char[] c = s.toCharArray();
		
		Arrays.sort(c);
		String string = new String(c);
		return string;
	}
	
	/*
	public static void main(String[] args){
		  Runtime runtime = Runtime.getRuntime();
		    // Run the garbage collector
		    runtime.gc();
		    long MEGABYTE = 1024L * 1024L;    
		long memory = runtime.totalMemory() - runtime.freeMemory();
		
	    System.out.println("Used memory is bytes: " + memory);
	    
		setupMap();
		long starttime = System.nanoTime();
		//System.out.println(map.get(6));
		//System.out.println(map.get(6).get("BELOTT"));
		long endtime = System.nanoTime();
		long total = endtime - starttime;
		System.out.println("Total: " + total);
		memory = runtime.totalMemory() - runtime.freeMemory();
	    System.out.println("Used memory is bytes: " + memory / MEGABYTE);
		
		
	}*/
}
