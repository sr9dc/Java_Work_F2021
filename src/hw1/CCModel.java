// srajulad, Sai Rajuladevi
package hw1;

import java.io.File;
import java.util.Scanner;

public class CCModel {
	Case[] cases;
	String[] fileData;

	/**loadData() takes filename as a parameter,
	 * reads the file and loads all 
	 * data as a String for each row in 
	 * fileData[] array
	 * @param filename
	 */
	void loadData(String filename) {
		//write your code here

		// get number of lines to size the fileData array
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

		// initialize a fileData array
		fileData = new String[lines];

		// Scan again for input into Array
		Scanner fileScanner = null; 
		try {
			File file = new File(filename);
			fileScanner = new Scanner(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// input data into the fileData Array using an index for iteration
		int fileDataIndex = 0;

		while(fileScanner.hasNextLine()) {
			fileData[fileDataIndex] = fileScanner.nextLine();
			fileDataIndex++;
		}

		fileScanner.close();

	}

	/**loadCases() uses the data stored in fileData array
	 * and creates Case objects for each row.
	 * These cases are loaded into the cases array.
	 * Note that you may have to traverse the fileData array twice
	 * to be able to initialize the cases array's size.
	 */
	void loadCases() {
		//write your code here

		// create a cases array with length corresponding to the number of lines in the text file
		cases = new Case[fileData.length];

		// Load cases
		for(int i = 0; i < fileData.length; i++) {
			String[] casesDataSplit = fileData[i].split("\t");

			// This is our checking condition for the case type
			String caseTitleAndOrType = casesDataSplit[1];

			// Case type is Administrative - this means that the case number could or could not be there
			if(caseTitleAndOrType.contains("(Administrative)")){	
				// Find the start index of the case type
				int startIndex = caseTitleAndOrType.indexOf("(Administrative)");

				// the case number is there
				if(casesDataSplit.length == 3) {
					cases[i] = new Case(
							casesDataSplit[0], 
							caseTitleAndOrType.substring(0, startIndex - 1), 
							"Administrative", 
							casesDataSplit[2]);
				}

				// the case number is not there
				else {
					cases[i] = new Case(
							casesDataSplit[0], 
							caseTitleAndOrType.substring(0, startIndex - 1), 
							"Administrative", 
							null);
				}
			}

			// Case type is Federal - this means that the case number could or could not be there
			else if(caseTitleAndOrType.contains("(Federal)")){	
				// Find the start index of the case type
				int startIndex = caseTitleAndOrType.indexOf("(Federal)");

				// the case number is there
				if(casesDataSplit.length == 3) {
					cases[i] = new Case(
							casesDataSplit[0], 
							caseTitleAndOrType.substring(0, startIndex - 1), 
							"Federal", 
							casesDataSplit[2]);
				}

				// the case number is not there
				else {
					cases[i] = new Case(
							casesDataSplit[0], 
							caseTitleAndOrType.substring(0, startIndex - 1), 
							"Federal", 
							null);
				}
			}

			// Case type is not there - this means that the case number could or could not be there
			else {
				// the case number is there
				if(casesDataSplit.length == 3) {
					cases[i] = new Case(
							casesDataSplit[0], 
							casesDataSplit[1], 
							null, 
							casesDataSplit[2]);
				}

				// the case number is not there
				if(casesDataSplit.length == 2) {
					cases[i] = new Case(
							casesDataSplit[0], 
							casesDataSplit[1], 
							null, 
							null);
				}
			}
		}
	}
}
