package com.tkmcnally.wordunscrambler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Util {

	
	public static void main(String[] args) {
			File file = new File("C:/Users/Thomas/Desktop/sqlite/asorted.txt");
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(file);
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			// The name of the file to open.
			String fileName = "C:/Users/Thomas/Desktop/sqlite/import.txt";

			try {
				// Assume default encoding.
				FileWriter fileWriter =
						new FileWriter(fileName);

				// Always wrap FileWriter in BufferedWriter.
				BufferedWriter bufferedWriter =
						new BufferedWriter(fileWriter);

				String line;
				long count = 0;
				char[] key;
				String keyString;
				String lastLine = "";
				String currentLine, currentWord;
				boolean done = false;
				char l = 'a';
				while(!done) {
					/*currentLine = line.substring(0, line.indexOf(','));
					currentWord = line.substring(line.indexOf(','), line.length());
					if(currentLine.equals(lastLine)) {
						bufferedWriter.write(currentWord);
					} else {
						bufferedWriter.newLine();
						bufferedWriter.write(line);
					}
					/*key = line.toCharArray();
					Arrays.sort(key);
					keyString = new String(key);
					if(keyString.equals(lastLine)) {
						bufferedWriter.write("," + line);	
					} else {
						bufferedWriter.newLine();
						bufferedWriter.write(keyString + "," + line);	
					}
					
					lastLine = keyString;
					count++;
					 */
					
				
					
						//String key1 = line.substring(0, line.indexOf(','));
						//String trim = line.substring(line.indexOf(',') + 1, line.length());
					//	StringBuilder sb = new StringBuilder(trim);
						//trim = trim.replace(',', '?');
						//bufferedWriter.write(key1 + "," + trim.toString());
					
					//	bufferedWriter.write(line);
					
					bufferedWriter.newLine();
					bufferedWriter.write("create table " + l + "words(sortedword text primary key, originalword text);");
					bufferedWriter.newLine();
					bufferedWriter.write(".import " + l + "sorted1.txt " + l + "words");
					if(l == 'z')
						done = true;
					l++;
				
					
				}

				// Always close files.
				bufferedWriter.close();
			}
			catch(IOException ex) {
				System.out.println(
						"Error writing to file '"
								+ fileName + "'");
				// Or we could just do this:
				// ex.printStackTrace();
			}
		}
	}

