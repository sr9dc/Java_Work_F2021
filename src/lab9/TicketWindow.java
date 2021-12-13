// srajulad, Sai Rajuladevi
package lab9;

import java.util.LinkedList;
import java.util.List;


public class TicketWindow implements Runnable{

	static int ticketSoldCount;
	static boolean isWindowOpen = true;

	int ticketProcessingTime;
	List<Customer> customerList = new LinkedList<>();


	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(isWindowOpen) {
			Customer c;
			synchronized(MovieHall.customerQueue) {
				c = MovieHall.customerQueue.poll();
			}
			if(c != null) {
				try {
					Thread.sleep(ticketProcessingTime * c.numberOfTickets);
					MovieHall.totalTime += (ticketProcessingTime * c.numberOfTickets);
					if(c instanceof ImpatientCustomer) {
						System.out.println("\t\tImpatientCustomer" + c.id + " bought " + c.numberOfTickets + " tickets");
					}
					else{
						System.out.println("\t\tCustomer" + c.id + " bought " + c.numberOfTickets + " tickets");
					}
					ticketSoldCount += c.numberOfTickets;
					customerList.add(c);
				}
				catch (InterruptedException e) {
					e.printStackTrace();

				}
			}
			
			if(ticketSoldCount >= MovieHall.maxSeats) {
				isWindowOpen = false;
			}
		}
		
	}
	
	TicketWindow(int ticketProcessingTime){
		this.ticketProcessingTime = ticketProcessingTime;
	}
}
