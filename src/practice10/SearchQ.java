package practice10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class SearchQ {
	String fileName; 		//stores file name input by the user
	String searchWord;		//stores search word input by user
	StringBuilder fileContent = new StringBuilder();	//stores file content
	Queue<String> searchHistory = new LinkedList<>();	//stores last 5 successful searches
	ArrayList<Integer> positions;						//stores the positions of last successful searchWord
	Scanner input = new Scanner(System.in);				//takes user input for filename and searchWord

	public static void main(String[] args) {
		SearchQ sq = new SearchQ();
		sq.readFile();
		sq.getSearchWord();
		while (sq.searchWord.length()>0) {
			sq.printSearchHistory();
			sq.getSearchWord();
		} 
		System.out.println("Bye Bye");
	}

	/**readFile() asks the user for the filename and reads its contents into fileContent
	 */
	public void readFile()  {
		System.out.println("Enter file name");
		fileName = input.nextLine();
		try {
			Scanner fileInput = new Scanner (new File(fileName));
			while (fileInput.hasNextLine()) {
				fileContent.append(fileInput.nextLine() + "\n");
			}
			fileInput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/************************** Write your code below this line ******************************/

	/**getSearchWord() asks user for searchWord, initializes searchWord with the input
	 * It then invokes getWordPositions(searchWord) which returns positions arraylist
	 * If positions arraylist's size is 0, it means the word wasn't found so it prints a message accordingly
	 * else, it checks if searchHistory size is >= 5. If yes, it removes (polls) the queue. 
	 * If not, it adds (offers) the new search word
	 * 
	 */
	public void getSearchWord() {
		//write your code here
		System.out.println("Enter search word. To quit, simply press Enter with no word");
		searchWord = input.nextLine();
		if(searchWord == null) {
			System.out.println("Bye Bye");
		}
		positions = getWordPositions(searchWord);

		if(positions.size() == 0) {
			System.out.println("Sorry " + searchWord + " not found");
		}
		else {
			if(searchHistory.size() >= 5) {
				searchHistory.poll();
			}
			searchHistory.add(searchWord);
		}
	}

	/** printSearchHistory() prints the output as required
	 * using the positions arrayList and the searchHistory queue
	 */
	public void printSearchHistory() {
		//write your code here
		System.out.print(searchWord + " found in " + positions.size() + " positions: ");
		for(int position : positions) {
			System.out.print(position + " ");
		};

		System.out.println("\n***Your search history***");
		for(String historyItem: searchHistory) {
			System.out.println(historyItem);
		}

	}

	/** getWordPositions() searches for the searchWord in the fileContent
	 *  It uses "[^a-zA-Z0-9']+" as the regex for parsing the words.
	 *  It stores all positions at which the word is found in the positions arrayList
	 */
	public ArrayList<Integer> getWordPositions(String searchWord) {
		//write your code here
		String regex = "[^a-zA-Z0-9']+";
		
		String[] matches = fileContent.toString().split(regex);

		ArrayList<Integer> wordPositions = new ArrayList<>();

		for(int i = 0; i < matches.length; i++){
			if(matches[i].equalsIgnoreCase(searchWord)) {
				wordPositions.add(i);
			}
		}
		return wordPositions;
	}
}