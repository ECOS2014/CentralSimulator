package uniandes.centralsimulator.alarms.receiver;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import uniandes.centralsimulator.alarms.entities.AlarmReceive;
import uniandes.centralsimulator.alarms.entities.Status;
import uniandes.centralsimulator.alarms.entities.SystemActive;
import uniandes.centralsimulator.alarms.entities.TypeNotification;
import uniandes.centralsimulator.alarms.entities.TypeSensor;
import uniandes.security.MessageChecker;
import uniandes.security.MessageCipher;

public class CentralPropertySensorListenerThread implements Runnable
{
	private static final int DEFAULT_PROPERTY_SENSOR_BUFFER_SIZE = 1024;
	private Socket propertySensorSocket;
	private InputStream propertySensorInputStream;
	private byte[] propertySensorBuffer;

	public CentralPropertySensorListenerThread(Socket propertySensorSocket)
	{
		this.propertySensorSocket = propertySensorSocket;
		this.propertySensorBuffer = new byte[DEFAULT_PROPERTY_SENSOR_BUFFER_SIZE];
		try 
		{
			this.propertySensorInputStream =  this.propertySensorSocket.getInputStream();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run() 
	{
		AlarmReceive alarm;
		while(true)
		{
			try
			{
				this.propertySensorBuffer = new byte[DEFAULT_PROPERTY_SENSOR_BUFFER_SIZE];
				propertySensorInputStream.read(propertySensorBuffer);
				alarm =createAlarm(propertySensorBuffer);
				if(alarm !=null)
					QueueAlarms.getInstance().putEvent(alarm);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private AlarmReceive createAlarm(byte[] buffer) 
	{		
		AlarmReceive alarm;
		alarm= new AlarmReceive();
		String[] dataBuffer; 
		String bufferString;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		Date currentDate = new Date();

		//casa;sensor;status;typesensor;systemActive;typeNotification;milisengundo invertidos en la casa
		bufferString = (new String(buffer).trim()).split(";")[0];

		MessageCipher ms = new MessageCipher();
		bufferString = ms.decrypt(bufferString);
		dataBuffer =bufferString.split(";");

		if (bufferString != null && isValidHash(dataBuffer, bufferString))
		{
			//System.out.println("The messages <" + bufferString + "> would be processed it has a valid hash" );
			
			alarm.setStartMillisecondsServer(currentDate.getTime());
			alarm.setIdProperty(dataBuffer[0]);
			alarm.setIdSensor(dataBuffer[1]);
			alarm.setStatus(Status.values()[Integer.parseInt(dataBuffer[2])]);
			alarm.setTypeSensor(TypeSensor.values()[Integer.parseInt(dataBuffer[3])]);
			alarm.setSystemActive(SystemActive.values()[Integer.parseInt(dataBuffer[4])]);
			alarm.setTypeNotification(TypeNotification.values()[Integer.parseInt(dataBuffer[5])]);
			alarm.setMillisecondsHome(Long.parseLong(dataBuffer[6]));
			alarm.setStartDateHome(dataBuffer[7]);
			alarm.setEndDateHome(dataBuffer[8]);
			alarm.setStartDateServer(df.format(currentDate));
		}
		else
		{
			//System.out.println("Can't process <" + bufferString + "> the hash is not valid" );
			return null;
		}

		return alarm;
	}

	private boolean isValidHash(String[] messageArr, String messageHash) 
	{
		String message = messageArr[0] + ";";
		message += (messageArr[1] + ";");
		message += (messageArr[2] + ";");
		message += (messageArr[3] + ";");
		message += (messageArr[4] + ";");
		message += (messageArr[5] + ";");
		message += (messageArr[6] + ";");
		message += (messageArr[7] + ";");
		message += messageArr[8];

		MessageChecker mc = new MessageChecker();
		//TODO: Cambiar cuando mantenga conexiones
		return mc.checkHash(messageArr[9], message, null);
	}
}
