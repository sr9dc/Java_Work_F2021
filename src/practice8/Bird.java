package practice8;

public class Bird extends Pet{	
	static int birdCount;
	
	@Override
	String talk() {
		petCount++;
		birdCount++;
		return "Tweet...";
	}

}
