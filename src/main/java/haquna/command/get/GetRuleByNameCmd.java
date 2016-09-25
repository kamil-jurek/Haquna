package haquna.command.get;

import java.util.LinkedList;

import haquna.Haquna;
import haquna.command.Command;
import heart.xtt.Rule;
import heart.xtt.Table;

public class GetRuleByNameCmd implements Command {		
	
	public static final String pattern = "^[A-Z].*=(\\s*)[A-Z].*[.]getRuleByName[(]['](.*)['][)](\\s*)";
	
	private String commandStr;
	private String varName;
	private String ruleName;
	private String tableName;
	
	public GetRuleByNameCmd() {
		
	}
	
	public GetRuleByNameCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[=|.|']");		
		this.varName = commandParts[0];		
		this.tableName = commandParts[1];
		this.ruleName = commandParts[3];
	}
	
	@Override
	public void execute() {				
		if(!Haquna.isVarUsed(varName)) {
			if(Haquna.tableMap.containsKey(tableName)) {				
				Table table = Haquna.tableMap.get(tableName);
				LinkedList<Rule> rules = table.getRules();
				
				for(Rule rule : rules) {
					if(rule.getName().equals(ruleName)) {
						Haquna.ruleMap.put(varName, rule);
						return;
					}
				}
				System.out.println("No rule with '" + ruleName + "' name in '" + tableName + "' table");
										     			
			} else {
				System.out.println("No " + tableName + " table in memory");
			}			
		} else {
			System.out.println("Variable name: " + varName + " already in use");
		}
	}		
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new GetRuleByNameCmd(cmdStr);
	}
}