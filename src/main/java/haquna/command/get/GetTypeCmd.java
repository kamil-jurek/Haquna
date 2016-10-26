package haquna.command.get;

import haquna.AttrVar;
import haquna.Haquna;
import haquna.HaqunaException;
import haquna.TypeVar;
import haquna.command.Command;
import haquna.utils.HaqunaUtils;
import heart.xtt.Type;

public class GetTypeCmd implements Command {		
	
	public static final String pattern = "^[A-Z].*=(\\s*)[A-Z].*[.]getType[(][)](\\s*)";
	
	private String commandStr;
	private String varName;
	private String attribiuteName;
	
	public GetTypeCmd() {
		
	}
	
	public GetTypeCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[=|.|']");		
		this.varName = commandParts[0];		
		this.attribiuteName = commandParts[1];
	}
	
	@Override
	public void execute() {				
		try {
			HaqunaUtils.checkVarName(varName);
			AttrVar attrVar = HaqunaUtils.getAttrVar(attribiuteName);
			Type type = attrVar.attr.getType();
			
			Haquna.typeMap.put(varName, new TypeVar(type, null));
			
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
		return new GetTypeCmd(cmdStr);
	}
}