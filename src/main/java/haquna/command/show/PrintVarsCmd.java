package haquna.command.show;

import haquna.Haquna;
import haquna.command.Command;

public class PrintVarsCmd implements Command {
	
	public static final String pattern = "^printVars[(][)](\\s*)";
	
	public PrintVarsCmd() {
		
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
		for(String var : Haquna.attrMap.keySet()) {
			System.out.println("\t" + var + " = " + Haquna.attrMap.get(var).getName());
		}
		
		System.out.println("=== TYPES ===");
		for(String var : Haquna.typeMap.keySet()) {
			System.out.println("\t" + var + " = " + Haquna.typeMap.get(var).getName());
		}
		
		System.out.println("=== RULES ===");
		for(String var : Haquna.ruleMap.keySet()) {
			System.out.println("\t" + var + " = " + Haquna.ruleMap.get(var).getName());
		}
		
		System.out.println("=== CALLBACKS ===");
		for(String var : Haquna.callbackMap.keySet()) {
			System.out.println("\t" + var + " = " + Haquna.callbackMap.get(var));
		}
		
		System.out.println("=== TYPES BUILDERS ===");
		for(String var : Haquna.typeBuMap.keySet()) {
			System.out.println("\t" + var + " = " + Haquna.typeBuMap.get(var).getName());
		}
		
		System.out.println("=== ATTRIBUTES BUILDERS ===");
		for(String var : Haquna.attrBuMap.keySet()) {
			System.out.println("\t" + var + " = " + Haquna.attrBuMap.get(var).getName());
		}
		
		System.out.println("=== TABLES BUILDERS ===");
		for(String var : Haquna.tableBuMap.keySet()) {
			System.out.println("\t" + var + " = " + Haquna.tableBuMap.get(var).getName());
		}
		
		System.out.println("=== RULES BUILDERS ===");
		for(String var : Haquna.ruleBuMap.keySet()) {
			System.out.println("\t" + var + " = " + Haquna.ruleBuMap.get(var));
		}
		
		System.out.println("=== WORKING MEMORY ===");
		for(String var : Haquna.wmMap.keySet()) {
			System.out.println("\t" + var + " = " + Haquna.wmMap.get(var));
		}
				
	}
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new PrintVarsCmd();
	}
}
