package practice13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ZipcodeRunner {
	ArrayList<Zipcode> zipcodes = new ArrayList<Zipcode>();
	ArrayList<County> counties = new ArrayList<County>();

	void loadZipcodes(){
		Scanner fileContent = null;
		try {
			fileContent = new Scanner (new File("Zipcodes.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (fileContent.hasNextLine()) {
			String[] zipcodeData = fileContent.nextLine().split(",");
			zipcodes.add(new Zipcode(zipcodeData[0], zipcodeData[1], zipcodeData[2]));
		}
	}

	void loadCounties() {
		Collections.sort(zipcodes);

		Iterator<Zipcode> zipcodesIterator = zipcodes.iterator();

		if(zipcodesIterator.hasNext()) {
			counties.add(new County(zipcodesIterator.next().county, 1));
		}
		while (zipcodesIterator.hasNext()) {
			Zipcode check = zipcodesIterator.next();

			if(counties.get(counties.size()-1).countyName.equals(check.county)) {
				counties.get(counties.size()-1).countZipcodes++;
			}
			else {
				counties.add(new County(check.county, 1));
			}
		}
	}
	
	void printCounties() {
		Collections.sort(counties);
		for(County county : counties) {
			System.out.println(county.countyName + ", " + county.countZipcodes);
		}
	}

//	public class ZipcodeComparator implements Comparator<Zipcode> {
//		@Override
//		public int compare(Zipcode o1, Zipcode o2) {
//			// TODO Auto-generated method stub
//			return o1.county.compareTo(o2.county);
//		}
//		
//	}
//	
//	public class CountyComparator implements Comparator<County> {
//		@Override
//		public int compare(County o1, County o2) {
//			// TODO Auto-generated method stub
//			return o2.countZipcodes - o1.countZipcodes;
//		}
//		
//	}
	
	public static void main(String[] args) {
		ZipcodeRunner zipcodeRun = new ZipcodeRunner();
		zipcodeRun.loadZipcodes();
		zipcodeRun.loadCounties();
		zipcodeRun.printCounties();
		
	}

}
