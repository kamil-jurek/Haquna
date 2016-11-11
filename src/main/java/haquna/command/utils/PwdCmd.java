package haquna.command.utils;

import haquna.Haquna;
import haquna.command.Command;
import haquna.utils.HaqunaUtils;

public class PwdCmd implements Command {		
	
	public static final String pattern = "^(\\s*)pwd(\\s*)";
	
	private String commandStr;
	
	public PwdCmd() {
		
	}
	
	public PwdCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
	
	}
	
	@Override
	public void execute() {		
		try {
			System.out.println("Working directory: " + System.getProperty("user.dir"));
			
			Haquna.wasSucces = true;
					
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
		return new PwdCmd(cmdStr);
	}

	public String getCommandStr() {
		return commandStr;
	}
}