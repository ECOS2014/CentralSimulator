package uniandes.centralsimulator.alarms.processor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		currentDate  = new Date();
		if(this.alarm.getTypeNotification().equals(TypeNotification.Alarm)){
			actionName = CentralAlarmEvaluator.getInstance().getActionToDo(this.alarm.getIdProperty()+"_"+this.alarm.getIdSensor());
			action = FactoryActions.getInstance().getAcction(actionName);
			action.execute();
		}
		
		long centralMilisegs = (currentDate.getTime() -this.alarm.getStartMillisecondsServer()); 
		totalMilliseconds = centralMilisegs + this.alarm.getMillisecondsHome();  
				
		
		log = "Casa: "+this.alarm.getIdProperty()+" Hilo: "+this.count +" Total milisegundos: "+ totalMilliseconds + " sensor: "+this.alarm.getIdSensor() + " tipo de notificacion: "+this.alarm.getTypeNotification()+" "+actionName+
		"CASA INICIO: "+this.alarm.getStartDateHome() +"FIN: "+this.alarm.getEndDateHome()+" SERVIDOR INICIO: "+this.alarm.getStartDateServer() +" FIN: "+df.format(currentDate);
		
		//System.out.println(log);
				
		QueueWriter.getInstance().putLine(log,(this.alarm.getMillisecondsHome())+"-"+centralMilisegs+"-"+totalMilliseconds);
		
		AdminThreads.getInstance().putFollower(this); 
	}


	public void setAlarm(AlarmReceive alarm){
		this.alarm = alarm; 		
	}

}
