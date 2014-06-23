package uniandes.centralsimulator.alarms.processor;
import uniandes.centralsimulator.alarms.receiver.QueueAlarms;

public class ManagerAlarmThreads implements Runnable {

	@Override
	public void run() {

		while(true){
			if(QueueAlarms.getInstance().hasElements() && AdminThreads.getInstance().hasLeader())
				AdminThreads.getInstance().runLeader(QueueAlarms.getInstance().getFirstEvent());
		}

	}



}
