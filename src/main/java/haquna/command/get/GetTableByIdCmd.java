package haquna.command.get;

import java.util.LinkedList;

import haquna.Haquna;
import haquna.HaqunaException;
import haquna.command.Command;
import haquna.utils.HaqunaUtils;
import heart.xtt.Table;
import heart.xtt.XTTModel;

public class GetTableByIdCmd implements Command {		
	
	public static final String pattern = "^" + Haquna.varName +"(\\s*)=(\\s*)" + Haquna.varName + "[.]getTableById[(]['](.*)['][)](\\s*)";
	
	private String commandStr;
	private String varName;
	private String modelName;
	private String tableId;
	
	public GetTableByIdCmd() {
		
	}
	
	public GetTableByIdCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[=|.|']");		
		this.varName = commandParts[0];		
		this.modelName = commandParts[1];
		this.tableId = commandParts[3];
	}
	
	@Override
	public void execute() {				
		try {
			HaqunaUtils.checkVarName(varName);
			XTTModel model = HaqunaUtils.getModel(modelName);
			getTableById(model);
			
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
		return new GetTableByIdCmd(cmdStr);
	}
	
	private void getTableById(XTTModel model) throws HaqunaException {
		LinkedList<Table> tables = model.getTables();
		for(Table table : tables){
			if(table.getId().equals(tableId)){
				Haquna.tableMap.put(varName, table);
				return;
			}	     
		}
		throw new HaqunaException("No table with '" + tableId + "' id in '" + modelName + "' model");
	}
}