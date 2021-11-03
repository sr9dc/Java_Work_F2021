// Sai Rajuladevi, srajulad

package exam2;

public class Movie extends Media{
	
	String director;
	int duration;

	Movie(String title, String year, String director, int duration) {
		super(title, year);
		// TODO Auto-generated constructor stub
		this.director = director;
		this.duration = duration;
	}

	@Override
	int enjoy() {
		// TODO Auto-generated method stub
		System.out.println("Watching movie: " + this.title + " by " + this.director + " for the next " + this.duration + " minutes");
		return this.duration;
	}

}
