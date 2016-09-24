package haquna.command.show;

import haquna.Haquna;
import haquna.command.Command;

public class ShowVarsCmd implements Command {
	
	public static final String pattern = "^showVars[(][)](\\s*)";
	
	//private String commandStr;
	//private String varName;
	
	public ShowVarsCmd(String _commandStr) {
		//this.commandStr = _commandStr.replace(" ", "");
	}
	
	public void execute() {
		System.out.println("MODELS:");
		for(String var : Haquna.modelMap.keySet()) {
			System.out.println(var + " = " + Haquna.modelMap.get(var));
		}
		
		System.out.println("TABLES:");
		for(String var : Haquna.tableMap.keySet()) {
			System.out.println(var + " = " + Haquna.tableMap.get(var).getName());
		}
	}
	
	public static boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
}
