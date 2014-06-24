package uniandes.centralsimulator.alarms.processor;
import uniandes.centralsimulator.alarms.receiver.QueueAlarms;

public class ManagerAlarmThreads implements Runnable {

	private long countThreads;
	@Override
	public void run() {
		countThreads =0;
		while(true){
			if(QueueAlarms.getInstance().hasElements() && AdminThreads.getInstance().hasLeader()){
				countThreads++;
				AdminThreads.getInstance().runLeader(QueueAlarms.getInstance().getFirstEvent(), countThreads);
				
				System.out.println("Cantidad de hilos ejecutados: "+countThreads);
			}
				
		}

	}



}
