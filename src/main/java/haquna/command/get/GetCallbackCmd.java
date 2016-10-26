package haquna.command.get;

import haquna.Haquna;
import haquna.HaqunaException;
import haquna.command.Command;
import haquna.utils.HaqunaUtils;
import heart.xtt.Attribute;

public class GetCallbackCmd implements Command {		
	
	public static final String pattern = "^[A-Z].*=(\\s*)[A-Z].*[.]getCallback[(][)](\\s*)";
	
	private String commandStr;
	private String varName;
	private String attribiuteName;
	
	public GetCallbackCmd() {
		
	}
	
	public GetCallbackCmd(String _commandStr) {
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
			
			String callback = attr.getCallback();
			
			Haquna.callbackMap.put(varName, callback);
			
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
		return new GetCallbackCmd(cmdStr);
	}
}