package haquna.command.get;

import java.util.LinkedList;

import haquna.Haquna;
import haquna.command.Command;
import heart.xtt.Attribute;
import heart.xtt.XTTModel;

public class GetAttribiuteByNameCmd implements Command {		
	
	public static final String pattern = "^[A-Z].*=(\\s*)[A-Z].*[.]getAttribiuteByName[(]['](.*)['][)](\\s*)";
	
	private String commandStr;
	private String varName;
	private String modelName;
	private String attribiuteName;
	
	public GetAttribiuteByNameCmd() {
		
	}
	
	public GetAttribiuteByNameCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[=|.|']");		
		this.varName = commandParts[0];		
		this.modelName = commandParts[1];
		this.attribiuteName = commandParts[3];
	}
	
	@Override
	public void execute() {				
		if(!Haquna.isVarUsed(varName)) {
			if(Haquna.modelMap.containsKey(modelName)) {
				XTTModel model = Haquna.modelMap.get(modelName);				
				LinkedList<Attribute> attribiutes = model.getAttributes();
				
				for(Attribute att : attribiutes){					
					if(att.getName().equals(attribiuteName)){
						Haquna.attribiuteMap.put(varName, att);
						return;
					}	     
				}
				System.out.println("No attribiute with '" + attribiuteName + "' name in '" + modelName + "' model");
							
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
		return new GetAttribiuteByNameCmd(cmdStr);
	}
}