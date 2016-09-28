package haquna;

import haquna.Haquna;
import haquna.command.Command;
import heart.WorkingMemory;
import heart.xtt.XTTModel;

public class NewWorkingMemoryCmd implements Command {		
	
	public static final String pattern = "^[A-Z].*=(\\s*)new(\\s*)WorkingMemory[(][A-Z](.*)[)](\\s*)";
	
	private String commandStr;
	private String varName;
	private String modelName;
	
	public NewWorkingMemoryCmd() {
		
	}
	
	public NewWorkingMemoryCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[=|.|'|\\(|\\)]");		
		this.varName = commandParts[0];		
		this.modelName = commandParts[2];
	}
	
	@Override
	public void execute() {				
		if(!Haquna.isVarUsed(varName)) {
			if(Haquna.modelMap.containsKey(modelName)) {
				XTTModel model = Haquna.modelMap.get(modelName);
				WorkingMemory wm = new WorkingMemory();
				
				wm.registerAllAttributes(model);
				
				Haquna.wmMap.put(varName, wm);
			
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
		return new NewWorkingMemoryCmd(cmdStr);
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

}