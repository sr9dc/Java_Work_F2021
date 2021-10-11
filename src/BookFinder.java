import java.io.File;
import java.util.Scanner;

public class BookFinder {
	
	static String fileName;
	static String searchString;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getUserInputs();
		
		StringBuilder loadedRecords = loadRecords();
		
		String[] searchRecords = searchRecords(loadedRecords);
		
		printOutput(searchRecords);
		
	}
	
	static void getUserInputs() {
		Scanner input = new Scanner(System.in); 
		
		System.out.println("Enter the file name:");
		fileName = input.nextLine();
		
		System.out.println("Enter what you are looking for");
		searchString = input.nextLine();	
		
		input.close();
	}
	
	static StringBuilder loadRecords() {
		StringBuilder fileContent = new StringBuilder();
		
		Scanner fileScanner = null; 
		try {
			File file = new File(fileName);
			fileScanner = new Scanner(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		while(fileScanner.useDelimiter(";").hasNext()) {
			fileContent.append(fileScanner.next()+"; ");
		}
		
		return fileContent;
	}
	
	static String[] searchRecords(StringBuilder fileContent) {
		String[] books = fileContent.toString().split("\n");
		
		// get length of new array to create
		int arrLen = 0; 
		for(String line : books) {
			if(line.toLowerCase().contains(searchString.toLowerCase())) {
				arrLen++;
			}
		}
		
		String[] foundRecords = new String[arrLen];
		
		int index = 0; 
		for(String line : books) {
			if(line.toLowerCase().contains(searchString.toLowerCase())) {
				foundRecords[index] = line;
				index++;
			}
		}
		
		
		return foundRecords;
	}
	
	static void printOutput(String[] foundRecords) {
		System.out.println("Found records: " + foundRecords.length);
		
		if(foundRecords.length > 0) {
			for(String line : foundRecords) {
				System.out.println(line);
			}
		}
		else {
			System.out.println("Sorry " + searchString + " not found in " + fileName);
		}
		
		
	}

}
