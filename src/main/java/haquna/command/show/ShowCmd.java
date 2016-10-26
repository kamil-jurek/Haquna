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
	
	public static final String pattern = "^" + Haquna.varName + "[.]show[(][)](\\s*)";
	
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
			System.out.println(XttModelUtils.tableToHMR(table));
		     					
		} else if(Haquna.attribiuteMap.containsKey(varName)){
			Attribute attr = Haquna.attribiuteMap.get(varName);
			System.out.println(XttModelUtils.attributeToHMR(attr));
		
		} else if(Haquna.typeMap.containsKey(varName)){
			Type.Builder typeBuilder = Haquna.typeMap.get(varName).typeBuilder;
			System.out.println(" builder" + XttModelUtils.typeBuildToHMR(typeBuilder));		
		    		
		} else if(Haquna.ruleMap.containsKey(varName)){
			Rule rule = Haquna.ruleMap.get(varName);
			System.out.println(XttModelUtils.ruleToHMR(rule));
		
		} else if(Haquna.callbackMap.containsKey(varName)){
			String callback = Haquna.callbackMap.get(varName);
		    System.out.println("Callback: " + callback);
						
		} else if(Haquna.modelMap.containsKey(varName)){
			XTTModel model = Haquna.modelMap.get(varName);
			System.out.println(XttModelUtils.getHMR(model));
		
		} else {
			System.out.println("No such object in memory");
		}
		
		Haquna.wasSucces = true;
	}
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new ShowCmd(cmdStr);
	}
}
