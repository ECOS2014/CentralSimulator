package uniandes.centralsimulator.alarms.receiver;

import uniandes.centralsimulator.alarms.processor.ManagerAlarmThreads;

public class CentralSimulator 
{
	public static void main(String[] args)
	{
		Thread manager = new Thread(new ManagerAlarmThreads());
		manager.start();
		new CentralMultiThreadServer();
		
	}
}
