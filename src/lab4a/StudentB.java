package lab4a;

import java.util.Random;

public class StudentB extends Student{
	
	static double studentBDonations; //total money donated by Section B students
	public static final int MAX_MONEY_DONATION = 1000;
	

	StudentB(char section, String lastName, String firstName, double income) {
		super(section, lastName, firstName, income);
		// TODO Auto-generated constructor stub
	}


	@Override
	void donate() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		donation = 1 + rand.nextInt(MAX_MONEY_DONATION-1);
		
		Student.totalMoneyDonations += donation;
		StudentB.studentBDonations += donation;
		
	}

}
