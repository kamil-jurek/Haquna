package haquna.command.get;

import java.util.LinkedList;

import haquna.Haquna;
import haquna.command.Command;
import heart.xtt.Table;
import heart.xtt.XTTModel;

public class GetTableByNameCmd implements Command {		
	
	public static final String pattern = "^[A-Z].*=(\\s*)[A-Z].*[.]getTableByName[(]['](.*)['][)](\\s*)";
	
	private String commandStr;
	private String varName;
	private String modelName;
	private String tableName;
	
	public GetTableByNameCmd() {
		
	}
	
	public GetTableByNameCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[=|.|']");		
		this.varName = commandParts[0];		
		this.modelName = commandParts[1];
		this.tableName = commandParts[3];
	}
	
	@Override
	public void execute() {				
		if(!Haquna.isVarUsed(varName)) {
			if(Haquna.modelMap.containsKey(modelName)) {
				XTTModel model = Haquna.modelMap.get(modelName);				
				LinkedList<Table> tables = model.getTables();
				
				for(Table table : tables){
					if(table.getName().equals(tableName)){
						Haquna.tableMap.put(varName, table);
						return;
					}	     
				}
				System.out.println("No table with '" + tableName + "' name in '" + modelName + "' model");
			
			} else {
				System.out.println("No " + modelName + " model in memory");
			}			
		} else {
			System.out.println("Variable name: " + varName + " already in use");
		}
	}		
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new GetTableByNameCmd(cmdStr);
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

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}