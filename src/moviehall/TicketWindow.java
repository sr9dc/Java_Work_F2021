package moviehall;

import java.util.LinkedList;
import java.util.List;

public class TicketWindow implements Runnable{
	static int ticketSoldCount;
	static boolean isWindowOpen = true;

	int ticketProcessingTime;

	List<Customer> customerList= new LinkedList<Customer>();

	TicketWindow(int ticketProcessingTime){
		this.ticketProcessingTime = ticketProcessingTime;
	}

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
					System.out.println("\t\t\tCustomer" + c.id + " bought " + c.numberOfTickets + " tickets");
					ticketSoldCount += c.numberOfTickets;
					customerList.add(c);
					if(ticketSoldCount >= MovieHall.maxSeats) {
						isWindowOpen = false;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
