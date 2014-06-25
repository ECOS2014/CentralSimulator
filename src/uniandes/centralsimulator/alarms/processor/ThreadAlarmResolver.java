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
	long totalMilliseconds;
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
		Date currentDate;
		String actionName="";
		String log;
		currentDate  = new Date();
		if(this.alarm.getTypeNotification().equals(TypeNotification.Alarm)){
			actionName = CentralAlarmEvaluator.getInstance().getActionToDo(this.alarm.getIdProperty()+"_"+this.alarm.getIdSensor());
			action = FactoryActions.getInstance().getAcction(actionName);
			action.execute();
		}
		totalMilliseconds = (currentDate.getTime() -this.alarm.getStartMillisecondsServer() )+ this.alarm.getMillisecondsHome();  
				
		
		log = "Casa: "+this.alarm.getIdProperty()+" Hilo: "+this.count +" Total milisengundos: "+ totalMilliseconds + " sensor: "+this.alarm.getIdSensor() + " tipo de notificacion: "+this.alarm.getTypeNotification()+" "+actionName;
		QueueWriter.getInstance().putEvent(log);
		
		AdminThreads.getInstance().putFollower(this); 
	}


	public void setAlarm(AlarmReceive alarm){
		this.alarm = alarm; 		
	}

}
