package haquna.command.set;

import haquna.Haquna;
import haquna.command.Command;
import heart.WorkingMemory;
import heart.alsvfd.Null;
import heart.alsvfd.SimpleNumeric;
import heart.alsvfd.SimpleSymbolic;
import heart.alsvfd.Value;
import heart.exceptions.AttributeNotRegisteredException;
import heart.exceptions.NotInTheDomainException;

public class SetValueOfCmd implements Command {
	
	public static final String pattern = "^[A-Z](.*)[.]setValueOf[(]['](.*)['](\\s*)[,](\\s*)['](.*)['][)](\\s*)";
	
	private String commandStr;
	private String wmName;
	private String attributeName;
	private String attributeValue;
	
	public SetValueOfCmd() {
		
	}
	
	public SetValueOfCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("['|.]");				
		this.wmName = commandParts[0];
		this.attributeName = commandParts[2];
		this.attributeValue = commandParts[4];	
	}
	
	public void execute() {
		if(Haquna.wmMap.containsKey(wmName)) {
			WorkingMemory wm = Haquna.wmMap.get(wmName);
			Value attVal;
			
			if(attributeValue == null) {
				attVal = new Null();
			
			} else if(attributeValue.matches("-?\\d+(\\.\\d+)?")) {
				double d = Double.parseDouble(attributeValue);
				attVal = new SimpleNumeric(d);
				
			} else if(attributeValue.contains("/")){
				String[] splited = attributeValue.split("[/]");
				attVal = new SimpleSymbolic(splited[0], Integer.parseInt(splited[1]));
			
			} else {
				attVal = new SimpleSymbolic(attributeValue);				
			
			}
							
			try {
				wm.setAttributeValue(attributeName, attVal);
			
			} catch (AttributeNotRegisteredException | NotInTheDomainException e) {
				e.printStackTrace();
			}
			
			System.out.println("==============SET=================");
			System.out.println(attributeName + " = " + wm.getAttributeValue(attributeName));
			System.out.println("=================================");
			
		} else {
			System.out.println("No " + wmName + " WorkingMemory in memory");
		}		
	}
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}

	public Command getNewCommand(String cmdStr) {
		return new SetValueOfCmd(cmdStr);
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
