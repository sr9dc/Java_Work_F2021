// Sai Rajuladevi, srajulad

package exam2;

public class Book extends Media{
	
	String author;
	int pages;
	
	static final int PAGE_READING_TIME = 5;


	Book(String title, String year, String author, int pages) {
		super(title, year);
		// TODO Auto-generated constructor stub
		this.author = author;
		this.pages = pages;
	}

	@Override
	int enjoy() {
		// TODO Auto-generated method stub
		System.out.println("Reading book: " + this.title + " by " + this.author + " for the next " + (this.pages * PAGE_READING_TIME) + " minutes");
		return (this.pages * PAGE_READING_TIME);
	}

}
