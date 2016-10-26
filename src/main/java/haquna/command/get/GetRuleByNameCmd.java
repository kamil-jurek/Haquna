package haquna.command.get;

import java.util.LinkedList;

import haquna.Haquna;
import haquna.HaqunaException;
import haquna.command.Command;
import haquna.utils.HaqunaUtils;
import heart.xtt.Rule;
import heart.xtt.Table;

public class GetRuleByNameCmd implements Command {		
	
	public static final String pattern = "^" + Haquna.varName +"(\\s*)=(\\s*)" + Haquna.varName + "[.]getRuleByName[(]['](.*)['][)](\\s*)";
	
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
		try {
			HaqunaUtils.checkVarName(varName);
			Table table = HaqunaUtils.getTable(tableName);
			getRuleByName(table);
			
			Haquna.wasSucces = true;
		
		} catch (HaqunaException e) {
			HaqunaUtils.printRed(e.getMessage());
			
			return;
		}	
	}		
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new GetRuleByNameCmd(cmdStr);
	}
	
	private void getRuleByName(Table table) throws HaqunaException {
		/*LinkedList<Rule> rules = table.getRules();		
		for(Rule rule : rules) {
			if(rule.getName().equals(ruleName)) {
				Haquna.ruleMap.put(varName, rule);
				return;
			}
		}
		throw new HaqunaException("No rule with '" + ruleName + "' name in '" + tableName + "' table");*/
		String modelName = determineModel();
		System.out.println(modelName);
		
		
		Rule.Builder ruleBuilder = Haquna.modelMap.get(modelName).getBuilder().getIncompleteRuleNamed(ruleName);
		/*for(String k : Haquna.modelMap.keySet()) {
			ruleBuilder = Haquna.modelMap.get(k).getBuilder().getIncompleteRuleNamed(ruleName);
		}*/
				
		if(ruleBuilder != null) {
			Haquna.ruleBuilderMap.put(varName+"B", ruleBuilder);
			
		} else {
			throw new HaqunaException("No rule with '" + ruleName + "' name in '" + tableName + "' table");
		}
		
	}
	
	private String determineModel() {
		String modelName = null;
		for(String k : Haquna.modelMap.keySet()) {
			for(Table t : Haquna.modelMap.get(k).getTables()) {
				System.out.println(t.getName());
				System.out.println(Haquna.tableMap.get(tableName).getName());
				
				if(t.getName().equals(Haquna.tableMap.get(tableName).getName())) {
					modelName = k;
					System.out.print("fsdfsdf");
					return modelName;
				}
				else{
					System.out.print("2222");
				}
			}
		}
		return modelName;
	}
}