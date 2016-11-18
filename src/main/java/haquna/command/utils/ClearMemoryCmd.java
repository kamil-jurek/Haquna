package haquna.command.utils;

import haquna.HaqunaSingleton;
import haquna.command.Command;
import haquna.utils.HaqunaUtils;

public class ClearMemoryCmd implements Command {		
	
	public static final String pattern = "^(\\s*)clearMemory[(][)](\\s*)";
	
	private String commandStr;
	
	public ClearMemoryCmd() {
		
	}
	
	public ClearMemoryCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
	
	}
	
	@Override
	public boolean execute() {		
		try {
			HaqunaSingleton.modelMap.clear();
			HaqunaSingleton.wmMap.clear();
			HaqunaSingleton.attrMap.clear();
			HaqunaSingleton.tableMap.clear();
			HaqunaSingleton.ruleMap.clear();
			HaqunaSingleton.typeMap.clear();
			HaqunaSingleton.callbackMap.clear();
			HaqunaSingleton.attrBuilderMap.clear();
			HaqunaSingleton.tableBuilderMap.clear();
			HaqunaSingleton.ruleBuilderMap.clear();
			HaqunaSingleton.typeBuilderMap.clear();
			
			return true;
					
		} catch (Exception e) {
			HaqunaUtils.printRed(e.getMessage());
			e.printStackTrace();
			
			return false;
		}
	}		
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new ClearMemoryCmd(cmdStr);
	}

	public String getCommandStr() {
		return commandStr;
	}
}