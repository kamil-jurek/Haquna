package haquna;

import java.util.LinkedList;

import heart.xtt.Table;
import heart.xtt.XTTModel;

public class ShowTablesListCmd implements Command {
	
	public static final String pattern = "^[A-Z](.*)[.]showTablesList[(][)](\\s*)";
	
	private String commandStr;
	private String varName;
	
	public ShowTablesListCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[.]");	
		this.varName = commandParts[0];
	}
	
	public void execute() {
		try {				
			if(Haquna.modelMap.containsKey(varName)) {
				XTTModel model = Haquna.modelMap.get(varName);
			
				//Printing all the tables and rules within the model
				LinkedList<Table> tables = model.getTables();
				for(Table ta : tables){
					System.out.println("Table id:"+ta.getId());
					System.out.println("Table name:"+ta.getName());
		     
				}	
			} else {
				System.out.println("No such model in memory");
			}
		} catch(Exception e) {
			System.out.println("No such model in memory");
			e.printStackTrace();			    	
			    	
		}
	}
	
	public static boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
}
