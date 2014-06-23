package uniandes.centralsimulator.world;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Hashtable;

public class CentralAlarmEvaluator 
{
	private static final String RULES_FILE_PATH = "data/rules.config";
	
	private static CentralAlarmEvaluator instance;
	
	private Hashtable<String, String> rulesHastable;
	
	private CentralAlarmEvaluator()
	{
		
	}
	
	public void setActionToDo(String key, String newAction)
	{
		rulesHastable.put(key, newAction);
	}
	
	public String getActionToDo(String key)
	{
		return rulesHastable.get(key);
	}
	
	public void loadRules() throws Exception
	{
		File rulesFile = new File(RULES_FILE_PATH);
		
		if (!rulesFile.exists())
		{
			throw new Exception("The file of rules doesn't exist");
		}
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(rulesFile));
			String line = br.readLine();
			String key = null;
			String value = null;
			
			rulesHastable = new Hashtable<String, String>();
			
			while (line != null)
			{
				key = ((line.split("="))[0]).trim();
				value = ((line.split("="))[1]).trim();
				rulesHastable.put(key, value);
				line = br.readLine();
			}
			
			br.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	public static CentralAlarmEvaluator getInstance()
	{
		if (instance == null)
		{
			instance = new CentralAlarmEvaluator();
		}
		
		return instance;
	}
}
