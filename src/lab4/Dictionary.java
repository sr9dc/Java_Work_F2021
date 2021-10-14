// srajulad, Sai Rajuladevi
package lab4;

import java.io.File;
import java.util.Scanner;

public class Dictionary extends WordReference{
	
	public Dictionary(String filename) {
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
			String[] wordMeaning = fileScanner.nextLine().split(" [(]");
			
			wordData[fileDataIndex]= new String[] {wordMeaning[0], wordMeaning[1].split("[)]")[1].trim()};
			fileDataIndex++;
		}
		
		fileScanner.close();	
	}

	@Override
	String[] lookup(String word) {
		// TODO Auto-generated method stub
		String[] lookups;
		
		int meaningsCount = 0;
		for(String[] wordMeaningPair : wordData) {
			if(word.equalsIgnoreCase(wordMeaningPair[0])) {
				meaningsCount++;
			}
		}
		
		if(meaningsCount == 0) {
			return null;
		}
		
		lookups = new String[meaningsCount];
		
		int meaningsIndex = 0;
		
		for(String[] wordMeaningPair : wordData) {
			if(word.equalsIgnoreCase(wordMeaningPair[0])) {
				lookups[meaningsIndex] = wordMeaningPair[1];
				meaningsIndex++;
			}
		}
 
		return lookups;
	}
	

}
