package lab8;

public class SalesCounter implements Runnable {
	int id; 			//Unique sequential identifier for each sales counter
	static int count;	//Counts SalesCounter objects created so far


	/**SalesCounter() increments count, initializes id  */
	SalesCounter() {
		//Write your code here
		count++;
		id = count;
	}

	/** run() runs while isShopOpen is true. It does the following: 
	 * -	Poll next customer 
	 * -	Print the message: "Salescounter0: CustomerX served. Q length: Y " 
	 * -	Sleep for (processingTime x itemsBought) by Customer 
	 * -	Assign current time to Customer’s dequeueTime 
	 * -	Shop.totalQueueTime += dequeueTime – enqueueTime 
	 * -	Increment Shop.customersServed 
	 * */
	@Override
	public void run() {
		//write your code here

		while(Shop.isShopOpen) { 
			Customer c;
			for(int i = 0; i < Shop.customerQ.size(); i++) {
				synchronized(Shop.customerQ) {
					c = Shop.customerQ.get(i).poll();
				}
				if(c != null) {
					try {
						System.out.println(ThreadMart.spacer(i) + "Salescounter" + i + ": Customer" + c.id + " served with " +
								"Q length: " + Shop.customerQ.get(i).size());
						Thread.sleep(Shop.processingTime * c.itemsBought);
						c.dequeueTime = System.currentTimeMillis();
						Shop.totalQueueTime += (c.dequeueTime - c.enqueueTime);
						Shop.customersServed++;
					}
					catch (InterruptedException e) {
						e.printStackTrace();

					}

				}
			}
		}
	}
}



