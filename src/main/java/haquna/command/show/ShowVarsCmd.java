package haquna.command.show;

import haquna.Haquna;
import haquna.command.Command;

public class ShowVarsCmd implements Command {
	
	public static final String pattern = "^showVars[(][)](\\s*)";
	
	public ShowVarsCmd() {
		
	}
	
	public void execute() {
		System.out.println("=== MODELS ===");
		for(String var : Haquna.modelMap.keySet()) {
			System.out.println("\t" + var + " = " + Haquna.modelMap.get(var));
		}
		
		System.out.println("=== TABLES ===");
		for(String var : Haquna.tableMap.keySet()) {
			System.out.println("\t" + var + " = " + Haquna.tableMap.get(var).getName());
		}
		
		System.out.println("=== ATTRIBIUTES ===");
		for(String var : Haquna.attribiuteMap.keySet()) {
			System.out.println("\t" + var + " = " + Haquna.attribiuteMap.get(var).getName());
		}
		
		System.out.println("=== TYPES ===");
		for(String var : Haquna.typeMap.keySet()) {
			System.out.println("\t" + var + " = " + Haquna.typeMap.get(var).getName());
		}
		
		System.out.println("=== RULES ===");
		for(String var : Haquna.ruleMap.keySet()) {
			System.out.println("\t" + var + " = " + Haquna.ruleMap.get(var).getName());
		}
	}
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new ShowVarsCmd();
	}
}
