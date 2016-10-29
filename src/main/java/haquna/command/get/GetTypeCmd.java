package haquna.command.get;

import haquna.Haquna;
import haquna.HaqunaException;
import haquna.command.Command;
import haquna.utils.HaqunaUtils;
import heart.xtt.Attribute;
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
			Attribute attr = HaqunaUtils.getAttribute(attribiuteName);
			Type type = attr.getType();
			
			Haquna.typeMap.put(varName, type);
			
			Haquna.wasSucces = true;
			
		} catch (HaqunaException e) {
			HaqunaUtils.printRed(e.getMessage());
			
			return;
		
		} catch (Exception e) {
			HaqunaUtils.printRed(e.getMessage());
			e.printStackTrace();
			
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