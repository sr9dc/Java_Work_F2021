// srajulad, Sai Rajuladevi
package lab9;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class MovieHall {
	static int examPart;
	static int maxSeats;
	static int MIN_TICKETS = 1;
	static int MAX_TICKETS = 10;
	static Queue<Customer> customerQueue = new LinkedList<>();

	static int balkQueueLength;

	int ticketProcessingTime;
	int customerDelay;
	long startTime;
	long endTime;
	QueueManager queueManager;
	TicketWindow ticketWindow;


	static int totalTime = 0;
 
	public static void main(String[] args) {
		MovieHall movieHall = new MovieHall();
		movieHall.getInputs();
		movieHall.startThreads();
		movieHall.printReport();
		movieHall.testResults();
	}

	public void getInputs() {
		Scanner input = new Scanner(System.in);

		System.out.println("Part 1 or 2?");
		examPart = input.nextInt();


		System.out.println("Enter single ticket processing time (ms)");
		ticketProcessingTime = input.nextInt();

		System.out.println("Enter max tickets to be sold:");
		maxSeats = input.nextInt();

		System.out.println("Enter max customer delay (ms)?");
		customerDelay = input.nextInt();

		if(examPart == 2) {
			System.out.println("Enter impatient customer's balk-queue-length?");
			balkQueueLength = input.nextInt();
		}

		input.close();
	}

	public void startThreads() {

		queueManager = new QueueManager(customerDelay);
		Thread thread1 = (new Thread(queueManager));
		ticketWindow = new TicketWindow(ticketProcessingTime);
		Thread thread2 = (new Thread(ticketWindow));
		System.out.println("--------------- Detailed Customer Report ---------------");
		startTime = System.currentTimeMillis();
		thread1.start();
		thread2.start();
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		endTime = System.currentTimeMillis();

	}

	public void printReport() {
		System.out.println("--------------- Part 1 Report ---------------");
		System.out.printf("%-40.40s    : %-5.5s", "Ticket window open duration",  endTime - startTime + "ms");
		System.out.println();
		System.out.printf("%-40.40s    : %-5.5s", "Total customers",  Customer.CustomerCount);
		System.out.println();
		System.out.printf("%-40.40s    : %-5.5s", "Customers who bought tickets",  ticketWindow.customerList.size());
		System.out.println();
		System.out.printf("%-40.40s    : %-5.5s", "Customers in queue when window closed",  Customer.CustomerCount - ticketWindow.customerList.size());
		System.out.println("\n");
		System.out.printf("%-40.40s    : %-5.5s", "Total tickets sold",  TicketWindow.ticketSoldCount);
		System.out.println();

		if(examPart == 2) {
			System.out.println("--------------- Part 2 Report ---------------");
			System.out.printf("%-40.40s    : %-5.5s", "Impatient Customers",  ImpatientCustomer.impatientCustomerCount);
			System.out.println();
			System.out.printf("%-40.40s    : %-5.5s", "Customers who balked",  queueManager.balkCount);
			System.out.println();
			
		}

		System.out.println("--------------- Customer Summary Report ---------------");

		Collections.sort(ticketWindow.customerList);
		int i = 1;
		int total = 0;
		for(Customer c : ticketWindow.customerList) {
			total += c.numberOfTickets;
			if(c instanceof ImpatientCustomer) {
				System.out.printf("%-5.5s%-30.30s%-20.20s%-20.20s", i + ".", "ImpatientCustomer " 
						+ c.id + " bought:",  c.numberOfTickets + " tickets" 
						, "Cumulative total: " + total);
				System.out.println();
			}
			else{
				System.out.printf("%-5.5s%-30.30s%-20.20s%-20.20s", i + ".", "Customer " 
						+ c.id + " bought:",  c.numberOfTickets + " tickets"
						, "Cumulative total: " + total);
				System.out.println();
			}
			i+=1;
		}

	}

	public void testResults() {

		int ticketsSold = 0;  //total tickets sold

		int minTickets = MAX_TICKETS, maxTickets = MIN_TICKETS;

		//find the min, max, and total tickets sold from the customerList

		for (Customer c : ticketWindow.customerList) {

			ticketsSold += c.numberOfTickets;
			if (minTickets > c.numberOfTickets) minTickets = c.numberOfTickets;
			if (maxTickets < c.numberOfTickets) maxTickets = c.numberOfTickets;

		}

		//test whether total customerCount matches the sum of customers in customerList, //customers in customerQueue, and customers who balked

		assertEquals("Total customers", Customer.CustomerCount,

				ticketWindow.customerList.size() + customerQueue.size() + queueManager.balkCount);


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
