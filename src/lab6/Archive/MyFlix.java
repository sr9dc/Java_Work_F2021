// Sai Rajuladevi, srajulad

package lab6;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public class MyFlix {
	List<Movie> moviesList = new ArrayList<>();
	List<Genre> genresList = new ArrayList<>();
	String[] movieDBStrings;

	//do not change this method
	public static void main(String[] args) {
		MyFlix myFlix = new MyFlix();
		myFlix.movieDBStrings = myFlix.readMovieDB("MoviesDB.tsv");
		myFlix.loadMovies();
		myFlix.loadGenres();
		Collections.sort(myFlix.moviesList);
		Collections.sort(myFlix.genresList);
		myFlix.showMenu();
	}

	//do not change this method
	//showMenu() displays the menu for the user to choose from,
	//takes required inputs, and invokes related methods
	void showMenu() {
		Scanner input = new Scanner(System.in);
		int choice = 0;
		System.out.println("*** Welcome to MyFlix ***"); 
		System.out.println("1. Search for a movie");
		System.out.println("2. List of genres");
		System.out.println("3. Exit");
		choice = input.nextInt();
		input.nextLine();
		switch (choice) {
		case 1: {
			System.out.println("Enter the string to search in movie names");
			String searchString = input.nextLine();
			printSearchResults(findMovies(searchString));
			if(findMovies(searchString).size()==0) {
				System.out.println("Sorry! No movie found!");
			}
			break;
		}
		case 2: {
			printGenreReport();
			break;
		}
		case 3: System.out.println("Bye Bye!"); break;
		default: break;
		}
		input.close();
	}

	//do not change this method
	//readMovieDB reads all data from the MovieDB file
	//and loads each row as a string in movieDBStrings
	String[] readMovieDB(String filename) {
		StringBuilder movies = new StringBuilder();
		try {
			Scanner input = new Scanner(new File(filename));
			while (input.hasNextLine()) {
				movies.append(input.nextLine() + "\n");
			}
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return movies.toString().split("\n");
	}

	//loadMovies use data in movieDBStrings to create Movie objects 
	//and add them to moviesList.
	void loadMovies() {
		//write your code here
		for(String movieDBData : movieDBStrings) {
			String[] movieStrs = movieDBData.split("\t");

			Movie m = new Movie(movieStrs[0], movieStrs[1]);

			for(int i = 2; i < movieStrs.length; i++) {
				m.movieGenres.add(movieStrs[i]);
			}

			moviesList.add(m);
		}

	}

	//loadGenres uses data in moviesList to create Genre objects 
	//and add them to genresList
	void loadGenres() {
		//write your code here
		Iterator<Movie> moviesIterator = moviesList.iterator();

		while (moviesIterator.hasNext()) {
			Movie check = moviesIterator.next();

			for(String movieGenre : check.movieGenres) {
				Genre genreInput = new Genre(movieGenre);
				if(genresList.contains(genreInput)) {
					int genreFoundIndex = (genresList.indexOf(genreInput));
					genresList.get(genreFoundIndex).genreMovies.add(check);
				}
				else {
					genreInput.genreMovies.add(check);
					genresList.add(genreInput);
				}
			}
		}
	}

	//findMovies() returns a list of Movie objects 
	//that have the searchString in their names
	List<Movie> findMovies(String searchString) {
		//write your code here
		List<Movie> moviesReturnList = new ArrayList<>();

		Iterator<Movie> moviesIterator = moviesList.iterator();

		while (moviesIterator.hasNext()) {
			Movie check = moviesIterator.next();

			if(check.movieName.toLowerCase().contains(searchString.toLowerCase())) {
				moviesReturnList.add(check);
			}
		}

		return moviesReturnList;
	}

	//print the search output. 
	//You may use this printf statement:System.out.printf("%3d. %-50s\tYear: %s\n", ++count, movie.movieName, movie.movieYear);
	void printSearchResults(List<Movie> searchResults) {
		//write your code here

		Iterator<Movie> moviesIterator = searchResults.iterator();

		int count = 0;
		while (moviesIterator.hasNext()) {
			Movie check = moviesIterator.next();
			System.out.printf("%3d. %-50s\tYear: %s\n", ++count, check.movieName, check.movieYear);
		}

	}

	//print the genre summary report. 
	//You may use this printf statement:System.out.printf("%3d. %-15s Number of movies: %,6d%n", ++count, genre.genreName, genre.genreMovies.size());
	void printGenreReport() {
		//write your code here
		Iterator<Genre> genresIterator = genresList.iterator();

		int count = 0;
		while (genresIterator.hasNext()) {
			Genre out = genresIterator.next();
			System.out.printf("%3d. %-15s Number of movies: %,6d%n", ++count, out.genreName, out.genreMovies.size());
		}
	}
}
