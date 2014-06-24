package uniandes.centralsimulator.alarms.receiver;

import uniandes.centalsimulator.reader.AdminWriter;
import uniandes.centralsimulator.alarms.cache.CentralAlarmEvaluator;
import uniandes.centralsimulator.alarms.processor.ManagerAlarmThreads;

public class CentralSimulator 
{
	public static void main(String[] args)
	{
		Thread manager = new Thread(new ManagerAlarmThreads());
		AdminWriter adminWriter =new AdminWriter();
		Thread writer = new Thread(adminWriter);
		Thread threadMonitor = new Thread(adminWriter);
		try {
			CentralAlarmEvaluator.getInstance().loadRules();
		} catch (Exception e) {
			e.printStackTrace();
		}
		manager.start();
		threadMonitor.start();
		writer.start();
		
		new CentralMultiThreadServer();
		
	}
}
