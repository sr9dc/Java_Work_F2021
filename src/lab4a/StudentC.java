package lab4a;

import java.util.Random;

public class StudentC extends Student implements Serviceable{
	
	static double studentCDonations; //total money donated by Section C students
	public static final int MAX_MONEY_DONATION = 10;
	
	int timeDonation;
	
	StudentC(char section, String lastName, String firstName, double income) {
		super(section, lastName, firstName, income);
		// TODO Auto-generated constructor stub
	}

	@Override
	void donate() {
		// TODO Auto-generated method stub
		donation = MAX_MONEY_DONATION;
		Student.totalMoneyDonations += donation;
		StudentC.studentCDonations += donation;
		
	}
	
	@Override
	public void serve() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		timeDonation = 1 + rand.nextInt(MAX_SERVICE_HOURS-1);
		
		totalTimeDonations+= timeDonation;
	}
}
