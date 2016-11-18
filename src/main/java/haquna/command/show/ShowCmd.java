package haquna.command.show;

import haquna.HaqunaSingleton;
import haquna.XttModelUtils;
import haquna.command.Command;
import heart.xtt.Attribute;
import heart.xtt.Rule;
import heart.xtt.Table;
import heart.xtt.Type;
import heart.xtt.XTTModel;

public class ShowCmd implements Command {
	
	public static final String pattern = "^" + HaqunaSingleton.varName + "[.]show[(][)](\\s*)";
	
	private String commandStr;
	private String varName;
	
	public ShowCmd() {
		
	}
	
	public ShowCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[.]");	
		this.varName = commandParts[0];
	}
	
	public boolean execute() {
		if(HaqunaSingleton.tableMap.containsKey(varName)) {
			Table table = HaqunaSingleton.tableMap.get(varName);			
			System.out.println(XttModelUtils.tableToHMR(table));
		     					
		} else if(HaqunaSingleton.attrMap.containsKey(varName)){
			Attribute attr = HaqunaSingleton.attrMap.get(varName);
			System.out.println(XttModelUtils.attributeToHMR(attr));
		
		} else if(HaqunaSingleton.typeMap.containsKey(varName)){
			Type type = HaqunaSingleton.typeMap.get(varName);
			System.out.println(XttModelUtils.typeToHMR(type));		
		    		
		} else if(HaqunaSingleton.ruleMap.containsKey(varName)){
			Rule rule = HaqunaSingleton.ruleMap.get(varName);
			System.out.println(XttModelUtils.ruleToHMR(rule));
		
		} else if(HaqunaSingleton.callbackMap.containsKey(varName)){
			String callback = HaqunaSingleton.callbackMap.get(varName);
		    System.out.println("Callback: " + callback);
						
		} else if(HaqunaSingleton.modelMap.containsKey(varName)){
			XTTModel model = HaqunaSingleton.modelMap.get(varName);
			System.out.println(XttModelUtils.getHMR(model));
		
		} else {
			System.out.println("No such object in memory");
		}
		
		return true;
	}
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new ShowCmd(cmdStr);
	}
}
