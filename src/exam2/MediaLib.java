// Sai Rajuladevi, srajulad
package exam2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MediaLib {

	Media[] media; //to hold all types of media

	//please DO NOT change the main method
	public static void main(String[] args) {
		MediaLib mediaLib = new MediaLib();
		mediaLib.loadMedia("Media.txt");
		int index = 0;
		System.out.println("*** Welcome to MediaLib ***");
		for (Media m: mediaLib.media) {
			System.out.printf("%2d. %-10s %-20s \t %10s %n", ++index, m.getClass().getSimpleName(), m.title, m.year);
		}
		System.out.println("------------------------------");
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the media number you would like to enjoy today?");
		int choice = input.nextInt();
		input.nextLine();
		input.close();
		mediaLib.selectMedia(choice);
	}

	//loadMedia reads the data from the file named filename 
	// and loads different types of media into media[] array
	void loadMedia(String filename) {
		//write your code here
		Scanner fileContent = null;
		StringBuilder mediaData = new StringBuilder();
		try {
			fileContent = new Scanner (new File("Media.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (fileContent.hasNextLine()) {
			mediaData.append(fileContent.nextLine() + "\n");
		}
		String[] mediaDataArr = mediaData.toString().split("\n"); 
		
		media = new Media[mediaDataArr.length];
		
		int mediaIndex = 0;
		
		for(String mediaItem : mediaDataArr) {
			String[] mediaItems = mediaItem.split(","); 

			if(mediaItems[0].equalsIgnoreCase("Book")) {
				media[mediaIndex] = new Book(mediaItems[1].trim(), mediaItems[4].trim(), 
						mediaItems[2].trim(), Integer.parseInt(mediaItems[3].trim()));
			}
			if(mediaItems[0].equalsIgnoreCase("Movie")) {
				media[mediaIndex] = new Movie(mediaItems[1].trim(), mediaItems[4].trim(), 
						mediaItems[2].trim(), Integer.parseInt(mediaItems[3].trim()));
			}
			if(mediaItems[0].equalsIgnoreCase("EBook")) {
				media[mediaIndex] = new EBook(mediaItems[1].trim(), mediaItems[4].trim(), 
						 mediaItems[2].trim(), Integer.parseInt(mediaItems[3].trim()), mediaItems[5].trim(), Integer.parseInt(mediaItems[6].trim()));
			}
			
			mediaIndex++;
		}
	}

	//selectMedia takes the user choice, uses it to index into media[]
	//and invokes the media' enjoy().
	//For EBooks, it needs to invoke download() before invoking enjoy()
	void selectMedia(int choice) {
		//write your code here
		if(media[choice-1] instanceof EBook) {
			((EBook)media[choice-1]).download();
		}
		media[choice-1].enjoy();

	}

}
