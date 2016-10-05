package haquna.command.show;

import java.util.LinkedList;

import haquna.Haquna;
import haquna.command.Command;
import heart.xtt.Type;
import heart.xtt.XTTModel;

public class ShowTypesListCmd implements Command {
	
	public static final String pattern = "^" + Haquna.varName + "(\\s*)" + "[.]" + "(\\s*)" + "showTypesList[(][)](\\s*)";
	
	private String commandStr;
	private String varName;
	
	public ShowTypesListCmd() {
		
	}
	
	public ShowTypesListCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[.]");	
		this.varName = commandParts[0];
	}
	
	public void execute() {
		if(Haquna.modelMap.containsKey(varName)) {
			XTTModel model = Haquna.modelMap.get(varName);
			
			LinkedList<Type> types = model.getTypes();
			System.out.print("[");
			for(Type t : types){				
			    System.out.print(t.getName());
			    
			    if(t != types.getLast()) {
			    	System.out.print(", ");
			    }			    
			}
			System.out.println("]");
			
		} else {
			System.out.println("No " + varName + " model in memory");
		}		
	}
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new ShowTypesListCmd(cmdStr);
	}
	
	public String getCommandStr() {
		return commandStr;
	}

	public void setCommandStr(String commandStr) {
		this.commandStr = commandStr;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}
	
	
}
