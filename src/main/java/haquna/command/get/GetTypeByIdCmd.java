package haquna.command.get;

import java.util.LinkedList;

import haquna.Haquna;
import haquna.command.Command;
import heart.xtt.Type;
import heart.xtt.XTTModel;

public class GetTypeByIdCmd implements Command {		
	
	public static final String pattern = "^[A-Z].*=(\\s*)[A-Z].*[.]getTypeById[(]['](.*)['][)](\\s*)";
	
	private String commandStr;
	private String varName;
	private String modelName;
	private String typeId;
	
	public GetTypeByIdCmd() {
		
	}
	
	public GetTypeByIdCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[=|.|']");		
		this.varName = commandParts[0];		
		this.modelName = commandParts[1];
		this.typeId = commandParts[3];
	}
	
	@Override
	public void execute() {				
		if(!Haquna.isVarUsed(varName)) {
			if(Haquna.modelMap.containsKey(modelName)) {
				XTTModel model = Haquna.modelMap.get(modelName);				
				LinkedList<Type> types = model.getTypes();
				
				for(Type type : types){
					if(type.getId().equals(typeId)){
						Haquna.typeMap.put(varName, type);
						return;
					}	     
				}
				System.out.println("No type with '" + typeId + "' id in '" + modelName + "' model");
			
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
		return new GetTypeByIdCmd(cmdStr);
	}
}