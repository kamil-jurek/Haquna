package haquna.command.show;

import haquna.Haquna;
import haquna.command.Command;
import heart.State;
import heart.StateElement;
import heart.WorkingMemory;

public class ShowCurrentStateCmd implements Command {
	
	public static final String pattern = "^[A-Z](.*)[.]showCurrentState[(][)](\\s*)";
	
	private String commandStr;
	private String varName;
	
	public ShowCurrentStateCmd() {
		
	}
	
	public ShowCurrentStateCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[.]");	
		this.varName = commandParts[0];
	}
	
	public void execute() {
		if(Haquna.wmMap.containsKey(varName)) {
			WorkingMemory wm = Haquna.wmMap.get(varName);
			State current = wm.getCurrentState();
			
			System.out.println("=================================");
			for(StateElement se : current){
		    	System.out.println("Attribute " + se.getAttributeName()+"\t = " + se.getValue());
		    }
			System.out.println("=================================");
			
		} else {
			System.out.println("No " + varName + " WorkingMemory in memory");
		}		
	}
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}

	public Command getNewCommand(String cmdStr) {
		return new ShowCurrentStateCmd(cmdStr);
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
