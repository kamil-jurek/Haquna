package haquna.command.get;

import java.util.LinkedList;

import haquna.Haquna;
import haquna.command.Command;
import heart.xtt.Table;
import heart.xtt.XTTModel;

public class GetTableByIdCmd implements Command {		
	
	public static final String pattern = "^[A-Z].*=(\\s*)[A-Z].*[.]getTableById[(]['](.*)['][)](\\s*)";
	
	private String commandStr;
	private String varName;
	private String modelName;
	private String tableId;

	public GetTableByIdCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[=|.|']");		
		this.varName = commandParts[0];		
		this.modelName = commandParts[1];
		this.tableId = commandParts[3];
	}
	
	@Override
	public void execute() {				
		if(!Haquna.isVarUsed(varName)) {
			if(Haquna.modelMap.containsKey(modelName)) {
				XTTModel model = Haquna.modelMap.get(modelName);				
				LinkedList<Table> tables = model.getTables();
				
				for(Table table : tables){
					if(table.getId().equals(tableId)){
						Haquna.tableMap.put(varName, table);
						return;
					}	     
				}
			
			} else {
				System.out.println("No " + modelName + " model in memory");
			}			
		} else {
			System.out.println("Variable name: " + varName + " already in use");
		}
	}		
	
	public static boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
}