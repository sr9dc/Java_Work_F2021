// Sai Rajuladevi, srajulad
package hw2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;


public class CCModel {
	ObservableList<Case> caseList = FXCollections.observableArrayList(); 			//a list of case objects
	ObservableMap<String, Case> caseMap = FXCollections.observableHashMap();		//map with caseNumber as key and Case as value
	ObservableMap<String, List<Case>> yearMap = FXCollections.observableHashMap();	//map with each year as a key and a list of all cases dated in that year as value. 
	ObservableList<String> yearList = FXCollections.observableArrayList();			//list of years to populate the yearComboBox in ccView

	/** readCases() performs the following functions:
	 * It creates an instance of CaseReaderFactory, 
	 * invokes its createReader() method by passing the filename to it, 
	 * and invokes the caseReader's readCases() method. 
	 * The caseList returned by readCases() is sorted 
	 * in the order of caseDate for initial display in caseTableView. 
	 * Finally, it loads caseMap with cases in caseList. 
	 * This caseMap will be used to make sure that no duplicate cases are added to data
	 * @param filename
	 */
	void readCases(String filename) {
		//write your code here
		// create CaseReaderFactory instance
		CaseReaderFactory crf = new CaseReaderFactory();
		CaseReader cr = crf.createReader(filename);

		List<Case> cases = cr.readCases(); // get the cases based on the filename
		Collections.sort(cases); // sort on most recent date

		caseList = FXCollections.observableArrayList(cases); // populate the caseList member variable

		Iterator<Case> casesIterator = caseList.iterator(); // start iterating through caseList, and input into map
		while (casesIterator.hasNext()) {
			Case input = casesIterator.next();
			caseMap.put(input.getCaseNumber(), input); // key is caseNumber, value is case
		}
	}

	/** buildYearMapAndList() performs the following functions:
	 * 1. It builds yearMap that will be used for analysis purposes in Cyber Cop 3.0
	 * 2. It creates yearList which will be used to populate yearComboBox in ccView
	 * Note that yearList can be created simply by using the keySet of yearMap.
	 */
	void buildYearMapAndList() {
		//write your code here
		for (Map.Entry<String, Case> entry : caseMap.entrySet()) { // iterate through caseMap
			Case value = (Case) entry.getValue();  // case to be inputted based on year
			String caseYear = value.getCaseDate().substring(0,4); // pull the year

			if(yearMap.containsKey(caseYear)){ // if the map already contains the year key
				yearMap.get(caseYear).add(value); // populate the list value
			}
			else {
				List<Case> startCasesList = new ArrayList<>(); // initialize a new list
				startCasesList.add(value); // add the Case into the list value
				yearMap.put(caseYear, startCasesList); // put the year key and initialized list in
			}
		}
		yearList.addAll(yearMap.keySet()); // populate the yearList
	}

	/**searchCases() takes search criteria and 
	 * iterates through the caseList to find the matching cases. 
	 * It returns a list of matching cases.
	 */
	List<Case> searchCases(String title, String type, String year, String number) {
		//write your code here
		List<Case> searchReturnList = new ArrayList<>(); // initialize the list to be returned

		Iterator<Case> casesIterator = caseList.iterator(); // iterate through the caseList again

		while (casesIterator.hasNext()) {
			Case c = casesIterator.next(); // this is the case to check if the substrings are in it

			// one line if statements -> if the value is null, include the true condition in the boolean checks
			Boolean checkTitle = title == null ? true : c.getCaseTitle().toLowerCase().contains(title.toLowerCase());
			Boolean checkType = type == null ? true : c.getCaseType().toLowerCase().contains(type.toLowerCase());
			Boolean checkYear = year == null ? true : c.getCaseDate().substring(0, 4).contains(year);
			Boolean checkNumber = number == null ? true : c.getCaseNumber().contains(number);

			// if the checks are met for substring arguments, then add to list to be returned
			if(checkTitle && checkType && checkYear && checkNumber) searchReturnList.add(c);

		}
		return searchReturnList; // return the populated list
	}

}
