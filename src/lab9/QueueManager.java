// srajulad, Sai Rajuladevi
package lab9;

import java.util.Random;


public class QueueManager implements Runnable{
	int customerDelay;
	int balkCount;

	QueueManager(int customerDelay){
		this.customerDelay = customerDelay;
	}

	@Override
	public void run() { 
		// TODO Auto-generated method stub
		while(TicketWindow.isWindowOpen) {
			try {
				int delayTime = new Random().nextInt(customerDelay);
				MovieHall.totalTime += delayTime;
				Thread.sleep(delayTime);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}

			Customer c; 

			if(MovieHall.examPart == 1) {
				c = new Customer();
				synchronized(MovieHall.customerQueue) {
					c.joinQueue();
				}
			}

			if(MovieHall.examPart == 2) {
				if(new Random().nextInt(2) == 0) {
					c = new Customer();
					synchronized(MovieHall.customerQueue) {
						c.joinQueue();
					}
				}
				else {
					c = new ImpatientCustomer();
					synchronized(MovieHall.customerQueue) {
						if(!c.joinQueue()) {
							balkCount++;
						}
					}

				}
			}

		}
	}
}
