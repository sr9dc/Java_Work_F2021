package practice13;

public class County implements Comparable<County>{
	String countyName;
	int countZipcodes;
	
	County(String countyName, int countZipcodes){
		this.countyName = countyName;
		this.countZipcodes = countZipcodes;
	}

	@Override
	public int compareTo(County o) {
		// TODO Auto-generated method stub
		return o.countZipcodes - this.countZipcodes;
	}
}
