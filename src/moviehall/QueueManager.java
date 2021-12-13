package moviehall;

import java.util.Random;

public class QueueManager implements Runnable{
	int customerDelay;

	QueueManager(int customerDelay){
		this.customerDelay = customerDelay;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(TicketWindow.isWindowOpen) {
			try {
				Thread.sleep(new Random().nextInt(customerDelay));
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}

			Customer c = new Customer();
			synchronized(MovieHall.customerQueue) {
				c.joinQueue();
			}
		}
	}
}

