package haquna.command.wm;

import java.util.LinkedList;

import haquna.Haquna;
import haquna.HaqunaException;
import haquna.command.Command;
import haquna.utils.HaqunaUtils;
import heart.WorkingMemory;
import heart.alsvfd.Null;
import heart.alsvfd.SetValue;
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
		
		this.commandStr = this.commandStr.replaceFirst("\\.", "@");
		String[] commandParts = this.commandStr.split("['|@]");				
		this.wmName = commandParts[0];
		this.attributeName = commandParts[2];
		this.attributeValue = commandParts[4];	
	}
	
	public boolean execute() {
		try {
			WorkingMemory wm = HaqunaUtils.getWorkingMemory(wmName);
			setValue(wm);
			
			return true;
			
		} catch (HaqunaException | AttributeNotRegisteredException | NotInTheDomainException e) {
			HaqunaUtils.printRed(e.getMessage());
			
			return false;
		
		} catch (Exception e) {
			e.printStackTrace();
			
			return false;
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
	
	private void setValue(WorkingMemory wm) throws AttributeNotRegisteredException, NotInTheDomainException, HaqunaException {
		//String attributeValue2 = attributeValue.replaceAll("[\\]|\\[]", "");
		float cf = (float) getCertanityFactor(attributeValue);
		String[] attrValParts = attributeValue.split("#");
		
		if(attributeValue.matches(".*[\\[].*[\\]].*")) {
			LinkedList<Value> values = new LinkedList<Value>();
			
			for(String s : attributeValue.split("[,|\\[|\\]|\\s|#]")) {
				if(!s.matches("\\s*") && !s.matches("-?\\d+(\\.\\d+)?")) {
					values.add(getParsedAttrValue(s));
				}			
			}
			
			Value v = new SetValue(values);
			v.setCertaintyFactor(cf);
			
			wm.setAttributeValue(attributeName, v);
			wm.recordLog();	
			
		} else {
			Value v = getParsedAttrValue(attrValParts[0]);
			v.setCertaintyFactor(cf);
			
			wm.setAttributeValue(attributeName, v);
			wm.recordLog();		
		
		
		/*if(attrValParts.length == 2) {
			Value v = getParsedAttrValue(attributeValue.split("#")[0]);
			v.setCertaintyFactor(Float.parseFloat(attrValParts[1]));
			
			wm.setAttributeValue(attributeName, v);
			wm.recordLog();	
			
		} else if(attrValParts.length == 1) {
			Value v = getParsedAttrValue(attributeValue.split("#")[0]);
			v.setCertaintyFactor(1);
			
			wm.setAttributeValue(attributeName, v);
			wm.recordLog();	*/
						
		/*} else {
			throw new HaqunaException("Attribute value format inncorrect");*/
		}
		
	}
	private Value getParsedAttrValue(String attrValueStr ) {
		Value attVal;	
		if(attrValueStr == null) {
			attVal = new Null();
					
		} else if(attrValueStr.matches("-?\\d+(\\.\\d+)?")) {
			double d = Double.parseDouble(attrValueStr);
			attVal = new SimpleNumeric(d);
			
		} else if(attrValueStr.contains("/")){
			String[] splited = attrValueStr.split("[/]");
			attVal = new SimpleSymbolic(splited[0], Integer.parseInt(splited[1]));		
						
		} else {
			attVal = new SimpleSymbolic(attrValueStr);				
		
		} 
				
		return attVal;
	}
	
	private double getCertanityFactor(String attrValStr) {
		if(attrValStr.contains("#")) {
			String[] splitted = attrValStr.split("[#|\\]]");
			return Double.parseDouble(splitted[1]);
									
		} else {
			return 1.0;
		}
	}
}
