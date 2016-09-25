package haquna.command.get;

import java.util.LinkedList;

import haquna.Haquna;
import haquna.command.Command;
import heart.xtt.Rule;
import heart.xtt.Table;

public class GetRuleByIdCmd implements Command {		
	
	public static final String pattern = "^[A-Z].*=(\\s*)[A-Z].*[.]getRuleById[(]['](.*)['][)](\\s*)";
	
	private String commandStr;
	private String varName;
	private String ruleId;
	private String tableName;
	
	public GetRuleByIdCmd() {
		
	}
	
	public GetRuleByIdCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[=|.|']");		
		this.varName = commandParts[0];		
		this.tableName = commandParts[1];
		this.ruleId = commandParts[3];
	}
	
	@Override
	public void execute() {				
		if(!Haquna.isVarUsed(varName)) {
			if(Haquna.tableMap.containsKey(tableName)) {				
				Table table = Haquna.tableMap.get(tableName);
				LinkedList<Rule> rules = table.getRules();
				
				for(Rule rule : rules) {
					if(rule.getId().equals(ruleId)) {
						Haquna.ruleMap.put(varName, rule);
						return;
					}
				}
				System.out.println("No rule with '" + ruleId + "' id in '" + tableName + "' table");
										     			
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
		return new GetRuleByIdCmd(cmdStr);
	}
}