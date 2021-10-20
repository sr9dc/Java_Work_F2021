package practice8;

public class Cat extends Pet{
	static int catCount; 
	
	@Override
	String talk() {
		petCount++;
		catCount++;
		return "Meow...";
	}

}
