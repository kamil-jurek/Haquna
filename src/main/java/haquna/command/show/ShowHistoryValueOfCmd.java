package haquna.command.show;

import java.util.LinkedList;

import haquna.Haquna;
import haquna.command.Command;
import heart.RelativeTimePeriod;
import heart.RelativeTimestamp;
import heart.RelativeTimestamp.TimeType;
import heart.State;
import heart.StateElement;
import heart.WorkingMemory;
import heart.alsvfd.Value;

public class ShowHistoryValueOfCmd implements Command {
	
	public static final String pattern = "^" + Haquna.varName + "(\\s*)[.](\\s*)showValueOf[(]['](.+)['][,]['](.+)['][)](\\s*)";
	
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
			
			
			for(State s : wm.getHistoryLog()) {
				System.out.println("====" + s.getName() + "======");
				for(StateElement se : s.getStateElements()){
			    	System.out.println("Attribute " + se.getAttributeName()+" = " + se.getValue());
			    }
				System.out.println("=================================\n" + new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date (wm.getCurrentTimestamp()*1000)));
			}
			
			Long time;
			Long timeSec;
			try {
				timeSec = Long.parseLong(relativeTime);
				time = timeSec * 1000000; 
				
			} catch(Exception e) {
				e.printStackTrace();
				return;
			}
			
			LinkedList<Value> states = wm.findHistoricalValues(wm.getHistoryLog(), 
					  new RelativeTimePeriod(wm.getCurrentTimestamp()-time, 
											wm.getCurrentTimestamp(), 1000, TimeType.MILISCOUNT), attributeName);
			

			for(Value v : states) {
				String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date (v.getTimestamp()));
				System.out.println(v + ", " + date);
			}
			System.out.println();
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
