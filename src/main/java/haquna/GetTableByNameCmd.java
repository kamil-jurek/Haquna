package haquna;

import java.util.LinkedList;

import heart.xtt.Table;
import heart.xtt.XTTModel;

public class GetTableByNameCmd implements Command {		
	
	public static final String pattern = "^[A-Z].*=(\\s*)[A-Z].*[.]getTableByName[(]['](.*)['][)](\\s*)";
	
	private String commandStr;
	private String varName;
	private String modelName;
	private String tableName;
	//T = M.getTableByName()
	public GetTableByNameCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[=|.|']");		
		this.varName = commandParts[0];		
		this.modelName = commandParts[1];
		this.tableName = commandParts[3];
	}
	
	@Override
	public void execute() {				
		if(!Haquna.isVarUsed(modelName) ||
		   !Haquna.isVarUsed(varName)) {
			
			System.out.println(varName);
			System.out.println(modelName);
			System.out.println(tableName);
			XTTModel model = Haquna.modelMap.get(modelName);
		
			LinkedList<Table> tables = model.getTables();
			for(Table table : tables){
				if(table.getName().equals(tableName)){
					Haquna.tableMap.put(varName, table);
					System.out.println("Got table: " + varName);
					return;
				}	     
			}
			
		} else {
			System.out.println("Variable Name already in use");
		}
	}		
	
	public static boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
}