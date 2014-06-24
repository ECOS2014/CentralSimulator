package uniandes.centralsimulator.alarms.processor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import uniandes.centralsimulator.alarms.actions.FactoryActions;
import uniandes.centralsimulator.alarms.actions.IAction;
import uniandes.centralsimulator.alarms.cache.CentralAlarmEvaluator;
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
		
		if(this.alarm.getTypeNotification().equals(TypeNotification.Alarm)){
			action = FactoryActions.getInstance().getAcction(CentralAlarmEvaluator.getInstance().getActionToDo(this.alarm.getIdProperty()+"_"+this.alarm.getIdSensor()));
			action.execute();
		}
		System.out.println("atendiendo hilo: "+this.id+ " INICIO CASA: "+this.alarm.getStartDateHome()+ " FIN CASA: "+this.alarm.getEndDateHome() +" INICIO SERVIDOR: "+this.alarm.getStartDateServer() + " FIN SERVER: "+date+" sensor: "+this.alarm.getIdSensor() + " tipo de notificacion: "+this.alarm.getTypeNotification());
		AdminThreads.getInstance().putFollower(this); 
	}


	public void setAlarm(AlarmReceive alarm){
		this.alarm = alarm; 		
	}

}
