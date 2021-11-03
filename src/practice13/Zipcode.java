package practice13;

public class Zipcode implements Comparable<Zipcode>{
	String zipcode;
	String city;
	String county;
	
	Zipcode(String zipcode, String city, String county){
		this.zipcode = zipcode;
		this.city = city;
		this.county = county;
	}

	@Override
	public int compareTo(Zipcode o) {
		// TODO Auto-generated method stub
		return this.county.compareTo(o.county);
	}
	
	
}
