package uniandes.centralsimulator.alarms.receiver;


import java.util.concurrent.ConcurrentLinkedQueue;

import uniandes.centralsimulator.alarms.entities.AlarmReceive;

public class QueueAlarms  {
	private ConcurrentLinkedQueue<AlarmReceive> alarms;
	
	private static QueueAlarms queueAlarms;
	
	public static QueueAlarms getInstance(){
		if(queueAlarms ==null){
			queueAlarms = new QueueAlarms();
		}
		return queueAlarms;
	}
	
	private QueueAlarms(){
		alarms = new ConcurrentLinkedQueue<AlarmReceive>();
	}
	public boolean hasElements() {
		return !alarms.isEmpty();
	}
	
	public void putEvent(AlarmReceive alarm) {
		alarms.add(alarm);
	}

	public AlarmReceive getFirstEvent() {
		return alarms.poll();
	}

}

	