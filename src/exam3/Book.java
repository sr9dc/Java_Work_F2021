package exam3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Book implements Comparable<Book>{
	String title;
	List<String> authors = new ArrayList<String>();
	
	Book(String title){
		this.title = title;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(title);
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		if(this == o) {
			return true;
		}
		if(getClass() != o.getClass()) {
			return false;
		}
		Book b = (Book) o;
		return this.title.equals(b.title);
	}

	@Override
	public int compareTo(Book b) {
		// TODO Auto-generated method stub
		return this.title.compareTo(b.title);
	}
}
