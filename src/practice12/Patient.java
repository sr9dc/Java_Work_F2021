package practice12;

public class Patient {
	static int count;
	long startTime;
	long endTime;
	int id;
	
	Patient(){
		count++;
		startTime = System.currentTimeMillis();
		id = count;
	}
}
