package moviehall;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class MovieHall {
	static int examPart;
	static int maxSeats;
	static int MIN_TICKETS = 1;
	static int MAX_TICKETS = 10;
	static Queue<Customer> customerQueue = new LinkedList<>();

	int ticketProcessingTime;
	int customerDelay;
	long startTime;
	long endTime;

	QueueManager queueManager;
	TicketWindow ticketWindow;

	public static void main(String[] args) {
		MovieHall movieHall = new MovieHall();
		movieHall.getInputs();
		movieHall.startThreads();
		movieHall.printReport();
		movieHall.testResults();
	}

	void getInputs() {
		Scanner input = new Scanner(System.in);

		System.out.println("Part 1 or 2?");
		examPart = input.nextInt();

		System.out.println("Enter single ticket processing time (ms):");
		ticketProcessingTime = input.nextInt();

		System.out.println("Enter max tickets to be sold:");
		maxSeats = input.nextInt(); 

		System.out.println("Enter max customer delay (ms):");
		customerDelay = input.nextInt();
		input.close();
	}

	void startThreads() {
		queueManager = new QueueManager(customerDelay);
		ticketWindow = new TicketWindow(ticketProcessingTime);
		Thread t1 = (new Thread(queueManager)); 
		Thread t2 = (new Thread(ticketWindow)); 
		System.out.println("--------------- Detailed Customer Report ---------------");
		t1.start();
		t2.start();
		startTime = System.currentTimeMillis();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		endTime = System.currentTimeMillis();
	}

	void printReport() {

	}

	void testResults() {
		int ticketsSold = 0;  //total tickets sold

		int minTickets = MAX_TICKETS, maxTickets = MIN_TICKETS;

		//find the min, max, and total tickets sold from the customerList

		for (Customer c : ticketWindow.customerList) {

			ticketsSold += c.numberOfTickets;
			if (minTickets > c.numberOfTickets) minTickets = c.numberOfTickets;
			if (maxTickets < c.numberOfTickets) maxTickets = c.numberOfTickets;

		}

		//test whether total customerCount matches the sum of customers in customerList, //customers in customerQueue, and customers who balked

		//		assertEquals("Total customers", Customer.customerCount,
		//
		//				ticketWindow.customerList.size() + customerQueue.size() + queueManager.balkCount);

		//test total tickets sold calculated above matches the total count at TicketWindow

		assertEquals("Total tickets sold", ticketsSold, TicketWindow.ticketSoldCount );

		//test that total tickets sold is equal to or more than maxSeats 

		assertTrue(ticketsSold >= maxSeats);

		//test the minTickets calculated above is greater than or equal to MIN_TICKETS

		assertTrue("Min tickets", minTickets >= MIN_TICKETS);

		//test the maxTickets calculated above is less than or equal to MIN_TICKETS

		assertTrue("Max tickets", maxTickets <= MAX_TICKETS);
	}


}
