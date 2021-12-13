package practice12;

public class Doctor implements Runnable {
	int consultationTime;
	private int consultedCount = 0;

	Doctor(int consultationTime){
		this.consultationTime = consultationTime;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub	
		Patient p;
		while(consultedCount < ClinicManager.maxPatientCount){
			synchronized (Clinic.patientQ) {
				p = Clinic.patientQ.poll();
			}

			if(p != null) {
				try {
					System.out.println("Consulting patient " + p.id);
					Thread.sleep(consultationTime);
					p.endTime = System.currentTimeMillis();
					ClinicManager.patientWaitTime += (p.endTime - p.startTime);
					consultedCount++;
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}


