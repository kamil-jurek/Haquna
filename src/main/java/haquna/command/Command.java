package haquna.command;

public interface Command {
	
	public void execute();
		
	public boolean matches(String commandStr);
	
	public Command getNewCommand(String commandStr);
}
