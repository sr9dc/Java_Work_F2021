// Sai Rajuladevi, srajulad

package lab6;

import java.util.*;

public class Movie implements Comparable<Movie>{
	String movieName;
	String movieYear;
	List<String> movieGenres = new ArrayList<>();
	
	Movie(String movieName, String movieYear){
		this.movieName = movieName;
		this.movieYear = movieYear;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.movieName, this.movieYear);
	}

	
	@Override 
	public boolean equals(Object o) {
		if (o == this)
	        return true;
	    if (!(o instanceof Movie))
	        return false;
	    Movie other = (Movie)o;
	    return (this.movieName.equalsIgnoreCase(other.movieName)) && (this.movieYear.equalsIgnoreCase(other.movieYear));
	}

	@Override
	public int compareTo(Movie o) {
		// TODO Auto-generated method stub
		return this.movieName.compareTo(o.movieName);
	}
	
}
