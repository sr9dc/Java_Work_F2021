// Sai Rajuladevi, srajulad
package lab7;

public class Nomination implements Comparable<Nomination>{
	String category;
	String title;
	String artist;
	
	Nomination(String category, String title, String artist){
		this.category = category;
		this.title = title;
		this.artist = artist;
	}
	
	@Override
	public String toString() {
		return artist + ": " + category + ": " + title;
	}
	
	@Override
	public int compareTo(Nomination n) {
		// TODO Auto-generated method stub
		return this.artist.toLowerCase().compareTo(n.artist.toLowerCase());
	}

}
