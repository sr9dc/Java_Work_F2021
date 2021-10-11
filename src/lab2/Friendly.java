// Sai Rajuladevi srajulad
package lab2;

import java.io.File;
import java.util.Scanner;

public class Friendly {

	String[] persons;
	String[][] personFriends;

	public static void main(String[] args) {
		Friendly friendly = new Friendly();
		friendly.readFriends("friends.txt");
		friendly.getInputOutput();
	}

	//do not change this method
	void getInputOutput() {
		int choice = 0;
		Scanner input = new Scanner(System.in);
		do {
			System.out.println("*** Welcome to Friendly! ***");
			System.out.println("1. Find the number of friends a person has");
			System.out.println("2. Find the number of common friends between two persons");
			System.out.println("3. Find the names of common friends between two persons");
			System.out.println("4. Exit");
			choice = input.nextInt();
			input.nextLine(); //clear the buffer
			switch (choice) {
			case 1: {
				System.out.println("Enter the person's name"); 
				String name = input.nextLine();
				String[] friends = findFriends(name);
				if (friends != null) {
					System.out.printf("%s has %d friends%n", name, friends.length);
					int count = 0;
					for (String s : friends ) {
						System.out.println(++count + ". " + s );
					}
				} else System.out.println("Sorry! No friends found!");
				System.out.println("-----------------------------");
				break;
			}
			case 2: {
				System.out.println("Enter first person's name");
				String name1 = input.nextLine();
				System.out.println("Enter second person's name");
				String name2 = input.nextLine();
				System.out.printf("%s and %s have %d common friends%n", name1, name2, countCommonFriends(name1, name2));
				System.out.println("-----------------------------");
				break;
			}
			case 3: {
				System.out.println("Enter first person's name");
				String name1 = input.nextLine();
				System.out.println("Enter second person's name");
				String name2 = input.nextLine();
				String[] commonFriends = findCommonFriends(name1, name2);
				if (commonFriends != null) {
					System.out.printf("%s and %s have %d common friends%n", name1, name2, commonFriends.length);
					int count = 0;
					for (String s : commonFriends) {
						System.out.println(++count + ". " + s);
					}
				} else System.out.println("Sorry! No match found!");
				System.out.println("-----------------------------");
				break;
			}
			default: System.out.println("Goodbye!");break;
			}
		} while (choice != 4);
		input.close();
	}


	/** readFriends() reads the file with filename to 
	 * populate persons and personFriends arrays
	 */
	void readFriends(String filename) {
		//write your code here
		StringBuilder fileContent = new StringBuilder();
		
		Scanner fileScanner = null; 
		try {
			File file = new File(filename);
			fileScanner = new Scanner(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		while(fileScanner.useDelimiter(" ").hasNext()) {
			fileContent.append(fileScanner.next()+ " ");
		}
		
		String[] read = fileContent.toString().split("\n");

		persons = new String[read.length];
		personFriends = new String[read.length][];
		
		for(int i = 0; i < read.length; i++) {
			Scanner nameScanner = new Scanner(read[i]);
			if(nameScanner.hasNext()) {
				persons[i] = nameScanner.next().replaceAll(":", "");
			}
						 
			while(nameScanner.hasNextLine()) {
				String[] populate = nameScanner.nextLine().trim().split("[ ,]+");
				personFriends[i] = populate;
			}
			nameScanner.close();	
		}
	}

	/** given a name, returns an array of friends a person has
	 * If the name is not found, it returns null
	 */
	
	String[] findFriends(String name) {
		//write your code here
		int friendsIndex = -1;
		
		for(int i = 0; i < persons.length; i++) {
			if(name.equalsIgnoreCase(persons[i])) {
				friendsIndex = i;
			}
		}
		if(friendsIndex > -1) {
			return personFriends[friendsIndex];
		}
		else {
			return null; 
		}
		
	}

	/** given two names, returns how many common friends they have */
	int countCommonFriends(String name1, String name2) {
		//write your code here
		
		if(findFriends(name1) == null || findFriends(name2) == null) {
			return 0;
		}
		
		int count = 0; 
		
		for(int i = 0; i < findFriends(name1).length; i++) {
			for(int j = 0; j < findFriends(name2).length; j++) {
				if(findFriends(name1)[i].equalsIgnoreCase(findFriends(name2)[j])) {
					count++;
				}
			}
		}

		return count;
	}

	/**given two names, returns an array of names of common friends. 
	 * If there are no common friends, then it returns an empty array, i.e. array of size 0*/
	String[] findCommonFriends(String name1, String name2) {
		//write your code here
		int commonFriendsSize = countCommonFriends(name1, name2);
		
		if(findFriends(name1) == null || findFriends(name2) == null) {
			return null;
		}

		String[] common = new String[commonFriendsSize];
		
		int commonIndex = 0; 
		
		for(int i = 0; i < findFriends(name1).length; i++) {
			for(int j = 0; j < findFriends(name2).length; j++) {
				if(findFriends(name1)[i].equalsIgnoreCase(findFriends(name2)[j])) {
					common[commonIndex] = findFriends(name1)[i];
					commonIndex++;
				}
			}
		}
		return common;
	}

}
