package uniandes.centralsimulator.world;

import java.io.InputStream;
import java.net.Socket;

public class CentralAlarmSolverThread implements Runnable 
{
	private Socket socketObject = null;
	private byte buffer[] = new byte[512];
	
	public CentralAlarmSolverThread(Socket socketObject)
	{
		this.socketObject =  socketObject;
	}
	
	@Override
	public void run() 
	{
		try
		{
			try
			{
				InputStream reader = socketObject.getInputStream();
				reader.read(buffer);
				String message = (new String(buffer)).trim();
				
				//TODO: Agregar procesamiento de la alarma
				//llamado asincrono a la notificacion
				
				//llamado asincrono para registrar en server de persistencia
				
				System.out.println("Central server is reading: " + message);
			}
			finally
			{
				socketObject.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
