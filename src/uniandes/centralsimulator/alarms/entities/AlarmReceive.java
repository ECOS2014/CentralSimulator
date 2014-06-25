package uniandes.centralsimulator.alarms.entities;

public class AlarmReceive {

	private String idSensor;
	private String idProperty;
	
	private TypeSensor typeSensor; 
	private Status status;
	private SystemActive systemActive;
	private TypeNotification typeNotification;
	
	private long millisecondsHome;
	private long startMillisecondsServer;
	
	private String startDateHome;
	private String endDateHome;
	private String startDateServer;
	
	
	
	
	public String getStartDateHome() {
		return startDateHome;
	}
	public void setStartDateHome(String startDateHome) {
		this.startDateHome = startDateHome;
	}
	public String getEndDateHome() {
		return endDateHome;
	}
	public void setEndDateHome(String endDateHome) {
		this.endDateHome = endDateHome;
	}
	public String getStartDateServer() {
		return startDateServer;
	}
	public void setStartDateServer(String startDateServer) {
		this.startDateServer = startDateServer;
	}
	
	
	
	public String getIdSensor() {
		return idSensor;
	}
	public void setIdSensor(String idSensor) {
		this.idSensor = idSensor;
	}
	public String getIdProperty() {
		return idProperty;
	}
	public void setIdProperty(String idProperty) {
		this.idProperty = idProperty;
	}
	public TypeSensor getTypeSensor() {
		return typeSensor;
	}
	public void setTypeSensor(TypeSensor typeSensor) {
		this.typeSensor = typeSensor;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public SystemActive getSystemActive() {
		return systemActive;
	}
	public void setSystemActive(SystemActive systemActive) {
		this.systemActive = systemActive;
	}
	public TypeNotification getTypeNotification() {
		return typeNotification;
	}
	public void setTypeNotification(TypeNotification typeNotification) {
		this.typeNotification = typeNotification;
	}
	public long getMillisecondsHome() {
		return millisecondsHome;
	}
	public void setMillisecondsHome(long millisecondsHome) {
		this.millisecondsHome = millisecondsHome;
	}
	public long getStartMillisecondsServer() {
		return startMillisecondsServer;
	}
	public void setStartMillisecondsServer(long startMillisecondsServer) {
		this.startMillisecondsServer = startMillisecondsServer;
	}
	
	

}
