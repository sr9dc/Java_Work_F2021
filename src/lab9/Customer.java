// srajulad, Sai Rajuladevi
package lab9;

import java.util.Random;

public class Customer implements Comparable<Customer>{
	static int CustomerCount;
	int id;
	int numberOfTickets;
	
	Customer() {
		//write your code here
		CustomerCount++;
		id = CustomerCount;
		numberOfTickets = new Random().nextInt((MovieHall.MAX_TICKETS-MovieHall.MIN_TICKETS) + 1) 
				+ MovieHall.MIN_TICKETS;
	}
	
	boolean joinQueue() {
		MovieHall.customerQueue.offer(this);
		System.out.println("Customer" + this.id + " joined Q");
		return true;
	}

	@Override
	public int compareTo(Customer o) {
		// TODO Auto-generated method stub
		return o.numberOfTickets - this.numberOfTickets;
	}

	
}
