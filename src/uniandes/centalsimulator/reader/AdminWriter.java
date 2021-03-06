package uniandes.centalsimulator.reader;

import uniandes.centralsimulator.alarms.receiver.IStoppable;

public class AdminWriter implements Runnable, IStoppable{

	private boolean stop=true;
	@Override
	public void run() {
		String line, milliseconds;
		Writer writer= new Writer();
		while(stop){
			line = QueueWriter.getInstance().getFirstLine();
			milliseconds = QueueWriter.getInstance().getFirstMilliseconds();
			
			if(line!=null)
				writer.Write(line,"./data/log.txt");
			if(milliseconds!=null)
				writer.Write(milliseconds,"../logMilliseconds.txt");
		}

	}

	@Override
	public void shutdown() {
		stop=false;
	}

}
