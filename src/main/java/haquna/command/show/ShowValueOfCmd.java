package haquna.command.show;

import haquna.Haquna;
import haquna.command.Command;
import heart.WorkingMemory;
import heart.alsvfd.Value;

public class ShowValueOfCmd implements Command {
	
	public static final String pattern = "^[A-Z](.*)[.]showValueOf[(]['](.*)['][)](\\s*)";
	
	private String commandStr;
	private String wmName;
	private String attributeName;
	
	public ShowValueOfCmd() {
		
	}
	
	public ShowValueOfCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[.|']");	
		this.wmName = commandParts[0];
		this.attributeName = commandParts[2];
	}
	
	public void execute() {
		if(Haquna.wmMap.containsKey(wmName)) {
			WorkingMemory wm = Haquna.wmMap.get(wmName);
			Value attVal = wm.getAttributeValue(attributeName);
			System.out.println("=================================");
			System.out.println(attributeName + " = " + attVal);
			System.out.println("=================================");
			
		} else {
			System.out.println("No " + wmName + " WorkingMemory in memory");
		}		
	}
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}

	public Command getNewCommand(String cmdStr) {
		return new ShowValueOfCmd(cmdStr);
	}	
	
	public String getCommandStr() {
		return commandStr;
	}

	public void setCommandStr(String commandStr) {
		this.commandStr = commandStr;
	}

	public String getVarName() {
		return wmName;
	}

	public void setVarName(String varName) {
		this.wmName = varName;
	}
	
	
}
