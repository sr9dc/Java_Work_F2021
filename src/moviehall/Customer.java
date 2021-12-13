package moviehall;

import java.util.Random;

public class Customer implements Comparable<Customer>{
	static int customerCount;
	int id;
	int numberOfTickets;
	
	Customer(){
		customerCount++;
		id = customerCount;
		
		Random r = new Random();
		numberOfTickets = (r.nextInt((MovieHall.MAX_TICKETS - MovieHall.MIN_TICKETS) + 1) ) + MovieHall.MIN_TICKETS;

	}
	
	boolean joinQueue() {
		MovieHall.customerQueue.add(this);
		System.out.println("Customer" + this.id + " joined Q" );
		return true;
	}

	@Override
	public int compareTo(Customer c) {
		// TODO Auto-generated method stub
		return c.numberOfTickets - this.numberOfTickets;
	}
}
