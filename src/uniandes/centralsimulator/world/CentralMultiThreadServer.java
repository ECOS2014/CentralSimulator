package uniandes.centralsimulator.world;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CentralMultiThreadServer implements IStoppable
{
	private static final String CONFIG_FILE_PATH = "./data/config.properties";
	private static final String KEY_LISTENING_PORT = "listeningPort";
	
	private ServerSocket server = null;
	private ExecutorService threadPool;
	
	private int listeningPort;
	
	public CentralMultiThreadServer()
	{
		try
		{
			initProperties();
			//TODO: inicializar cola
			threadPool = Executors.newCachedThreadPool();
			threadPool.submit(new ShutDownMonitor(this));
			server = new ServerSocket(listeningPort);
			System.out.println("Central server listening on port " + listeningPort);
			System.out.println("Hit enter to stop the server");
			while (true)
			{
				// Aqui llegan los mensajes de las propiedades
				//TODO: encolar
				Socket socketObject = server.accept();
				
				//--------------------------------------------------------
				
				System.out.println("Thread created");
				//TODO: Cambiar, no crear el thread sino obtenerlo de la cola
				//TODO: Aplicar leader/followers
				threadPool.submit(new CentralAlarmSolverThread(socketObject));
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
