package uniandes.centralsimulator.alarms.receiver;

import uniandes.centralsimulator.alarms.cache.CentralAlarmEvaluator;
import uniandes.centralsimulator.alarms.processor.ManagerAlarmThreads;

public class CentralSimulator 
{
	public static void main(String[] args)
	{
		Thread manager = new Thread(new ManagerAlarmThreads());
		try {
			CentralAlarmEvaluator.getInstance().loadRules();
		} catch (Exception e) {
			e.printStackTrace();
		}
		manager.start();
		new CentralMultiThreadServer();
		
	}
}
