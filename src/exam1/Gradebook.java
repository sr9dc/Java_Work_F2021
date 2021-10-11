// Sai Rajuladevi, srajulad
package exam1;

import java.io.File;
import java.util.Scanner;

public class Gradebook {

	String[] fileData; //stores rows of data read from data file
	String[] students;	//names of students
	float[][] scores;	//students' scores in the same order as given in data file 
	float[] averageScores; //each students' average score

	//do not change this method
	public static void main(String[] args) {
		Gradebook gradebook = new Gradebook();
		gradebook.fileData = gradebook.loadData("Gradebook.txt");
		gradebook.students = gradebook.getStudents();
		gradebook.scores = gradebook.getScores();
		gradebook.averageScores = gradebook.getAverages();
		gradebook.printAverages();
	}

	/** loadData() uses filename to read the file and
	 * returns an array of String, with each 
	 * string representing one row in data file
	 * @param filename
	 * @return
	 */
	String[] loadData(String filename) {
		//write your code here
		
		// get number of lines to size the fileData array
		int lines = 0; 
		
		Scanner fileLinesScanner = null; 
		try {
			File file = new File(filename);
			fileLinesScanner = new Scanner(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		while(fileLinesScanner.hasNextLine()) {
			lines++;
			fileLinesScanner.nextLine();
		}
		
		fileLinesScanner.close();	
		
		// initialize the fileData array
		fileData = new String[lines];
		
		
		// Scan again
		
		Scanner fileScanner = null; 
		try {
			File file = new File(filename);
			fileScanner = new Scanner(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int fileDataIndex = 0;
		
		// input data into fileData Array
		while(fileScanner.hasNextLine()) {
			fileData[fileDataIndex] = fileScanner.nextLine();
			fileDataIndex++;
		}
		
		fileScanner.close();	
		
		return fileData;
		

	}

	/** getStudents() uses data in fileData array
	 * and extracts student's names from it. It returns
	 * an array of these names.
	 * @return
	 */
	String[] getStudents() {
		//write your code here
		String[] students = new String[fileData.length];
		for(int i = 0; i < fileData.length; i++) {
			Scanner nameScanner = new Scanner(fileData[i]);
			if(nameScanner.hasNext()) {
				students[i] = nameScanner.next().replaceAll(":", "");
				nameScanner.nextLine();
			}
			nameScanner.close();
		}
		return students;
	}

	/** getScores uses data stored in fileData array
	 * and extracts scores of each student in a 2D array of 
	 * float numbers. 
	 * It returns the 2D array. 
	 * @return
	 */
	float[][] getScores() {
		///write your code here
		scores = new float[fileData.length][];
		for(int i = 0; i < fileData.length; i++) {
			Scanner scoreScanner = new Scanner(fileData[i]);
			if(scoreScanner.hasNext()) {
				scoreScanner.next();
			}
			while(scoreScanner.hasNextLine()) {
				String[] stringPopulate = scoreScanner.nextLine().trim().split("[ ,]+");
				
				float[] populate = new float[stringPopulate.length];
				
				int populateIndex = 0;
				for(String score : stringPopulate) {
					populate[populateIndex] = Float.parseFloat(score);
					populateIndex++;
				}
				
				scores[i] = populate;
			}
			
			scoreScanner.close();
		}
		return scores;
	}

	/** getAverages uses data stored in score[][] array
	 * and computes average score for each student.
	 * It returns an array of these averages.
	 * @return
	 */
	float[] getAverages() {
		//write your code here
		float[] average = new float[scores.length];
		
		for(int i = 0; i < scores.length; i++) {
			float sum = 0;
			for(int j = 0; j < scores[i].length ; j++) {
				sum += scores[i][j];
			}
			average[i] = (sum / scores[i].length);
		}
		return average;
	}

	//do not change this method
	void printAverages() {
		System.out.printf("%-20s\t%s%n", "Student", "Average Score");
		System.out.println("----------------------------------------");
		for (int i = 0; i < fileData.length; i++) {
			System.out.printf("%-20s:\t%10.2f\n", students[i] , averageScores[i] );
		}
	}
}
