package haquna.command.wm;

import haquna.Haquna;
import haquna.HaqunaException;
import haquna.command.Command;
import haquna.utils.HaqunaUtils;
import heart.WorkingMemory;
import heart.alsvfd.Null;
import heart.alsvfd.SimpleNumeric;
import heart.alsvfd.SimpleSymbolic;
import heart.alsvfd.Value;
import heart.exceptions.AttributeNotRegisteredException;
import heart.exceptions.NotInTheDomainException;

public class SetValueOfCmd implements Command {
	
	public static final String pattern = "^" + Haquna.varName + "[.]setValueOf[(][']" + 
											   Haquna.attrNamePattern + "['](\\s*)[,](\\s*)[']" +
											   Haquna.attrValuePattern+ "(.*)['][)](\\s*)";
	
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
		try {
			WorkingMemory wm = HaqunaUtils.getWorkingMemory(wmName);
			setValue(wm);
			
			Haquna.wasSucces = true;
			
		} catch (HaqunaException | AttributeNotRegisteredException | NotInTheDomainException e) {
			HaqunaUtils.printRed(e.getMessage());
			
			return;
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
	
	private void setValue(WorkingMemory wm) throws AttributeNotRegisteredException, NotInTheDomainException {
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
						
		wm.setAttributeValue(attributeName, attVal);
		wm.recordLog();								
	}
}
