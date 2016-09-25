package haquna.command.get;

import haquna.Haquna;
import haquna.command.Command;
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
		if(!Haquna.isVarUsed(varName)) {
			if(Haquna.attribiuteMap.containsKey(attribiuteName)) {				
				Attribute attribiute = Haquna.attribiuteMap.get(attribiuteName);
				Type type = attribiute.getType();
				
				Haquna.typeMap.put(varName, type);
						     			
			} else {
				System.out.println("No " + attribiuteName + " attribiut in memory");
			}			
		} else {
			System.out.println("Variable name: " + varName + " already in use");
		}
	}		
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new GetTypeCmd(cmdStr);
	}
}