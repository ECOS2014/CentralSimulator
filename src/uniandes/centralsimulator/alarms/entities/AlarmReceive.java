package uniandes.centralsimulator.alarms.entities;

public class AlarmReceive {

	private String idSensor;
	private String idProperty;
	
	private TypeSensor typeSensor; 
	private Status status;
	private SystemActive systemActive;
	private TypeNotification typeNotification;
	private String startDate;
	
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String date) {
		startDate = date;
	}
	


}
