package exam3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class BookLibrary {

	List<Book> booksList;  //stores all books
	Map<String, List<Book>> authorsMap = new HashMap<String, List<Book>>();  //stores all authors' names in their original 'case' as keys and their list of books as value
	String[] bookRecords; //carries data read from the file

	//do not change this method
	public static void main(String[] args) {

		BookLibrary bookLibrary = new BookLibrary();
		bookLibrary.readFile("BookAuthors.txt");
		bookLibrary.loadBooksList();
		bookLibrary.loadAuthorsMap();

		Scanner input = new Scanner(System.in);
		System.out.println("*** Welcome to Book Library ***");
		System.out.println("1. Print Books List");
		System.out.println("2. Print Authors Map");
		switch (input.nextInt()) {
		case 1: bookLibrary.printBooksList(); break;
		case 2: bookLibrary.printAuthorsMap(); break;
		default: break;
		}
		System.out.println("** Bye Bye! **"); 
		input.close();
	}


	//do not change this method
	void readFile(String filename) {
		StringBuilder fileData = new StringBuilder();
		try {
			Scanner input = new Scanner(new File(filename));
			while (input.hasNext()) {
				fileData.append(input.nextLine() + "\n");
			}
			bookRecords = fileData.toString().split("\n");
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	/**loadBooksList() loads data from bookRecords into
	 * booksList
	 */
	void loadBooksList() {
		//write your code here
		booksList = new ArrayList<Book>();
		for(String s: bookRecords) {
			Book in = new Book(s.split("\t")[0]);

			List<String> authors = new ArrayList<String>();
			for(int i = 1; i < s.split("\t").length; i++) {
				authors.add(s.split("\t")[i]);
			}

			in.authors = authors;

			booksList.add(in);
		}

		Collections.sort(booksList);
	}

	/**loadAuthorsMap() loads data from booksList into
	 * authorsMap
	 */
	void loadAuthorsMap() {
		//write your code here
		authorsMap = new TreeMap<>();

		for(Book b: booksList) {
			for(String author: b.authors) {
				if(!authorsMap.containsKey(author)){
					List<Book> authorsBooksList = new ArrayList<Book>();
					authorsBooksList.add(b);
					authorsMap.put(author, authorsBooksList); 
				}
				else {
					authorsMap.get(author).add(b);
				}
			}
		}		
	}

	/**printBooksList() prints the output
	 * as shown in the handout
	 */
	void printBooksList() {
		//write your code here
		int i = 1;
		for(Book b: booksList) {
			String authors = ""; 
			for(String author: b.authors) authors+=(author + "; ");

			System.out.println(i + ". " + b.title + "; " + authors);
			i++;
		}
	}

	/**printAuthorsMap prints the output
	 * as shown in the handout. 
	 */
	void printAuthorsMap() {
		//write your code here
		int i = 1;
		for (Map.Entry<String,List<Book>> entry : authorsMap.entrySet()) {

			String books = ""; 
			for(Book b: entry.getValue()) books+=("   -" + b.title + "\n");
			
			System.out.println(i + ". Books by " + entry.getKey() + "\n" + books);
			i++;
		}
	}

}
