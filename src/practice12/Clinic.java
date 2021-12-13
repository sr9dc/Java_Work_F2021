package practice12;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Clinic implements Runnable {
	static Queue<Patient> patientQ = new LinkedList<>();
	int patientCount;
	long clinicOpenTime;
	int maxPatientArrivalGap;
	
	Clinic(int maxPatientArrivalGap){
		this.maxPatientArrivalGap = maxPatientArrivalGap;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		clinicOpenTime = System.currentTimeMillis();
		
		while(patientCount < ClinicManager.maxPatientCount){
			try {
				synchronized (patientQ) {
					patientQ.offer(new Patient());
					patientCount++;
				}
				Thread.sleep(new Random().nextInt(maxPatientArrivalGap));
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
