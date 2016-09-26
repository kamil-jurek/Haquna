package haquna.command.get;

import java.util.LinkedList;

import haquna.Haquna;
import haquna.command.Command;
import heart.xtt.Attribute;
import heart.xtt.XTTModel;

public class GetAttributeByNameCmd implements Command {		
	
	public static final String pattern = "^[A-Z].*=(\\s*)[A-Z].*[.]getAttributeByName[(]['](.*)['][)](\\s*)";
	
	private String commandStr;
	private String varName;
	private String modelName;
	private String attributeName;
	
	public GetAttributeByNameCmd() {
		
	}
	
	public GetAttributeByNameCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[=|.|']");		
		this.varName = commandParts[0];		
		this.modelName = commandParts[1];
		this.attributeName = commandParts[3];
	}
	
	@Override
	public void execute() {				
		if(!Haquna.isVarUsed(varName)) {
			if(Haquna.modelMap.containsKey(modelName)) {
				XTTModel model = Haquna.modelMap.get(modelName);				
				LinkedList<Attribute> attributes = model.getAttributes();
				
				for(Attribute att : attributes){					
					if(att.getName().equals(attributeName)){
						Haquna.attribiuteMap.put(varName, att);
						return;
					}	     
				}
				System.out.println("No attribute with '" + attributeName + "' name in '" + modelName + "' model");
							
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
		return new GetAttributeByNameCmd(cmdStr);
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

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getAttribiuteName() {
		return attributeName;
	}

	public void setAttribiuteName(String attribiuteName) {
		this.attributeName = attribiuteName;
	}
	
	
}