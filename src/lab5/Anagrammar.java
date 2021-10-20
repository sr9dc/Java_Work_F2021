// Sai Rajuladevi, srajulad
package lab5;

import java.io.File;
import java.util.Scanner;
import java.util.Arrays;

public class Anagrammar {
	String[] words;		//stores all words read from words.txt
	boolean isInDictionary; //true if the clue word exists in words.txt
	boolean hasAnagrams;	//true if the clue word has anagrams
	String[] anagramArray;	//stores all anagrams of clue-word, if found
	
	/**loadWords method reads the file and loads all words 
	 * into the words[] array */
	void loadWords(String filename) {
		// get number of lines to size the words array
		int lines = 0; 

		Scanner fileLinesScanner = null; 
		try {
			File file = new File(filename);
			fileLinesScanner = new Scanner(file);
		} catch (Exception e) {
			e.printStackTrace();
		}


		while(fileLinesScanner.hasNextLine()) {
			lines++;
			fileLinesScanner.nextLine();
		}

		fileLinesScanner.close();	

		// initialize the words array
		words = new String[lines];


		// Scan again
		Scanner fileScanner = null; 
		try {
			File file = new File(filename);
			fileScanner = new Scanner(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// index to iterate through array
		int wordsIndex = 0;

		// input data into words Array
		while(fileScanner.hasNextLine()) {
			words[wordsIndex] = fileScanner.nextLine();
			wordsIndex++;
		}

		fileScanner.close();	
	}
	
	/** findAnagrams method traverses the words array and looks 
	 * for anagrams of the clue. While doing so, if the clue-word itself is found in the 
	 * words array, it sets the isInDictionary to true.
	 * If it finds any anagram of the clue, it sets the hasAnagram to true. 
	 * It loads the anagram into the anagramArray. 
	 * If no anagrams found, then anagramArray remains an array with size 0. 
	 * */
	void findAnagrams(String clue) {
		// sort the clue word in a char Array
		char[] clueCharArr = clue.toLowerCase().toCharArray();
		Arrays.sort(clueCharArr);
		
		isInDictionary = false;
		hasAnagrams = false;
		
		// count the anagrams to size the anagrams array
		int anagramsCount = 0;
		
		for(String word : words) {
			// skip when word and clue are same, set isInDictionary to true
			if(word.equalsIgnoreCase(clue)) {
				isInDictionary = true;
				continue;
			}
			
			// sort each word using a char array
			char[] wordCharArr = word.toLowerCase().toCharArray();
			Arrays.sort(wordCharArr);
			
			// compare to clue array, and increment anagrams count
			if(Arrays.equals(wordCharArr, clueCharArr)) {
				anagramsCount++;
			}
		}
		
		// size the anagrams Array
		anagramArray = new String[anagramsCount];
		
		// iteration index for Array
		int anagramsIndex = 0;
		
		for(String word : words) {
			// skip when word and clue are the same
			if(word.equalsIgnoreCase(clue)) continue;
			
			// sort each word using a char array
			char[] wordCharArr = word.toLowerCase().toCharArray();
			Arrays.sort(wordCharArr);
			
			// compare to clue array, and store and increment using the anagrams index
			if(Arrays.equals(wordCharArr, clueCharArr)) {
				anagramArray[anagramsIndex] = word;
				anagramsIndex++;
			}
		}
		
		// now, there are more than 0 anagrams set hasAnagrams to true
		if(anagramsIndex > 0) hasAnagrams = true;
	}
	
}
