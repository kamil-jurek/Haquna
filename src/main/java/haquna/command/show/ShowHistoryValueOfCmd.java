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
			
			
			/*for(State s : wm.getHistoryLog()) {
				System.out.println("====" + s.getName() + "======");
				for(StateElement se : s.getStateElements()){
			    	System.out.println("Attribute " + se.getAttributeName()+" = " + se.getValue());
			    }
				System.out.println("=================================\n" + new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date (wm.getCurrentTimestamp())));
			}*/
			
			Long time;
			Long timeSec;
			try {
				timeSec = Long.parseLong(relativeTime);
				time = timeSec * 1000; 
				///10000000000000
				
			} catch(Exception e) {
				e.printStackTrace();
				return;
			}
			
			State s = wm.findHistoricalState(wm.getHistoryLog(), new RelativeTimestamp(-time, TimeType.MILISCOUNT));
			String ds = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date (s.getTimestamp()));
			System.out.println(attributeName + " at "+ ds +" was: " + s.getValueOfAttribute(attributeName));
			
			System.out.println(time);
			RelativeTimePeriod rtp1 = new RelativeTimePeriod(-time, wm.getCurrentTimestamp(), 1000, TimeType.MILISCOUNT);
			LinkedList<Value> values1 = wm.findHistoricalValues(wm.getHistoryLog(), rtp1, attributeName);
						
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
