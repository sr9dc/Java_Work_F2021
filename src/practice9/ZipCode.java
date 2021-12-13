package practice9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class ZipCode {

	static HashMap<String, ArrayList<String>> zipMap = new HashMap<String, ArrayList<String>>();

	static void search() {
		int total = 0;
		int dup_count = 0;
		int dirty = 0;
		try {
			Scanner input = new Scanner(new File("DirtyZipcodes.txt")); // Scanner iterator
			while (input.hasNextLine()) {
				String[] splitLines = input.nextLine().split(","); // for .tsv, split on tab character
				if(!zipMap.containsKey(splitLines[0])) {
					ArrayList<String> values = new ArrayList<String>();
					values.add(splitLines[1]);
					values.add(splitLines[2]);
					zipMap.put(splitLines[0], values);
				}
				else {
					if(splitLines[1].equals(zipMap.get(splitLines[0]).get(0)) &&
							splitLines[2].equals(zipMap.get(splitLines[0]).get(1))) {
						dup_count++;
						System.out.println("Duplicate found: " + splitLines[0] + ", " +
						splitLines[1] + ", " + splitLines[2]);
					}
					else {
						String oldZip = splitLines[0];
						ArrayList<String> old_values = zipMap.get(oldZip);

						ArrayList<String> new_values = new ArrayList<String>();
						new_values.add(splitLines[1]);
						new_values.add(splitLines[2]);
						zipMap.replace(splitLines[0], new_values);
						
						System.out.println("Overwritten " + oldZip +
								", " + old_values.get(0) + ", " + old_values.get(1) +
								" with " + splitLines[0] +
								", " + new_values.get(0) + ", " + new_values.get(1));
						dirty++;
					}
				}
				total++;
			}
			input.close(); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println(zipMap.size() + " record loaded, " + dup_count + " duplicates ignored, " + dirty + " dirty zipcodes overwritten");
	}
	
	
	public static void main(String[] args) {
		search();
		System.out.println(zipMap.get("90401").get(0) + " " + zipMap.get("90401").get(1));
	}
	
}
