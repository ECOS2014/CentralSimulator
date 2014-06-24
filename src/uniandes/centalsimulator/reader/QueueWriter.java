package uniandes.centalsimulator.reader;

import java.util.concurrent.ConcurrentLinkedQueue;

public class  QueueWriter  {
	private ConcurrentLinkedQueue<String> lines;
	
	private static QueueWriter queueAlarms;
	
	public static QueueWriter getInstance(){
		if(queueAlarms ==null){
			queueAlarms = new QueueWriter();
		}
		return queueAlarms;
	}
	
	private QueueWriter(){
		lines = new ConcurrentLinkedQueue<String>();
	}
	public boolean hasElements() {
		return !lines.isEmpty();
	}
	
	public void putEvent(String line) {
		lines.add(line);
	}

	public String getFirstEvent() {
		return lines.poll();
	}

}

	