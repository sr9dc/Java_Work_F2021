// Sai Rajuladevi, srajulad
package lab6;

import java.util.*;

public class Genre implements Comparable<Genre>{
	String genreName;
	List<Movie> genreMovies = new ArrayList<>();
	
	Genre(String genreName){
		this.genreName = genreName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.genreName);
	}
	
	@Override 
	public boolean equals(Object o) {
		if (o == this)
	        return true;
	    if (!(o instanceof Genre))
	        return false;
	    Genre other = (Genre)o;
	    return (this.genreName.equalsIgnoreCase(other.genreName));
	}
	
	@Override
	public int compareTo(Genre o) {
		// TODO Auto-generated method stub
		if(o.genreMovies.size() - this.genreMovies.size() == 0) {
			return this.genreName.compareTo(o.genreName);
		}
		return o.genreMovies.size() - this.genreMovies.size();
	}
}
