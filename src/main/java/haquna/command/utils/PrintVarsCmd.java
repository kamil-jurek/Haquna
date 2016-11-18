package haquna.command.utils;

import haquna.HaqunaSingleton;
import haquna.command.Command;

public class PrintVarsCmd implements Command {
	
	public static final String pattern = "^printVars[(][)](\\s*)";
	
	public PrintVarsCmd() {
		
	}
	
	public boolean execute() {
		System.out.println("=== MODELS ===");
		for(String var : HaqunaSingleton.modelMap.keySet()) {
			System.out.println("\t" + var + " = " + HaqunaSingleton.modelMap.get(var));
		}
		
		System.out.println("=== TABLES ===");
		for(String var : HaqunaSingleton.tableMap.keySet()) {
			System.out.println("\t" + var + " = " + HaqunaSingleton.tableMap.get(var).getName());
		}
		
		System.out.println("=== ATTRIBIUTES ===");
		for(String var : HaqunaSingleton.attrMap.keySet()) {
			System.out.println("\t" + var + " = " + HaqunaSingleton.attrMap.get(var).getName());
		}
		
		System.out.println("=== TYPES ===");
		for(String var : HaqunaSingleton.typeMap.keySet()) {
			System.out.println("\t" + var + " = " + HaqunaSingleton.typeMap.get(var).getName());
		}
		
		System.out.println("=== RULES ===");
		for(String var : HaqunaSingleton.ruleMap.keySet()) {
			System.out.println("\t" + var + " = " + HaqunaSingleton.ruleMap.get(var).getName());
		}
		
		System.out.println("=== CALLBACKS ===");
		for(String var : HaqunaSingleton.callbackMap.keySet()) {
			System.out.println("\t" + var + " = " + HaqunaSingleton.callbackMap.get(var));
		}
		
		System.out.println("=== TYPES BUILDERS ===");
		for(String var : HaqunaSingleton.typeBuilderMap.keySet()) {
			System.out.println("\t" + var + " = " + HaqunaSingleton.typeBuilderMap.get(var).getName());
		}
		
		System.out.println("=== ATTRIBUTES BUILDERS ===");
		for(String var : HaqunaSingleton.attrBuilderMap.keySet()) {
			System.out.println("\t" + var + " = " + HaqunaSingleton.attrBuilderMap.get(var).getName());
		}
		
		System.out.println("=== TABLES BUILDERS ===");
		for(String var : HaqunaSingleton.tableBuilderMap.keySet()) {
			System.out.println("\t" + var + " = " + HaqunaSingleton.tableBuilderMap.get(var).getName());
		}
		
		System.out.println("=== RULES BUILDERS ===");
		for(String var : HaqunaSingleton.ruleBuilderMap.keySet()) {
			System.out.println("\t" + var + " = " + HaqunaSingleton.ruleBuilderMap.get(var));
		}
		
		System.out.println("=== WORKING MEMORY ===");
		for(String var : HaqunaSingleton.wmMap.keySet()) {
			System.out.println("\t" + var + " = " + HaqunaSingleton.wmMap.get(var));
		}
		
		return true;
	}
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new PrintVarsCmd();
	}
}
