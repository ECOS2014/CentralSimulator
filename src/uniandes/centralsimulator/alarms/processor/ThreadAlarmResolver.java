package uniandes.centralsimulator.alarms.processor;

import java.util.Date;
import uniandes.centalsimulator.reader.QueueWriter;
import uniandes.centralsimulator.alarms.actions.FactoryActions;
import uniandes.centralsimulator.alarms.actions.IAction;
import uniandes.centralsimulator.alarms.cache.CentralAlarmEvaluator;
import uniandes.centralsimulator.alarms.entities.AlarmReceive;
import uniandes.centralsimulator.alarms.entities.TypeNotification;

public class ThreadAlarmResolver implements Runnable{

	AlarmReceive alarm;
	int id;
	long totalMiliseg;
	long count;
	public ThreadAlarmResolver(int id) {
		this.id = id;
	}
	
	public void SetNumber(long count){
		this.count = count;
	}
	@Override
	public void run() {
		IAction action;
		Date currentDate = new Date();
		
		String log;
		if(this.alarm.getTypeNotification().equals(TypeNotification.Alarm)){
			action = FactoryActions.getInstance().getAcction(CentralAlarmEvaluator.getInstance().getActionToDo(this.alarm.getIdProperty()+"_"+this.alarm.getIdSensor()));
			action.execute();
		}
		totalMiliseg = Long.parseLong(this.alarm.getEndDateHome())- Long.parseLong(this.alarm.getStartDateHome())
				+				currentDate.getTime() - Long.parseLong(this.alarm.getStartDateServer());
		
		log = "Hilo: "+this.count +" Total milisengundos: "+ totalMiliseg + " sensor: "+this.alarm.getIdSensor() + " tipo de notificacion: "+this.alarm.getTypeNotification();
		QueueWriter.getInstance().putEvent(log);
		
		AdminThreads.getInstance().putFollower(this); 
	}


	public void setAlarm(AlarmReceive alarm){
		this.alarm = alarm; 		
	}

}
