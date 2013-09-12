package com.tkmcnally.wordunscrambler.unscrambler;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.tkmcnally.wordunscrambler.MyGdxGame;


public class Unscrambler {

	public static boolean loaded = true;
	public static HashMap<String, List<String>> sm = new HashMap<String, List<String>>();
	
	public static Connection dbConnection;
	
	public static List<String> getAnagram(String anagram) throws SQLException {
		
		String prepareSql = "SELECT originalword FROM " + anagram.charAt(0) + "words WHERE sortedword ='" + anagram.toUpperCase() + "'";
		PreparedStatement preparedStatement = dbConnection.prepareStatement(prepareSql);

		ResultSet rs = preparedStatement.executeQuery();	
		List<String> returnList = new ArrayList<String>();
		
		while(rs.next()) {
			String temp = rs.getString(1);
			returnList.add(temp);
		}
	
		rs.close();
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
	
	public static void unscrambleWord(final MyGdxGame game, final String scrambled, HashMap<Integer, List<String>> unscrambledEntries) throws IOException {
		
		List<String> testing = new ArrayList<String>();
		List<String> combinations = Combinations.getCombinations(scrambled);

		try {
			for(int i = 0; i < combinations.size(); i++) {
				testing = new ArrayList<String>();			
				testing.addAll(getAnagram(combinations.get(i)));
				if(testing.size() > 0) {
					if(unscrambledEntries.get(combinations.get(i).length()) == null) {
						unscrambledEntries.put(combinations.get(i).length(), testing);
						Collections.sort(unscrambledEntries.get(combinations.get(i).length()));
					} else {
						unscrambledEntries.get(combinations.get(i).length()).addAll(testing);
						Collections.sort(unscrambledEntries.get(combinations.get(i).length()));
					}
				}
			}
		} catch (SQLException e) {
			testing.add("Error!");
			e.printStackTrace();
		}
	}
}
