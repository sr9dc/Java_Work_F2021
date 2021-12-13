// Sai Rajuladevi srajulad

package finals;

import java.util.Random;


public class Traffic implements Runnable{
	static int MIN_VEHICLE_DELAY = 5;
	static int MAX_VEHICLE_DELAY = 10;
	static int maxQLength;	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(Vehicle.vehicleCount < Road.maxVehicles) {
			Vehicle v;
			try {
				Random r = new Random();
				Thread.sleep(r.nextInt((MAX_VEHICLE_DELAY - MIN_VEHICLE_DELAY) + 1) + MIN_VEHICLE_DELAY);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			if(Road.problemPart == 1) {
				v = new Vehicle();
				synchronized(Road.vehicleQ) {
					v.joinVehicleQ();
					Traffic.maxQLength++;
				}
			}
			else{
				Random r2 = new Random();
				int r2Choice = r2.nextInt(4);

				if(r2Choice == 0) {
					v = new ImpatientVehicle();
				}
				else {
					v = new Vehicle();
				}

				synchronized(Road.vehicleQ) {
					v.joinVehicleQ();
					Traffic.maxQLength++;
				}

			}




			if(!TrafficLight.isGreen) {
				if(v instanceof ImpatientVehicle) {
					System.out.println("*****RED: ImpatientVehicle " + v.id + " exiting. Q length: " + Road.vehicleQ.size());
					Road.vehiclesExited++;
				}
				else {
					System.out.println("\tRED: Vehicle " + v.id + " in Q. Q length: " + Road.vehicleQ.size());
				}				
			}
		}

	}

}
