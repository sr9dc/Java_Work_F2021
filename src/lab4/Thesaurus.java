// srajulad, Sai Rajuladevi
package lab4;

import java.io.File;
import java.util.Scanner;

public class Thesaurus extends WordReference{
	
	public Thesaurus(String filename) {
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
		
		// initialize the wordData array for each word
		wordData = new String[lines][];
		
		// Scan again
		
		Scanner fileScanner = null; 
		try {
			File file = new File(filename);
			fileScanner = new Scanner(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int fileDataIndex = 0;
		
		// input data into fileData Array
		while(fileScanner.hasNextLine()) {
			wordData[fileDataIndex]= fileScanner.nextLine().split(", ");
			fileDataIndex++;
		}
	
		
		fileScanner.close();	
	}

	@Override
	String[] lookup(String word) {
		String[] lookups;
		
		for(String[] wordSynonymsPair : wordData) {
			if(word.equalsIgnoreCase(wordSynonymsPair[0])) {
				lookups = new String[wordSynonymsPair.length - 1];
				
				for(int i = 1; i < wordSynonymsPair.length; i++) {
					lookups[i-1] = wordSynonymsPair[i];
				}
				return lookups;
			}
		}
		return null;
	}
	
	


}
