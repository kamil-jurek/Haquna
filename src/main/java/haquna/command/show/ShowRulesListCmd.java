package haquna.command.show;

import haquna.Haquna;
import haquna.command.Command;
import heart.alsvfd.Formulae;
import heart.alsvfd.expressions.ExpressionInterface;
import heart.xtt.Decision;
import heart.xtt.Rule;
import heart.xtt.Table;

public class ShowRulesListCmd implements Command {
	
	public static final String pattern = "^[A-Z](.*)[.]showRulesList[(][)](\\s*)";
	
	private String commandStr;
	private String tableName;
	
	public ShowRulesListCmd() {
		
	}
	
	public ShowRulesListCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[.]");	
		this.tableName = commandParts[0];
	}
	
	public void execute() {
		if(Haquna.tableMap.containsKey(tableName)) {
			Table table = Haquna.tableMap.get(tableName);
			
			System.out.println("RULES FOR TABLE " + table.getName());		
			System.out.println("================================");				  
			for(Rule rule : table.getRules()){
				System.out.println("Rule name: " + rule.getName());
				System.out.println("Rule id:   " + rule.getId());
				System.out.print("\tIF ");
				for(Formulae f : rule.getConditions()){
					System.out.print(f.getLHS()+" "+f.getOp()+" "+f.getRHS()+", ");
				}
				  
				System.out.print("\n\tTHEN ");
				  
				for(Decision d: rule.getDecisions()){
					System.out.print(d.getAttribute().getName()+" is set to ");					  
					ExpressionInterface e = d.getDecision();
					System.out.print(e);
				}
				System.out.println();
				System.out.println("================================");
				  
			}
		} else {
			System.out.println("No " + tableName + " table in memory");
		}		
	}
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}

	public Command getNewCommand(String cmdStr) {
		return new ShowRulesListCmd(cmdStr);
	}	
	
	public String getCommandStr() {
		return commandStr;
	}

	public void setCommandStr(String commandStr) {
		this.commandStr = commandStr;
	}

	public String getVarName() {
		return tableName;
	}

	public void setVarName(String varName) {
		this.tableName = varName;
	}
	
	
}
