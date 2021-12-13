// Sai Rajuladevi srajulad
package finals;

public class ImpatientVehicle extends Vehicle{
	static int vehicleCount;
	int id;

	static int Q_TOO_LONG_LENGTH = 5;


	ImpatientVehicle(){
		vehicleCount++;
		id = vehicleCount;
	}

	@Override
	boolean joinVehicleQ() {
		if(Road.vehicleQ.size() >= Q_TOO_LONG_LENGTH) { 
			Road.vehiclesExited++;
			return false;
		}
		else {
			Road.vehicleQ.offer(this);
			return true;
		}
	}
}
