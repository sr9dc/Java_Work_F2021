package practice7;

public class IceCream extends Food{
	static final int ICECREAM_CALORIES = 200;

	public IceCream() {
		calories += ICECREAM_CALORIES;
		System.out.println("Serving Ice Cream");
	}

	/**eat() method returns the 
	 * String "crunch crunch"
	 */
	@Override
	public String eat() {
		return "Slurp Slurp";
	}

}
