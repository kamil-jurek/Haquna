package haquna.command.utils;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import haquna.Haquna;
import haquna.command.Command;
import haquna.utils.HaqunaUtils;

public class LsCmd implements Command {		
	
	public static final String pattern = "^(\\s*)ls(\\s*)";
	
	private String commandStr;
	
	public LsCmd() {
		
	}
	
	public LsCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
	
	}
	
	@Override
	public void execute() {		
		try {
			Path dir = Paths.get(System.getProperty("user.dir"));
    		try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*")) {
    			for(Path file : stream) {
    				System.out.println(file.getFileName());
    			}
    		}
			
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
		return new LsCmd(cmdStr);
	}

	public String getCommandStr() {
		return commandStr;
	}
}