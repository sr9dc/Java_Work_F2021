package practice7;

public abstract class Food {
	static int calories;
	int temperature;
	
	public Food() {
		System.out.println("Here comes food!");
	}
	
	public abstract String eat();
}
