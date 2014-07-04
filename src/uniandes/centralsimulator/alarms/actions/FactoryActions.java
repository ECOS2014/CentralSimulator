package uniandes.centralsimulator.alarms.actions;

public class FactoryActions {

	private static FactoryActions singleton;
	
	public static FactoryActions getInstance(){
		if(singleton ==null)
			singleton = new FactoryActions();
		return singleton;
	}
	
	private FactoryActions() {
	}
	
	public IAction getAcction(String typeAction){
		IAction action =null;
		if (typeAction==null)
		{
			typeAction = "Mail";
		}
		if(typeAction.equals("Mail")){
			action = new Mailer();
		}else if(typeAction.equals("Message")){
			action = new TextMessageSender();
		} else if(typeAction.equals("Call")){
			action = new Caller();
		}
		
		return action;
	}
}
