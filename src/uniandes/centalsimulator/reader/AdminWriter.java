package uniandes.centalsimulator.reader;

import uniandes.centralsimulator.alarms.receiver.IStoppable;

public class AdminWriter implements Runnable, IStoppable{

	private boolean stop=true;
	@Override
	public void run() {
		String line;
		Writer writer= new Writer();
		while(stop){
			line = QueueWriter.getInstance().getFirstEvent();
			if(line!=null)
				writer.Write(line);
		}

	}

	@Override
	public void shutdown() {
		stop=false;
	}

}
