// srajulad, Sai Rajuladevi
package hw1;

import java.util.Scanner;
import java.util.Arrays;

public class CyberCop {

	public static final String DATAFILE = "data/FTC-cases-TSV.txt";
	CCModel ccModel = new CCModel();
	SearchEngine searchEngine = new SearchEngine();

	Scanner input = new Scanner(System.in);

	String lineBreak = "---------------------------------------------------------------------------";

	/**main() instantiates CyberCop and then invokes dataManager's loadData
	 * and loadCases() methods
	 * It then invokes showMenu to get user input
	 * @param args
	 */
	//Do not change this method
	public static void main(String[] args) {
		CyberCop cyberCop = new CyberCop();
		cyberCop.ccModel.loadData(DATAFILE);
		cyberCop.ccModel.loadCases();
		cyberCop.showMenu();
	}

	/**showMenu() shows the menu. 
	 * Based on the user choice, it invokes one of the methods:
	 * printSearchResults(), printCaseTypeSummary(), or printYearwiseSummary()
	 * The program exits when user selects Exit option. 
	 * See the hand-out for the expected layout of menu-UI
	 */
	void showMenu() {
		int choice = 0;
		Scanner input = new Scanner(System.in);
		do {
			System.out.println("*** Welcome to CyberCop! ***");
			System.out.println("1. Search cases for a company");
			System.out.println("2. Search cases in a year");
			System.out.println("3. Search case number");
			System.out.println("4. Print Case-type Summary");
			System.out.println("5. Print Year-wise Summary");
			System.out.println("6. Exit");
			choice = input.nextInt();
			input.nextLine(); //clear the buffer
			SearchEngine search = new SearchEngine();
			System.out.println(lineBreak);

			// Based on the choices, use switch statement to select functionality
			switch (choice) {
			case 1: {
				System.out.println("Enter the search string"); 
				String searchString = input.nextLine();

				// store the filtered array returned from the search function
				Case[] foundTitle = search.searchTitle(searchString, ccModel.cases);

				// general method based on the filtered array
				printSearchResults(searchString, foundTitle);

				break;
			}
			case 2: {
				System.out.println("Enter the search year as YYYY"); 
				String searchString = input.nextLine();

				// store the filtered array returned from the search function
				Case[] foundTitle = search.searchYear(searchString, ccModel.cases);

				// general method based on the filtered array
				printSearchResults(searchString, foundTitle);

				break;
			}
			case 3: {
				System.out.println("Enter case number"); 
				String searchString = input.nextLine();

				// store the filtered array returned from the search function
				Case[] foundTitle = search.searchCaseNumber(searchString, ccModel.cases);

				// general method based on the filtered array
				printSearchResults(searchString, foundTitle);

				break;
			}
			case 4:
			{
				printCaseTypeSummary(); // general method for summary
				break;
			}
			case 5:{
				printYearwiseSummary(); // general method for summary
				break;
			}
			default: System.out.println("Goodbye!");break;
			}
		} while (choice != 6);
		input.close();
		System.out.println(lineBreak);
	}

	/**printSearchResults() takes the searchString and array of cases as input
	 * and prints them out as per the format provided in the handout
	 * @param searchString
	 * @param cases
	 */
	void printSearchResults(String searchString, Case[] cases) {
		//write your code here
		System.out.println(lineBreak);
		if(cases == null || cases.length == 0) {
			System.out.println("Sorry, no search results found for " + searchString);
		}
		else {
			// Print out formatted strings
			System.out.println(cases.length + " case(s) found for " + searchString);
			System.out.println(lineBreak);
			System.out.printf("%-5.5s%-13.13s%-80.80s   %-20.20s%-60.60s %n", "#.", "Last update", "Case Title", "Case Type", "Case/File Number");
			System.out.println(lineBreak);
			for(int i = 0; i < cases.length; i++) {
				// Conditions to ensure that null does not appear in print
				String caseType = (cases[i].caseType == null) ? "" : cases[i].caseType;
				String caseNumber = (cases[i].caseNumber == null) ? "" : cases[i].caseNumber;

				// Print out in clean format- the caseTitle will get cut off if the length is greater than 60 characters
				System.out.printf("%-5.5s%-13.13s%-80.80s   %-20.20s%-60.60s %n", (String.valueOf(i+1)+"."), cases[i].caseDate, cases[i].caseTitle, caseType, caseNumber);
			}
		}
		System.out.println(lineBreak);
	}

	/**printCaseTypeSummary() prints a summary of
	 * number of cases of different types as per the 
	 * format given in the handout.
	 */
	void printCaseTypeSummary() {
		//write your code here

		// Create counts for print
		int administrativeCount = 0; 
		int federalCount = 0;
		int unknownCount = 0;

		for(Case c: ccModel.cases) {
			// check for null first to use string equals method without issues
			if(c.caseType == null){unknownCount++; continue;}
			if(c.caseType.equals("Administrative")) administrativeCount++;
			if(c.caseType.equals("Federal")) federalCount++;
		}

		// Print out counts
		System.out.println("*** Case Type Summary Report ***");
		System.out.println("No. of Administrative cases: " + administrativeCount);
		System.out.println("No. of Federal cases: " + federalCount);
		System.out.println("No. of unknown case types: " + unknownCount);
		System.out.println(lineBreak);
	}

	/**printYearWiseSummary() prints number of cases in each year
	 * as per the format given in the handout
	 */
	void printYearwiseSummary() {
		//write your code here
		System.out.println("\t\t\t*** Year-wise Summary Report ***");
		System.out.println("\t\t\t*** Number of FTC cases per year ***");

		// use index to populate a case years array
		int fullYearsIndex = 0;
		int[] fullYears = new int[ccModel.cases.length];

		for(Case c: ccModel.cases) {
			fullYears[fullYearsIndex] = c.getYear();
			fullYearsIndex++;
		}

		// sort the years array to arrange from min to max range of years
		Arrays.sort(fullYears);

		// get the min and max year range
		int yearStart = fullYears[0];
		int yearEnd = fullYears[fullYears.length-1];

		// get the counts for each year- and print out in correct table format
		for(int i = yearEnd; i >=yearStart; i--) {
			int yearCount = searchEngine.searchYear(String.valueOf(i), ccModel.cases).length;

			// ensures that 5 yearCounts appear per row
			if((yearEnd-i) % 5 == 0) {
				System.out.println();
			}
			System.out.printf("\t%d: %3s", i, String.valueOf(yearCount));
		} 
		System.out.println("\n" + lineBreak);
	}


}
