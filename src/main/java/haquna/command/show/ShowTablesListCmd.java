package haquna.command.show;

import java.util.LinkedList;

import haquna.Haquna;
import haquna.command.Command;
import heart.xtt.Table;
import heart.xtt.XTTModel;

public class ShowTablesListCmd implements Command {
	
	public static final String pattern = "^" + Haquna.varName + "(\\s*)" + "[.]showTablesList[(][)](\\s*)";
	
	private String commandStr;
	private String varName;
	
	public ShowTablesListCmd() {
		
	}
	
	public ShowTablesListCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[.]");	
		this.varName = commandParts[0];
	}
	
	public void execute() {
		if(Haquna.modelMap.containsKey(varName)) {
			XTTModel model = Haquna.modelMap.get(varName);
			
			LinkedList<Table> tables = model.getTables();
			System.out.print("[");
			for(Table ta : tables){
				System.out.print(ta.getName());
				
				if(ta != tables.getLast()) {
					System.out.print( ", ");
				}

			}
			System.out.println("]");
			
		} else {
			System.out.println("No " + varName + " model in memory");
		}		
	}
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}

	public Command getNewCommand(String cmdStr) {
		return new ShowTablesListCmd(cmdStr);
	}	
	
	public String getCommandStr() {
		return commandStr;
	}

	public void setCommandStr(String commandStr) {
		this.commandStr = commandStr;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}
	
	
}
