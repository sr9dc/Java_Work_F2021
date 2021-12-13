// Sai Rajuladevi srajulad

package finals;


public class TrafficLight implements Runnable{
	static int TRAFFIC_LIGHT_DELAY = 100;
	static boolean isGreen = true;
 
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(Vehicle.vehicleCount < Road.maxVehicles) {
			try {
				Thread.sleep(TRAFFIC_LIGHT_DELAY);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			isGreen = !isGreen;
		}
	}
}