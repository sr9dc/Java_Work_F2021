// Sai Rajuladevi, srajulad
package lab7;

import java.util.List;

public class Artist implements Comparable<Artist>{
	String name;
	List<Nomination> nominations;
	
	Artist(String name, List<Nomination> nominations){
		this.name = name;
		this.nominations = nominations;
	}
	
	@Override
	public String toString() {
//		if(nominations.size()null) {
//			return name + ": " + nominations.size();
//		}
		return name + ": " + nominations.size();
	}

	@Override
	public int compareTo(Artist a) {
		// TODO Auto-generated method stub
		if(a.nominations.size()-this.nominations.size()==0) {
			return this.name.toLowerCase().compareTo(a.name.toLowerCase());
		}
		return a.nominations.size()-this.nominations.size();
	}

}
