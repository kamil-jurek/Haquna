package haquna;

import heart.xtt.Table;

public class ShowCmd implements Command {
	
	public static final String pattern = "^[A-Z](.*)[.]show[(][)](\\s*)";
	
	private String commandStr;
	private String varName;
	
	public ShowCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[.]");	
		this.varName = commandParts[0];
	}
	
	public void execute() {
			System.out.println(varName);
			if(Haquna.tableMap.containsKey(varName)) {
				Table table = Haquna.tableMap.get(varName);
			
				System.out.println("Table id:"+table.getId());
				System.out.println("Table name:"+table.getName());
		     					
			} else {
				System.out.println("No such table in memory");
			}		
	}
	
	public static boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
}
