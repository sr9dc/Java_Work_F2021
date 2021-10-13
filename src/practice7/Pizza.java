package practice7;

public class Pizza extends Food implements Heatable{
	
	static final int PIZZA_CALORIES = 800;
	
	public Pizza() {
		calories += PIZZA_CALORIES;
		System.out.println("Serving Ice Cream");
	}

	@Override
	public String eat() {
		// TODO Auto-generated method stub
		return "Chomp Chomp";
	}

	@Override
	public void heatIt() {
		// TODO Auto-generated method stub
		this.temperature = Heatable.HOTSERVINGTEMPERATURE;
	}

}
