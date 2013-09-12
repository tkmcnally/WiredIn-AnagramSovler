package com.tkmcnally.wordunscrambler;

public class Util {

	public static String filterString(final String userInput) {
			StringBuilder sb = new StringBuilder(userInput);
			for(int i = 0; i < sb.length(); i++) {
				if(!Character.isLetter(sb.charAt(i))) {
					sb = sb.deleteCharAt(i);
				}
			}			
			return sb.toString();
		}
	}

