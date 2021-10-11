// srajulad, Sai Rajuladevi
package hw1;

public class SearchEngine {

	/**searchTitle() takes a searchString and array of cases,
	 * searches for cases with searchString in their title,
	 * and if found, returns them in another array of cases.
	 * If no match is found, it returns null.
	 * Search is case-insensitive
	 * @param searchString
	 * @param cases
	 * @return
	 */
	Case[] searchTitle(String searchString, Case[] cases) {
		//write your code here

		// First find the number of times a match is found
		int foundTitleLength = 0;

		for(int i = 0; i < cases.length; i++) {
			if(cases[i].caseTitle.toLowerCase().contains(searchString.toLowerCase())) {
				foundTitleLength++;
			}
		}

		// Initialize foundTitle array with size of how many matches are found
		Case[] foundTitle = new Case[foundTitleLength];

		// Iterate over array again using a start index and populate foundTitle array
		int foundTitleIndex = 0; 

		for(int i = 0; i < cases.length; i++) {
			if(cases[i].caseTitle.toLowerCase().contains(searchString.toLowerCase())) {
				foundTitle[foundTitleIndex] = cases[i];
				foundTitleIndex++;
			}
		}

		// Take care of nulls
		if(foundTitleLength == 0) {
			return null;
		}
		return foundTitle;
	}

	/**searchYear() takes year in YYYY format as search string,
	 * searches for cases that have the same year in their date,
	 * and returns them in another array of cases.
	 * If not found, it returns null.
	 * @param year
	 * @param cases
	 * @return
	 */
	Case[] searchYear(String year, Case[] cases) {
		//write your code here
		// First find the number of times a match is found
		int foundYearLength = 0;

		for(int i = 0; i < cases.length; i++) {
			if(cases[i].getYear() == Integer.parseInt(year)) {
				foundYearLength++;
			}
		}

		// Initialize foundYear array with size of how many matches are found
		Case[] foundYear = new Case[foundYearLength];

		// Iterate over array again using a start index and populate foundYear array
		int foundYearIndex = 0; 

		for(int i = 0; i < cases.length; i++) {
			if(cases[i].getYear() == Integer.parseInt(year)) {
				foundYear[foundYearIndex] = cases[i];
				foundYearIndex++;
			}
		}

		// Take care of nulls
		if(foundYearLength == 0) {
			return null;
		}
		return foundYear;
	}

	/**searchCaseNumber() takes a caseNumber,
	 * searches for those cases that contain that caseNumber, 
	 * and returns an array of cases that match the search.
	 * If not found, it returns null.
	 * Search is case-insensitive.
	 * @param caseNumber
	 * @param cases
	 * @return
	 */
	Case[] searchCaseNumber(String caseNumber, Case[] cases) {
		// First find the number of times a match is found
		int foundCaseNumberLength = 0;

		for(int i = 0; i < cases.length; i++) {
			if(cases[i].caseNumber == null) continue;
			if(cases[i].caseNumber.toLowerCase().contains(caseNumber.toLowerCase())) {
				foundCaseNumberLength++;
			}
		}

		// Initialize foundTitle array with size of how many matches are found
		Case[] foundCaseNumber = new Case[foundCaseNumberLength];

		// Iterate over array again using a start index and populate foundTitle array
		int foundCaseNumberIndex = 0; 

		for(int i = 0; i < cases.length; i++) {
			if(cases[i].caseNumber == null) continue;
			if(cases[i].caseNumber.toLowerCase().contains(caseNumber.toLowerCase())) {
				foundCaseNumber[foundCaseNumberIndex] = cases[i];
				foundCaseNumberIndex++;
			}
		}

		// Take care of nulls
		if(foundCaseNumberLength == 0) {
			return null;
		}
		return foundCaseNumber;
	}
}
