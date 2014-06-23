package uniandes.centralsimulator.alarms.processor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import uniandes.centralsimulator.alarms.actions.FactoryActions;
import uniandes.centralsimulator.alarms.actions.IAction;
import uniandes.centralsimulator.alarms.entities.AlarmReceive;
import uniandes.centralsimulator.alarms.entities.TypeNotification;

public class ThreadAlarmResolver implements Runnable{

	AlarmReceive alarm;
	int id;

	public ThreadAlarmResolver(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		IAction action;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		Date currentDate = new Date();
		String date= df.format(currentDate);
		System.out.println(date);
		if(this.alarm.getTypeNotification().equals(TypeNotification.Alarm)){
			action = FactoryActions.getInstance().getAcction(this.alarm.getIdProperty()+"_"+this.alarm.getIdSensor());
			action.execute();
		}
		AdminThreads.getInstance().putFollower(this); 
	}


	public void setAlarm(AlarmReceive alarm){
		this.alarm = alarm; 		
	}

}
