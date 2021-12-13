// Sai Rajuladevi srajulad
package finals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Road implements Runnable{
	static Queue<Vehicle> vehicleQ = new LinkedList<>();
	static int maxVehicles;
	static int vehiclesPassed;
	static int vehiclesExited;
	static int problemPart;

	long startTime;
	long endTime;

	public static void main(String[] args) {
		Road road = new Road();
		road.startRoad();
		road.printReport(); 
		road.checkAssertions();
	}

	void startRoad() {
		Scanner input = new Scanner(System.in);

		System.out.println("Part 1 or 2?");
		problemPart = input.nextInt();

		System.out.println("How many vehicles?");
		maxVehicles = input.nextInt();

		TrafficLight trafficLight = new TrafficLight();
		Traffic traffic = new Traffic();


		Thread t1 = new Thread(this);
		Thread t2 = new Thread(trafficLight);
		Thread t3 = new Thread(traffic);


		t1.start();
		startTime = System.currentTimeMillis();
		t2.start();
		t3.start();

		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		endTime = System.currentTimeMillis();

		input.close();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(Vehicle.vehicleCount < maxVehicles) {
			Vehicle v;
			if(TrafficLight.isGreen) {
				synchronized(vehicleQ){
					v = vehicleQ.poll();
				}
				if(v!=null) {
					if(v instanceof ImpatientVehicle) {
						System.out.println("GREEN: ImpatientVehicle " + v.id + " passed. Q length: " + Road.vehicleQ.size());
					}
					else {
						System.out.println("GREEN: Vehicle " + v.id + " passed. Q length: " + Road.vehicleQ.size());
					}
					vehiclesPassed++;
				}
			}
		}
	}

	void printReport() {
		System.out.println("---------TRAFFIC REPORT-----------");
		System.out.println("The program ran for " + (endTime-startTime) + "ms");
		System.out.println("Max Q length at traffic light was " + Traffic.maxQLength);
		System.out.println("Final Q length at traffic light was " + Road.vehicleQ.size());
		if(problemPart == 2) {
			System.out.println("Vehicles passed: " + vehiclesPassed);
			System.out.println("Vehicles exited: " + vehiclesExited);
		}
	}

	void checkAssertions() {
		assertEquals(maxVehicles, vehiclesPassed + vehiclesExited + vehicleQ.size());
		assertTrue(Traffic.maxQLength >= vehicleQ.size());
		assertTrue(Vehicle.vehicleCount == maxVehicles );
	}

}
