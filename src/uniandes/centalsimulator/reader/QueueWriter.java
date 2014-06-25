package uniandes.centalsimulator.reader;

import java.util.concurrent.ConcurrentLinkedQueue;

public class  QueueWriter  {
	private ConcurrentLinkedQueue<String> lines;
	private ConcurrentLinkedQueue<String> milliseconds;
	
	private static QueueWriter queueAlarms;
	
	public static QueueWriter getInstance(){
		if(queueAlarms ==null){
			queueAlarms = new QueueWriter();
		}
		return queueAlarms;
	}
	
	private QueueWriter(){
		lines = new ConcurrentLinkedQueue<String>();
		milliseconds = new ConcurrentLinkedQueue<String>();
	}

	
	public void putLine(String line, String millisecs) {
		lines.add(line);
		milliseconds.add(millisecs);
	}

	public String getFirstLine() {
		return lines.poll();
	}
	
	public String getFirstMilliseconds() {
		return milliseconds.poll();
	}
}

	