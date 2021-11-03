// Sai Rajuladevi, srajulad

package exam2;

public class EBook extends Book implements Downloadable{
	
	String format;
	int size;


	EBook(String title, String year, String author, int pages, String format, int size) {
		super(title, year, author, pages);
		// TODO Auto-generated constructor stub
		this.format = format;
		this.size = size;
	}

	@Override
	int enjoy() {
		// TODO Auto-generated method stub
		System.out.println("Reading eBook: " + this.title + " by " + this.author + " for the next " + (this.pages * PAGE_READING_TIME) + " minutes");
		return  (this.pages * PAGE_READING_TIME);
	}

	@Override
	public int download() {
		// TODO Auto-generated method stub
		System.out.println("Downloading eBook: " + this.title + " by " + this.author + " for next " + (this.size / INTERNET_SPEED) + " seconds");
		return (this.size / INTERNET_SPEED);
	}
	

}
