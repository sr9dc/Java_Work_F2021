// srajulad, Sai Rajuladevi
package lab9;

import java.util.Random;

public class ImpatientCustomer extends Customer{
	static int impatientCustomerCount;
	
	ImpatientCustomer(){
		impatientCustomerCount++;
		numberOfTickets = new Random().nextInt((MovieHall.MAX_TICKETS-MovieHall.MIN_TICKETS) + 1) 
				+ MovieHall.MIN_TICKETS;
	}
	
	boolean joinQueue() {
		if(MovieHall.customerQueue.size() > MovieHall.balkQueueLength) {
			System.out.println("***ImpatientCustomer" + this.id + " balked");
			return false; 
		}
		else {
			MovieHall.customerQueue.offer(this);
			System.out.println("ImpatientCustomer" + this.id + " joined Q");
			return true;
		}
		
	}
}
