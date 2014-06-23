package uniandes.centralsimulator.alarms.processor;

import java.util.concurrent.ConcurrentLinkedQueue;

import uniandes.centralsimulator.alarms.entities.AlarmReceive;

public class AdminThreads {

	private ConcurrentLinkedQueue<ThreadAlarmResolver> threads;
	private static AdminThreads singleton;
	private final static int MAX_THREADS=1000;
	private static int MIN_TO_CREATE_FOLLOWERS = 15;
	private static int NUMBER_FOLLOWERS_TO_CREATE = 20;
	private static int TOTAL_THREADS = 0;

	public static AdminThreads getInstance(){
		if(singleton == null)
			singleton = new AdminThreads();
		return singleton;
	}

	private AdminThreads(){
		threads = new ConcurrentLinkedQueue<ThreadAlarmResolver>();
		this.createFollowers();

	}

	private void createFollowers() {
		ThreadAlarmResolver alarmResolver;
		int totalThreads= TOTAL_THREADS;
		
		if(totalThreads< MAX_THREADS && totalThreads+NUMBER_FOLLOWERS_TO_CREATE <= MAX_THREADS){
			for(int i=0; i<NUMBER_FOLLOWERS_TO_CREATE; i++){
				totalThreads++;
				alarmResolver = new ThreadAlarmResolver(totalThreads);
				threads.add(alarmResolver);
			}
			TOTAL_THREADS+=NUMBER_FOLLOWERS_TO_CREATE;
		}

	}

	public void putFollower(ThreadAlarmResolver alarmResolver) {
		threads.add(alarmResolver);
	}

	public void runLeader(AlarmReceive alarm) {
		ThreadAlarmResolver alarmResolver;
		Thread resolver; 
		alarmResolver =threads.poll();

		this.validateNumberThreads();

		if(alarmResolver !=null)
		{
			alarmResolver.setAlarm(alarm);
			resolver = new Thread(alarmResolver); 
			resolver.start();
		}


	}

	private void validateNumberThreads(){
		//Evalua si debe crear mas threads
		if(threads.size()<MIN_TO_CREATE_FOLLOWERS){
			NUMBER_FOLLOWERS_TO_CREATE*=2;
			MIN_TO_CREATE_FOLLOWERS = NUMBER_FOLLOWERS_TO_CREATE - 10;
			createFollowers();
		}
	}

	public boolean hasLeader(){
		return !threads.isEmpty();
	}

}
