package lab8;

import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class Shop implements Runnable{

	static List<Queue<Customer>> customerQ = new LinkedList<>();  
	static boolean isShopOpen = true;  //starts as true. Set to false when all customers served
	static int maxCustomer; //Maximum number of customers created
	static int customersServed; //Incremented after serving each customer
	static int processingTime;	//Time for SalesCounter to process one sale-item
	static long totalQueueTime; //Incremented  after serving each customer

	List<SalesCounter> salesCounters = new LinkedList<>(); //instances of SalesCounter
	Thread[] salesCounterThreads; //threads to run the salesCounters 
	int customerGapTime;	//interval between customer arrivals

	/** setupCounters() takes user inputs, creates SalesCounter object, 
	 * assigns it to salesCounterThread, and starts it 
	 **/
	void setupCounters() {
		//write your code here
		Scanner input = new Scanner(System.in);

		System.out.println("How many counters to open?");
		salesCounterThreads = new Thread[input.nextInt()];

		System.out.println("Sales item processing time?");
		processingTime = input.nextInt();

		System.out.println("Max customer count?");
		maxCustomer = input.nextInt();  

		System.out.println("Customer gap time?");
		customerGapTime = input.nextInt();

		input.close();

		for(int i = 0; i < salesCounterThreads.length; i++) {
			customerQ.add(new LinkedList<>());
			salesCounters.add(new SalesCounter());
			salesCounterThreads[i] = new Thread(salesCounters.get(i));
			
		}
		for(int i = 0; i < salesCounterThreads.length; i++) {
			salesCounterThreads[i].start();
		}
	}

	/** joinQueue() adds customer c to customeQ, 
	 * Prints the message "SalesCounter0: CustomerX joined with Y items. Q length:Z". 
	 * Initialize c’s enqueueTime to current time
	 */
	public void joinQueue(Customer c) {
		//write your code here
		List<Integer> customerQSizes = new LinkedList<>();
		for(int i = 0; i < salesCounterThreads.length; i++) {
			customerQSizes.add(customerQ.get(i).size());
		}
		int minIndex = customerQSizes.indexOf(Collections.min(customerQSizes));

		customerQ.get(minIndex).offer(c);

		System.out.println(ThreadMart.spacer(minIndex) + "SalesCounter" + minIndex + ": Customer" + c.id + " joined with " + 
				c.itemsBought + " items. Q length: " + customerQ.get(minIndex).size());
		c.enqueueTime = System.currentTimeMillis();

	}

	/** run() invokes setupCounters(), and runs the following 
	 * as long as CustomersServed < maxCustomer 
	 * - 	If Customer.count < maxCustomer, create new customer and pass it to joinQueue() 
	 * -	Sleep for customerGapTime 
	 * -	Set isShopOpen to false 
	 * -	Wait for salesCounterThread to join 
	 * */
	@Override
	public void run() {
		//write your code here
		setupCounters();
		while(customersServed < maxCustomer) {
			if(Customer.count < maxCustomer) {
				Customer c = new Customer();
				synchronized(customerQ) {
					joinQueue(c);
				}
			}
			try {
				Thread.sleep(new Random().nextInt(customerGapTime));
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		isShopOpen = false;
		
		try {
			for(int i = 0;  i < salesCounterThreads.length; i++) {
				salesCounterThreads[i].join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
