// Sai Rajuladevi, srajulad
package hw3;

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
			int rejects = 0;
			while (input.hasNextLine()) {
				String[] splitLines = input.nextLine().split("\t"); // for .tsv, split on tab character
				
				String[] checkVals = {"","","","","","","","",""};
				
				for(int i = 0; i < splitLines.length; i++) {
					checkVals[i] = splitLines[i];
				}

				
				if(checkVals[0].trim().equalsIgnoreCase("") 
						|| checkVals[1].trim().equalsIgnoreCase("") 
						|| checkVals[2].trim().equalsIgnoreCase("") 
						|| checkVals[3].trim().equalsIgnoreCase("") ) {
					rejects++;
					
				} else {
					Case c = new Case(checkVals[0], checkVals[1], checkVals[2], checkVals[3], // input into Case constructor
							checkVals[4], checkVals[5], checkVals[6]);
					caseList.add(c); // add to the returned list
				}
				
			}
			input.close(); 
			
			if(rejects > 0) {
				throw new DataException(Integer.toString(rejects) + " cases rejected.\n The file must have cases with\n" + 
						"tab separated date, title, type, and case number!");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DataException d) {
		}
		return caseList; // return list
	}
}
