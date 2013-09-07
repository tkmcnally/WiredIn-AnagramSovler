package com.tkmcnally.wordunscrambler.unscrambler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Combinations {

	public static List<String> getCombinations(final String word) {
		List<String> wordList = new ArrayList<String>();
		boolean first = true;

	/*	Set<Character> set = new HashSet<Character>();
		for(int i = 0; i < word.length(); i++) {
			set.add(Character.valueOf(word.charAt(i)));
		}*/
		
		Set<Integer> set = new HashSet<Integer>();
		for(int i = 0; i < word.length(); i++) {
			set.add(i);
		}
		Set<Set<Integer>> combinations = powerSet(set);
		StringBuilder sb = new StringBuilder();
		String sortedWord = new String();
		
		for(Set<Integer> sets: combinations) {
			if(!first) {
				if(sets.size() >= 2) {
					for(Integer c: sets) {
						sb.append(word.charAt(c));
					}
					char[] newArray = sb.toString().toCharArray();
					Arrays.sort(newArray);
					sortedWord = new String(newArray);
					wordList.add(sortedWord);
					sb.delete(0, sb.length());
				}
			}
			first = false;

		}

		return wordList;
	}

	public static <T> Set<Set<T>> powerSet(Set<T> originalSet) {
		Set<Set<T>> sets = new HashSet<Set<T>>();
		if (originalSet.isEmpty()) {
			sets.add(new HashSet<T>());
			return sets;
		}
		List<T> list = new ArrayList<T>(originalSet);
		T head = list.get(0);
		Set<T> rest = new HashSet<T>(list.subList(1, list.size()));
		for (Set<T> set : powerSet(rest)) {
			Set<T> newSet = new HashSet<T>();
			newSet.add(head);
			newSet.addAll(set);
			sets.add(newSet);
			sets.add(set);
		}
		return sets;
	}
}