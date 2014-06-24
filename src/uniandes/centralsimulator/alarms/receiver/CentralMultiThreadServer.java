package uniandes.centralsimulator.alarms.receiver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import uniandes.centralsimulator.alarms.entities.AlarmReceive;
import uniandes.centralsimulator.alarms.entities.Status;
import uniandes.centralsimulator.alarms.entities.SystemActive;
import uniandes.centralsimulator.alarms.entities.TypeNotification;
import uniandes.centralsimulator.alarms.entities.TypeSensor;

public class CentralMultiThreadServer implements IStoppable
{
	private static final String CONFIG_FILE_PATH = "./data/config.properties";
	private static final String KEY_LISTENING_PORT = "listeningPort";
	
	private ServerSocket server = null;
	private ExecutorService threadPool;
	
	private int listeningPort;
	
	public CentralMultiThreadServer()
	{
		byte[] buffer = new byte[512];
		try
		{
			initProperties();
			
			threadPool = Executors.newCachedThreadPool();
			threadPool.submit(new ShutDownMonitor(this));
			server = new ServerSocket(listeningPort);
			
			System.out.println("Central server listening on port " + listeningPort);
			
			while (true)
			{
				//llega el mensaje de la casa 
				Socket socketObject = server.accept();
				InputStream reader = socketObject.getInputStream();
				reader.read(buffer);
				
				//se encola el mensaje
				QueueAlarms.getInstance().putEvent(createAlarm(buffer));
			
			}
		}
		catch (SocketException e)
		{
			System.out.println("Server is down");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private AlarmReceive createAlarm(byte[] buffer) {
		
		AlarmReceive alarm;
		alarm= new AlarmReceive();
		String[] dataBuffer; 
		
		dataBuffer = new String(buffer).trim().split(";");
		
		alarm.setIdProperty(dataBuffer[0]);
		alarm.setIdSensor(dataBuffer[1]);
		alarm.setStatus(Status.values()[buffer[2]]);
		alarm.setTypeSensor(TypeSensor.values()[buffer[3]]);
		alarm.setSystemActive(SystemActive.values()[buffer[4]]);
		alarm.setTypeNotification(TypeNotification.values()[buffer[5]]);
		
		return alarm;
	}

	private void initProperties() 
	{
		Properties properties = loadProperties();
		String strListeningPort = properties.getProperty(KEY_LISTENING_PORT);
		listeningPort =  Integer.parseInt(strListeningPort);
	}

	private Properties loadProperties() 
	{
		Properties properties = null;
		try
		{
			FileInputStream inputStream = new FileInputStream(CONFIG_FILE_PATH);
			properties = new Properties();
			properties.load(inputStream);
			inputStream.close();			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return properties;
	}

	@Override
	public void shutdown() 
	{
		try
		{
			server.close();
		}
		catch (IOException ex){ }
		threadPool.shutdown();
		System.exit(0);
	}
}
