package haquna.command.show;

import java.util.LinkedList;

import haquna.Haquna;
import haquna.command.Command;
import heart.xtt.Attribute;
import heart.xtt.XTTModel;

public class ShowAttributesListCmd implements Command {
	
	public static final String pattern = "^[A-Z](.*)[.]showAttributesList[(][)](\\s*)";
	
	private String commandStr;
	private String varName;
	
	public ShowAttributesListCmd() {
		
	}
	
	public ShowAttributesListCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[.]");	
		this.varName = commandParts[0];
	}
	
	public void execute() {
						
		if(Haquna.modelMap.containsKey(varName)) {
			XTTModel model = Haquna.modelMap.get(varName);
			
			LinkedList<Attribute> atts = model.getAttributes();
			for(Attribute att: atts){
			    System.out.println("=============================");
			    System.out.println("Att Id:       " + att.getId());
			    System.out.println("Att name:     " + att.getName());
			    System.out.println("Att typeName: " + att.getTypeId());
			    System.out.println("Att abbrev:   " + att.getAbbreviation());
			    System.out.println("Att comm:     " + att.getComm());
			    System.out.println("Att desc:     " + att.getDescription());
			    System.out.println("Att class:    " + att.getXTTClass());
			    System.out.println("=============================");
			    System.out.println();
			}			
		} else {
			System.out.println("No " + varName + " model in memory");
		}
	}
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new ShowAttributesListCmd(cmdStr);
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
