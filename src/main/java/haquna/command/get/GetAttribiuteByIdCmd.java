package haquna.command.get;

import java.util.LinkedList;

import haquna.Haquna;
import haquna.command.Command;
import heart.xtt.Attribute;
import heart.xtt.XTTModel;

public class GetAttribiuteByIdCmd implements Command {		
	
	public static final String pattern = "^[A-Z].*=(\\s*)[A-Z].*[.]getAttribiuteById[(]['](.*)['][)](\\s*)";
	
	private String commandStr;
	private String varName;
	private String modelName;
	private String attribiuteId;
	
	public GetAttribiuteByIdCmd() {
		
	}
	
	public GetAttribiuteByIdCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[=|.|']");		
		this.varName = commandParts[0];		
		this.modelName = commandParts[1];
		this.attribiuteId = commandParts[3];
	}
	
	@Override
	public void execute() {				
		if(!Haquna.isVarUsed(varName)) {
			if(Haquna.modelMap.containsKey(modelName)) {
				XTTModel model = Haquna.modelMap.get(modelName);				
				LinkedList<Attribute> attribiutes = model.getAttributes();
				
				for(Attribute att : attribiutes) {
					if(att.getId().equals(attribiuteId)){
						Haquna.attribiuteMap.put(varName, att);
						return;
					}	     
				}
				System.out.println("No attribiute with '" + attribiuteId + "' id in '" + modelName + "' model");
			
			} else {
				System.out.println("No " + modelName + " model in memory");
			}			
		} else {
			System.out.println("Variable name: " + varName + " already in use");
		}
	}		
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new GetAttribiuteByIdCmd(cmdStr);
	}
}