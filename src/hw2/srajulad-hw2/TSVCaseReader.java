// Sai Rajuladevi, srajulad
package hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TSVCaseReader extends CaseReader{

	TSVCaseReader(String filename) {
		super(filename); // parent constructor inheritance
	}

	@Override
	List<Case> readCases() {
		List<Case> caseList = new ArrayList<>(); // list to be returned
		try {
			Scanner input = new Scanner(new File(filename)); // Scanner iterator
			while (input.hasNextLine()) {
				String[] splitLines = input.nextLine().split("\t"); // for .tsv, split on tab character
				Case c = new Case(splitLines[0], splitLines[1], splitLines[2], splitLines[3], // input into Case constructor
						splitLines[4], splitLines[5], splitLines[6]);
				caseList.add(c); // add to the returned list
			}
			input.close(); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return caseList; // return list
	}
}
