package haquna.command.show;

import haquna.Haquna;
import haquna.command.Command;
import heart.RelativeTimestamp;
import heart.RelativeTimestamp.TimeType;
import heart.WorkingMemory;
import heart.alsvfd.Value;

public class ShowHistoryValueOfCmd implements Command {
	
	public static final String pattern = "^[A-Z](.*)[.]showValueOf[(]['](.*)['][,]['](.*)['][)](\\s*)";
	
	private String commandStr;
	private String wmName;
	private String attributeName;
	private String relativeTime;
	
	public ShowHistoryValueOfCmd() {
		
	}
	
	public ShowHistoryValueOfCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[.|']");	
		this.wmName = commandParts[0];
		this.attributeName = commandParts[2];
		this.relativeTime = commandParts[4];
	}
	
	public void execute() {
		if(Haquna.wmMap.containsKey(wmName)) {
			WorkingMemory wm = Haquna.wmMap.get(wmName);
			
			RelativeTimestamp rt = new RelativeTimestamp(Long.parseLong(relativeTime), TimeType.MILISCOUNT);
			Value attVal = wm.findHistoricalValue(wm.getHistoryLog(), attributeName, rt);
			System.out.println("=================================");
			System.out.println(attributeName + " (" + rt +") = " + attVal);
			System.out.println("=================================");
			
		} else {
			System.out.println("No " + wmName + " WorkingMemory in memory");
		}		
	}
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}

	public Command getNewCommand(String cmdStr) {
		return new ShowHistoryValueOfCmd(cmdStr);
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
