package haquna.command.get;

import java.util.LinkedList;

import haquna.Haquna;
import haquna.HaqunaException;
import haquna.TypeVar;
import haquna.command.Command;
import haquna.utils.HaqunaUtils;
import heart.xtt.Type;
import heart.xtt.XTTModel;

public class GetTypeByIdCmd implements Command {		
	
	public static final String pattern = "^" + Haquna.varName +"(\\s*)=(\\s*)" + Haquna.varName + "[.]getTypeById[(]['](.*)['][)](\\s*)";
	
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
		try {
			HaqunaUtils.checkVarName(varName);
			XTTModel model = HaqunaUtils.getModel(modelName);
			getTypeById(model);
			
			Haquna.wasSucces = true;
		
		} catch (HaqunaException e) {
			HaqunaUtils.printRed(e.getMessage());
			
			return;
		}
	}		
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new GetTypeByIdCmd(cmdStr);
	}
	
	private void getTypeById(XTTModel model) throws HaqunaException {
		String typeName = null;
		Type type = null;
				
		for(Type t : model.getTypes()){
			if(t.getId().equals(typeId)){
				typeName = t.getName();
				type = t;
				break;
			}	     
		}
		
		Type.Builder typeBuilder = model.getBuilder().getIncompleteTypeNamed(typeName);
		
		if(type != null && typeBuilder != null) {
			Haquna.typeMap.put(varName, new TypeVar(type, typeBuilder));
		
		} else {
			throw new HaqunaException("No type with '" + typeId + "' id in '" + modelName + "' model");
		}
	}
}