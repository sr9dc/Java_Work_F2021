// Sai Rajuladevi, srajulad
package lab7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/** GrammyAnalyst takes Grammys.txt to provide two reports and one search functionality
 */
public class GrammyAnalyst {
	/**initialize these member variables with appropriate data structures **/
	List<Nomination> nominations = new ArrayList<Nomination>();  
	Map<String, List<Nomination>> grammyMap = new HashMap<String, List<Nomination>>();  
	List<Artist> artists = new ArrayList<Artist>();
	
	public static void main(String[] args) {
		GrammyAnalyst ga = new GrammyAnalyst();
		ga.loadNominations();
		ga.loadGrammyMap();
		System.out.println("*********** Grammy Report ****************");
		ga.printGrammyReport();
		System.out.println("*********** Search Artist ****************");
		System.out.println("Enter artist name");
		Scanner input = new Scanner(System.in);
		String artist = input.nextLine();
		ga.searchGrammys(artist);
		ga.loadArtists();
		System.out.println("*********** Artists Report ****************");
		ga.printArtistsReport();
		input.close();
	}
	
	/**loadNominations() reads data from Grammys.txt and 
	 * populates the nominations list, where each element is a Nomination object
	 */
	void loadNominations() {
		//write your code here
		try {
			Scanner input = new Scanner(new File("Grammys.txt")); // Scanner iterator
			while (input.hasNextLine()) {
				String[] splitLines = input.nextLine().split(";"); // for .tsv, split on tab character
				Nomination n = new Nomination(splitLines[0].trim(), splitLines[1].trim(), splitLines[2].trim());
				nominations.add(n); // add to the returned list
			}
			input.close(); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**loadGrammyMap uses artist name in lower case as the key, and a list of 
	 * all nominations for that artist as its value. Hint: use 'nominations' list 
	 * created in previous method to populate this map.
	 */
	void loadGrammyMap() {
		//write your code here
		grammyMap = new TreeMap<>();
		for(Nomination n: nominations) {
			if(!grammyMap.containsKey(n.artist.toLowerCase())) {
				List<Nomination> inputNominations = new ArrayList<Nomination>();
				inputNominations.add(n);
				grammyMap.put(n.artist.toLowerCase(), inputNominations);
			}
			else {
				grammyMap.get(n.artist.toLowerCase()).add(n);
			}
		}
	} 
	
	/**loadArtists loads the artists array List. Each Artist object in it should have
	 * artist's name in proper case, i.e., as read from data file, and 
	 * a list of nominations for that artist. Hint: use 'grammyMap' created in 
	 * previous method to populate this list
	 */
	void loadArtists() {
		//write your code here
		for (Map.Entry<String,List<Nomination>> entry : grammyMap.entrySet()) {
			Artist a = new Artist(entry.getValue().get(0).artist, entry.getValue());
			artists.add(a);
		}
	}
	
	/**printGrammyReport prints report as shown in the handout */
	void printGrammyReport() {
		//write your code here
		int i = 1;
		Collections.sort(nominations);
		for (Nomination n : nominations) {
			System.out.println(i + ". " + n.toString());
			i++;
		}
	}
	
	/**printArtistReport prints report as shown in the handout */
	void printArtistsReport() {
		//write your code here
		int i = 1;
		Collections.sort(artists);
		for (Artist a : artists) {
			System.out.println(i + ". " + a.toString());
			i++;
		}
	}
	
	/**searchGrammys takes a string as input and makes a case-insensitive
	 * search on grammyMap. If found, it prints data about all nominations
	 * as shown in the handout.
	 */
	void searchGrammys(String artist) {
		boolean found = false;
		for (Map.Entry<String,List<Nomination>> entry : grammyMap.entrySet()) {
			if(entry.getKey().contains(artist.toLowerCase())) {
				for(Nomination n : grammyMap.get(entry.getKey())) {
					System.out.println(n.toString());
				}
				found = true;
			}
		}
		if(!found) {
			System.out.println("Sorry! " + artist + " not found!");
		}
	}
}
