package haquna.command.add;

import haquna.Haquna;
import haquna.command.Command;
import heart.exceptions.ModelBuildingException;
import heart.xtt.Type;
import heart.xtt.XTTModel;

public class RemoveTypeCmd implements Command {		
	// NewModel = Model.add(Type)
	public static final String pattern = "^" + Haquna.varName + "(\\s*)=(\\s*)" + Haquna.varName + "[.]remove[(]" + Haquna.varName + "[)](\\s*)";
	
	private String commandStr;
	private String modelName;
	private String newModelName;
	private String typeToRemoveName;

	
	public RemoveTypeCmd() {
		
	}
	
	public RemoveTypeCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[=|.|(|)]");		
		this.newModelName = commandParts[0];		
		this.modelName = commandParts[1];
		this.typeToRemoveName = commandParts[3];
	}
	
	@Override
	public void execute() {				
		if(!Haquna.isVarUsed(newModelName)) {
			if(Haquna.modelMap.containsKey(modelName)) {
				if(Haquna.typeMap.containsKey(typeToRemoveName)) {
					removeType();		            				
				
				} else {
					System.out.println("No " + typeToRemoveName + " variable in memory");
				}
				
			} else {
				System.out.println("No " + modelName + " model in memory");
			}			
		} else {
			System.out.println("Variable name: " + newModelName + " already in use");
		}
	}		
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new RemoveTypeCmd(cmdStr);
	}

	private void removeType() {
		XTTModel model = Haquna.modelMap.get(modelName);	
		Type type = Haquna.typeMap.get(typeToRemoveName);
		
        XTTModel.Builder builder = model.getBuilder();       
        XTTModel newModel = null;
        try {
			builder.removeIncompleteTypeNamed(type.getName());
			newModel = builder.build();
			
			Haquna.modelMap.put(newModelName, newModel);
		
        } catch (ModelBuildingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
	}
	
	public String getCommandStr() {
		return commandStr;
	}

	public void setCommandStr(String commandStr) {
		this.commandStr = commandStr;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	
}