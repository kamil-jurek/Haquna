package haquna.command.show;

import haquna.Haquna;
import haquna.XttModelUtils;
import haquna.command.Command;
import heart.xtt.Attribute;
import heart.xtt.Rule;
import heart.xtt.Table;
import heart.xtt.Type;
import heart.xtt.XTTModel;

public class ShowCmd implements Command {
	
	public static final String pattern = "^[A-Z](.*)[.]show[(][)](\\s*)";
	
	private String commandStr;
	private String varName;
	
	public ShowCmd() {
		
	}
	
	public ShowCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[.]");	
		this.varName = commandParts[0];
	}
	
	public void execute() {
		if(Haquna.tableMap.containsKey(varName)) {
			Table table = Haquna.tableMap.get(varName);
			
			System.out.println(table.toHMR());
			/*System.out.println("=============================");
			System.out.println("Table id:   " + table.getId());
			System.out.println("Table name: " + table.getName());
			System.out.println("=============================");
			System.out.println();*/
		     					
		} else if(Haquna.attribiuteMap.containsKey(varName)){
			Attribute attr = Haquna.attribiuteMap.get(varName);
			System.out.println(attr.toHMR());
			/*System.out.println("=============================");
		    System.out.println("Att Id:       " + att.getId());
		    System.out.println("Att name:     " + att.getName());
		    System.out.println("Att typeName: " + att.getTypeId());
		    System.out.println("Att abbrev:   " + att.getAbbreviation());
		    System.out.println("Att comm:     " + att.getComm());
		    System.out.println("Att desc:     " + att.getDescription());
		    System.out.println("Att class:    " + att.getXTTClass());
		    System.out.println("=============================");
		    System.out.println();*/
		
		} else if(Haquna.typeMap.containsKey(varName)){
			Type type = Haquna.typeMap.get(varName);
			System.out.println(type.toHMR());
			
			/*System.out.println("=============================");
		    System.out.println("Type id:     " + type.getId());
		    System.out.println("Type name:   " + type.getName());
		    System.out.println("Type base:   " + type.getBase());
		    System.out.println("Type length: " + type.getLength());
		    System.out.println("Type scale:  " + type.getPrecision());
		    System.out.println("desc:        " + type.getDescription());		 
		    System.out.println("=============================");
		    System.out.println();   */
		    		
		} else if(Haquna.ruleMap.containsKey(varName)){
			Rule rule = Haquna.ruleMap.get(varName);
			System.out.println(rule.toHMR());
			/*System.out.println("================================");
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
			System.out.println("================================"); */ 
		
		} else if(Haquna.callbackMap.containsKey(varName)){
			String callback = Haquna.callbackMap.get(varName);
			System.out.println("=============================");
		    System.out.println("Callback: " + callback);
		    System.out.println("=============================");
		    System.out.println();
			
			
		} else if(Haquna.modelMap.containsKey(varName)){
			XTTModel model = Haquna.modelMap.get(varName);
			System.out.println(XttModelUtils.getHMR(model));
			
		/*	System.out.println("=============================");
			System.out.println(model.getSource());
		    System.out.println("=============================");
		    System.out.println();    */
		    
		} else {
			System.out.println("No such table in memory");
		}		
	}
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new ShowCmd(cmdStr);
	}
}
